package cn.edu.xmu.level46db.config.model;

import lombok.Data;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/12 11:28
 */
@Data
public abstract class AbstractExecutorPool {
    private int corePoolSize;
    private int maxPoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;
}
