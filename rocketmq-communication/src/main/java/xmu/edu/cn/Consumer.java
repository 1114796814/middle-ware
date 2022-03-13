package xmu.edu.cn;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 18:03
 */
public class Consumer {
    DefaultMQPushConsumer consumer = null;
    public String nameSrvAddr;
    public String consumerGroup;
    public String fromTopic;

    public Consumer(String nameSrvAddr, String consumerGroup, String fromTopic) throws MQClientException {
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
                    System.out.println(new Date() + "收到：" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
