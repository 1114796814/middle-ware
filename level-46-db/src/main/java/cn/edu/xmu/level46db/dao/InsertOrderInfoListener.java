package cn.edu.xmu.level46db.dao;

import cn.edu.xmu.level46db.mapper.CETOrderInfoPoMapper;
import cn.edu.xmu.level46db.model.po.CETOrderInfoPo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/19 14:35
 */
@Service
@RocketMQMessageListener(topic = "${cet.insertorderinfo}", consumerGroup = "${cet.insertorderinfo}", consumeThreadMax = 128)
public class InsertOrderInfoListener implements RocketMQListener<String> {
    @Autowired
    CETOrderInfoPoMapper cetOrderInfoPoMapper;

    @Override
    public void onMessage(String message) {
        SecureRandom secureRandom = new SecureRandom();
        CETOrderInfoPo cetOrderInfoPo = new CETOrderInfoPo();
        cetOrderInfoPo.setCetId(Long.valueOf(message));
        cetOrderInfoPo.setUserId(secureRandom.nextLong());
        cetOrderInfoPo.setCreateTime(LocalDateTime.now());
        int insert = cetOrderInfoPoMapper.insert(cetOrderInfoPo);
    }
}
