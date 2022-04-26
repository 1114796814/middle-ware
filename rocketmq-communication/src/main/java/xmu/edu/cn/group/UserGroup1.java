package xmu.edu.cn.group;

import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/14 10:00
 */
public class UserGroup1 {
    public static void main(String[] args) throws MQClientException {
        new Thread(new Producer("高艺桐","101.132.164.244:9876", "producer2",
                "sayHello1","zh_cn")).start();
        new ConsumerGroup("高艺桐","101.132.164.244:9876", "consumer1",
                "sayHello1");
    }
}
