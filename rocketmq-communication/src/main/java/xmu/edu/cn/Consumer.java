package xmu.edu.cn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import xmu.edu.cn.po.Msg;

import java.util.Date;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 18:03
 */
@Getter
public class Consumer {
    ObjectMapper objectMapper = new ObjectMapper();
    DefaultMQPushConsumer consumer = null;
    public String nameSrvAddr;
    public String consumerGroup;
    public String fromTopic;
    public String name;

    public Consumer(String name, String nameSrvAddr, String consumerGroup, String fromTopic) throws MQClientException {
        this.name = name;
        this.nameSrvAddr = nameSrvAddr;
        this.consumerGroup = consumerGroup;
        this.fromTopic = fromTopic;
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameSrvAddr);
        consumer.subscribe(fromTopic, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    String s = new String(msg.getBody());
                    Msg getmsg = null;
                    try {
                        getmsg = objectMapper.readValue(s, Msg.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    String getname = getmsg.getName();
                    if (!getname.equals(name)) {
                        System.out.println(new Date() +"用户 "+ getname + "  :" + getmsg.getMsg());
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
