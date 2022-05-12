package cn.edu.xmu.level46db.dao;

import cn.edu.xmu.level46db.mapper.CETOrderInfoPoMapper;
import cn.edu.xmu.level46db.mapper.CETPoMapper;
import cn.edu.xmu.level46db.mapper.ExtendCETPoMapper;
import cn.edu.xmu.level46db.mapper.UserPoMapper;
import cn.edu.xmu.level46db.model.po.*;
import cn.edu.xmu.level46db.model.bo.User;
import cn.edu.xmu.level46db.util.ReturnNo;
import cn.edu.xmu.level46db.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 12:12
 */
@Repository
public class Level46dbDao {
    @Autowired
    UserPoMapper userPoMapper;

    @Autowired
    CETPoMapper cetPoMapper;

    @Autowired
    RedisTemplate redisTemplate;


    @Autowired
    ExtendCETPoMapper extendCETPoMapper;

    @Autowired
    CETOrderInfoPoMapper cetOrderInfoPoMapper;
    @Autowired
    AsyncDao asyncDao;

    /*true 表示没了*/
    ConcurrentHashMap<String, Boolean> concurrentHashMap = new ConcurrentHashMap<>();


    String USER_INFO = "u_%s";
    String CET_INFO = "cet_%s";

    public ReturnObject insertUser(User user) {
        UserPo userPo = user.generatePo();
        try {
            int insert = userPoMapper.insert(userPo);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ReturnObject(userPo);
    }

    public ReturnObject login(User user) {
        UserPo userPo = user.generatePo();
        Object object = redisTemplate.opsForValue().get(String.format(USER_INFO, userPo.getEmail()));
        if (object != null) {
            if (object.toString().equals(user.getPassword())) {
                return new ReturnObject(ReturnNo.OK);
            } else {
                return new ReturnObject(ReturnNo.AUTH_INVALID_ACCOUNT);
            }
        }
        UserPoExample userPoExample = new UserPoExample();
        UserPoExample.Criteria criteria = userPoExample.createCriteria();
        criteria.andPasswordEqualTo(userPo.getPassword())
                .andEmailEqualTo(userPo.getEmail());
        try {
            List<UserPo> userPos = userPoMapper.selectByExample(userPoExample);
            if (userPos == null || userPos.size() == 0) {
                return new ReturnObject(ReturnNo.AUTH_INVALID_ACCOUNT);
            } else {
                set(String.format(USER_INFO, userPo.getEmail()), userPo.getPassword(), 600000);
                return new ReturnObject(ReturnNo.OK);
            }
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    public ReturnObject getUserById(Long id) {
        UserPo userPo = null;
        try {
            userPo = userPoMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
        return new ReturnObject(userPo);
    }

    public ReturnObject getUserByIdCard(String idCard) {
        UserPoExample userPoExample = new UserPoExample();
        UserPoExample.Criteria criteria = userPoExample.createCriteria();
        criteria.andIdCardEqualTo(idCard);
        try {
            List<UserPo> userPos = userPoMapper.selectByExample(userPoExample);
            return new ReturnObject(userPos.get(0));
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 悲观锁
     *
     * @return
     */
    public ReturnObject rush(Long id) {
        try {
            CETPo cetPo = cetPoMapper.selectByPrimaryKey(id);
            if (cetPo == null) {
                return new ReturnObject(ReturnNo.NOT_AT_THE_SPECIFIED_TIME);
            }
            if (cetPo.getStock() == 0) {
                return new ReturnObject(ReturnNo.NOT_ENOUGH);
            }
            cetPo.setStock(cetPo.getStock() - 1);
            int i = cetPoMapper.updateByPrimaryKey(cetPo);
            return new ReturnObject(ReturnNo.OK);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 乐观锁
     *
     * @return
     */
    public ReturnObject rushByOptimistic(Long id) {
        try {
            int rush = extendCETPoMapper.rush(id);
            if (rush == 0) {
                return new ReturnObject(ReturnNo.NOT_ENOUGH);
            } else {
                return new ReturnObject(ReturnNo.OK);
            }
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }


    /**
     * 通过redis扣除
     *
     * @return
     * @Parm id
     */
    public ReturnObject rushByOptimisticAndRedis(Long id) {
        try {
            final String key = String.format(CET_INFO, id.toString());
            if (concurrentHashMap.getOrDefault(key, false)) {
                return new ReturnObject(ReturnNo.NOT_ENOUGH);
            }
            Object object = redisTemplate.opsForValue().get(key);
            if (object == null) {
                synchronized (this) {
                    object = redisTemplate.opsForValue().get(String.format(CET_INFO, id));
                    if (object == null) {
                        CETPo cetPo = cetPoMapper.selectByPrimaryKey(id);
                        object = cetPo.getStock();
                        redisTemplate.opsForValue().set(String.format(CET_INFO, id), cetPo.getStock());
                        concurrentHashMap.put(key, false);
                    }
                }
            }
            if (Integer.valueOf(object.toString()) <= 0) {
                concurrentHashMap.put(key, true);
                return new ReturnObject(ReturnNo.NOT_ENOUGH);
            } else {
                Long decrement = redisTemplate.opsForValue().decrement(String.format(CET_INFO, id));
                if (decrement < 0) {
                    concurrentHashMap.put(key, true);
                    return new ReturnObject(ReturnNo.NOT_ENOUGH);
                } else {
                    //TODO 插入抢的情况 并扣除数据库
                    asyncDao.insertCETOrderTable(id);
                    asyncDao.decrCETtableById(id);
                    return new ReturnObject(ReturnNo.OK);
                }
            }
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    public boolean set(String key, String value, long timeout) {
        if (timeout <= 0) {
            timeout = 60;
        }
        long min = 1;
        long max = timeout / 5;
        try {
            //增加随机数，防止雪崩
            timeout += (long) new Random().nextDouble() * (max - min);
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
