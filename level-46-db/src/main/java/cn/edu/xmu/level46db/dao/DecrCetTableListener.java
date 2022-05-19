package cn.edu.xmu.level46db.dao;

import cn.edu.xmu.level46db.mapper.ExtendCETPoMapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/19 14:36
 */
@Service
@RocketMQMessageListener(topic = "${cet.decrcettablebyid}", consumerGroup = "${cet.decrcettablebyid}", consumeThreadMax = 128)
public class DecrCetTableListener implements RocketMQListener<String> {
    @Autowired
    ExtendCETPoMapper extendCETPoMapper;
    @Override
    public void onMessage(String message) {
        int rush = extendCETPoMapper.rush(Long.valueOf(message));
    }
}
