package cn.edu.xmu.level46db.dao;

import cn.edu.xmu.level46db.mapper.CETPoMapper;
import cn.edu.xmu.level46db.mapper.UserPoMapper;
import cn.edu.xmu.level46db.model.po.CETPo;
import cn.edu.xmu.level46db.model.po.CETPoExample;
import cn.edu.xmu.level46db.model.po.UserPo;
import cn.edu.xmu.level46db.model.po.UserPoExample;
import cn.edu.xmu.level46db.model.bo.User;
import cn.edu.xmu.level46db.util.ReturnNo;
import cn.edu.xmu.level46db.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 12:12
 */
@Repository
public class Level46dbDAO {
    @Autowired
    UserPoMapper userPoMapper;

    @Autowired
    CETPoMapper cetPoMapper;

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
        UserPoExample userPoExample = new UserPoExample();
        UserPoExample.Criteria criteria = userPoExample.createCriteria();
        criteria.andPasswordEqualTo(userPo.getPassword())
                .andEmailEqualTo(userPo.getEmail());
        try {
            List<UserPo> userPos = userPoMapper.selectByExample(userPoExample);
            if (userPos == null || userPos.size() == 0) {
                return new ReturnObject(ReturnNo.AUTH_INVALID_ACCOUNT);
            } else {
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

    public synchronized ReturnObject rush(Byte type, Integer year, Integer month) {
        LocalDate localDate = LocalDate.now();
        CETPoExample cetPoExample = new CETPoExample();
        CETPoExample.Criteria criteria = cetPoExample.createCriteria();
        criteria.andBeginDateLessThanOrEqualTo(localDate)
                .andEndDateGreaterThanOrEqualTo(localDate)
                .andTypeEqualTo(type)
                .andYearEqualTo(year)
                .andMonthEqualTo(month);
        try {
            List<CETPo> cetPos = cetPoMapper.selectByExample(cetPoExample);
            if (cetPos.size() == 0) {
                return new ReturnObject(ReturnNo.NOT_AT_THE_SPECIFIED_TIME);
            }
            CETPo cetPo = cetPos.get(0);
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
}
