package cn.edu.xmu.level46db.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/12 11:32
 */
@Component
@ConfigurationProperties(prefix = "async-pool.insert-order")
@Data
public class ConsumeInsertOrderPoll extends AbstractExecutorPool{
    // 线程前缀
    private String threadNamePrefix = "handler consumer insertorder executor-";
}
