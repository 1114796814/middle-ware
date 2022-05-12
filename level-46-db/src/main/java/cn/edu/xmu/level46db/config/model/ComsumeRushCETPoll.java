package cn.edu.xmu.level46db.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/12 11:29
 */
@Component
@ConfigurationProperties(prefix = "async-pool.rush-cet")
@Data
public class ComsumeRushCETPoll extends AbstractExecutorPool{
    // 线程前缀
    private String threadNamePrefix = "handler consumer rushcet executor-";
}
