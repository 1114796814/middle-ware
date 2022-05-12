package cn.edu.xmu.level46db.config;

import cn.edu.xmu.level46db.config.model.AbstractExecutorPool;
import cn.edu.xmu.level46db.config.model.ComsumeRushCETPoll;
import cn.edu.xmu.level46db.config.model.ConsumeInsertOrderPoll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/12 11:24
 */
@Configuration
@EnableAsync
@Slf4j
public class ThreadExecutorConfig {

    private final ComsumeRushCETPoll comsumeRushCETPoll;
    private final ConsumeInsertOrderPoll consumeInsertOrderPoll;

    // 构造器注入，spring4.x以后推荐使用
    @Autowired
    public ThreadExecutorConfig(ComsumeRushCETPoll comsumeRushCETPoll, ConsumeInsertOrderPoll consumeInsertOrderPoll) {
        this.comsumeRushCETPoll = comsumeRushCETPoll;
        this.consumeInsertOrderPoll = consumeInsertOrderPoll;
    }

    /**
     * 创建线程池 消费扣除库存，存入阻塞队列中
     */
    @Bean(name = "asyncExecutorConsumerRushCET")
    public Executor asyncExecutorConsumerRushCET() {
        return initExcutor(comsumeRushCETPoll, comsumeRushCETPoll.getThreadNamePrefix(), (r, executor) -> {
            log.info("队列已满，根据业务自行处理。。。");
        });
    }

    /**
     * 创建线程池 用于正常插入订单
     */
    @Bean(name = "asyncExecutorConsumerInsertOrder")
    public Executor asyncExecutorConsumerInsertOrder() {
        return initExcutor(consumeInsertOrderPoll, consumeInsertOrderPoll.getThreadNamePrefix(), (r, executor) -> {
            log.info("队列已满，根据业务自行处理。。。");
        });
    }

    private Executor initExcutor(AbstractExecutorPool abstractExecutorPool, String threadName, RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(abstractExecutorPool.getCorePoolSize());
        threadPool.setMaxPoolSize(abstractExecutorPool.getMaxPoolSize());
        threadPool.setKeepAliveSeconds(abstractExecutorPool.getKeepAliveSeconds());
        threadPool.setQueueCapacity(abstractExecutorPool.getQueueCapacity());
        threadPool.setThreadNamePrefix(threadName);
        threadPool.setRejectedExecutionHandler(rejectedExecutionHandler);
        return threadPool;
    }
}
