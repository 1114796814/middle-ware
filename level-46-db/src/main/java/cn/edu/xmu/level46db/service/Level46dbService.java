package cn.edu.xmu.level46db.service;

import cn.edu.xmu.level46db.dao.Level46dbDAO;
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
    Level46dbDAO level46dbDAO;

    @Transactional(readOnly = true)
    public ReturnObject login(LoginVo loginVo) {
        User user = new User(loginVo);
        return level46dbDAO.login(user);
    }


    @Transactional
    public ReturnObject rush(Byte type, Integer year, Integer month) {
        return level46dbDAO.rush(type, year, month);
    }
}
