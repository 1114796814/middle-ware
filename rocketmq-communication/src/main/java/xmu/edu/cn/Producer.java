package xmu.edu.cn;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 17:57
 */

public class Producer implements Runnable {
    DefaultMQProducer producer = null;
    public String nameSrvAddr;
    public String producerGroup;
    public String toTopic;

    public Producer(String nameSrvAddr, String producerGroup, String toTopic) {
        this.nameSrvAddr = nameSrvAddr;
        this.producerGroup = producerGroup;
        this.toTopic = toTopic;
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameSrvAddr);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String string = sc.next();
            if (string != null) {
                Message message = new Message(toTopic, string.getBytes(StandardCharsets.UTF_8));
                SendResult send = null;
                try {
                    send = producer.send(message);
                } catch (MQClientException e) {
                    e.printStackTrace();
                } catch (RemotingException e) {
                    e.printStackTrace();
                } catch (MQBrokerException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
