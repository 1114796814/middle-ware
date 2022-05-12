package cn.edu.xmu.level46db;

import cn.edu.xmu.level46db.dao.Level46dbDao;
import cn.edu.xmu.level46db.util.ReturnObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 12:16
 */
@SpringBootTest
public class Level46DbApplicationTest {
    @Autowired
    Level46dbDao level46dbDAO;
    @Test
    public void test(){
        ReturnObject userByIdCard = level46dbDAO.getUserByIdCard("1111");
        System.out.println(userByIdCard.getData());
    }
}
