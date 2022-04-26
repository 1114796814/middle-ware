package xmu.edu.cn.group;

import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/14 10:04
 */
public class UserGroup3 {
    public static void main(String[] args) throws MQClientException {
        new Thread(new Producer("黄添悦", "101.132.164.244:9876", "producer2",
                "sayHello1","zh_cn")).start();
        new ConsumerGroup("黄添悦","101.132.164.244:9876", "consumer1",
                "sayHello1");
    }
}
