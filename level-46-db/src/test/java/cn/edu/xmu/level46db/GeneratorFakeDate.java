package cn.edu.xmu.level46db;

import cn.edu.xmu.level46db.dao.Level46dbDao;
import cn.edu.xmu.level46db.model.bo.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Locale;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 12:16
 */
@SpringBootTest
public class GeneratorFakeDate {
    @Autowired
    Level46dbDao level46dbDAO;
    @Test
    public void generate(){
        Faker faker = new Faker(Locale.CHINA,new SecureRandom());
        User user = new User();
        for (int i = 0; i < 4e5; i++) {
            user.setEmail(faker.regexify("\\d{6,12}\\@(qq\\.com|163\\.com)"));
            user.setAddress(faker.address().city());
            user.setIdCard(faker.regexify("\\d{18}"));
            user.setMobile(faker.phoneNumber().cellPhone());
            user.setName(faker.name().name());
            user.setUniversity(faker.university().name());
            user.setPassword(faker.regexify("[a-zA-Z]{6,15}"));
            level46dbDAO.insertUser(user);
            System.out.println(i);
        }
    }
}
