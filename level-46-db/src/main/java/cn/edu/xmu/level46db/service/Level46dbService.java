package cn.edu.xmu.level46db.service;

import cn.edu.xmu.level46db.dao.Level46dbDao;
import cn.edu.xmu.level46db.model.vo.LoginVo;
import cn.edu.xmu.level46db.model.bo.User;
import cn.edu.xmu.level46db.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 14:30
 */
@Service
public class Level46dbService {

    @Autowired
    Level46dbDao level46dbDAO;

    @Transactional(readOnly = true)
    public ReturnObject login(LoginVo loginVo) {
        User user = new User(loginVo);
        return level46dbDAO.login(user);
    }

//    /**
//     * 悲观锁
//     * @param type
//     * @param year
//     * @param month
//     * @return
//     */
//    public synchronized ReturnObject rush(Long id) {
//        return transactionalRush(id);
//    }
//
//    @Transactional
//    public ReturnObject transactionalRush(Long id) {
//        return level46dbDAO.rush(id);
//    }

    /**
//     * 乐观锁
//     * @return
//     */
//    public  ReturnObject rush(Long id) {
//        return level46dbDAO.rushByOptimistic(id);
//    }


    public ReturnObject rush(Long id) {
        return level46dbDAO.rushByOptimisticAndRedis(id);
    }
}
