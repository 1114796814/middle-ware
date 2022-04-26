package cn.edu.xmu.level46db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.xmu.level46db.mapper")
public class Level46DbApplication {
    public static void main(String[] args) {
        SpringApplication.run(Level46DbApplication.class, args);
    }
}
