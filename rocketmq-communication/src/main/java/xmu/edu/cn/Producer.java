package xmu.edu.cn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.JacksonFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import xmu.edu.cn.po.Msg;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 17:57
 */

public class Producer implements Runnable {
    ObjectMapper objectMapper = new ObjectMapper();
    DefaultMQProducer producer = null;
    public String nameSrvAddr;
    public String producerGroup;
    public String toTopic;
    public String name;

    public Producer(String name,String nameSrvAddr, String producerGroup, String toTopic) {
        this.name = name;
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
            try {
                string = objectMapper.writeValueAsString(new Msg(this.name, string));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (string != null) {
                Message message = new Message(toTopic, string.getBytes(StandardCharsets.UTF_8));
                try {
                    producer.send(message);
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
