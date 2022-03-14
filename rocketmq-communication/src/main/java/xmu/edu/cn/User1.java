package xmu.edu.cn;

import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 17:49
 */
public class User1 {
    public static void main(String[] args) throws MQClientException {
        new Thread(new Producer("user1","101.132.164.244:9876", "producer1",
                "sayHello2")).start();
        new Consumer("user1","101.132.164.244:9876", "consumer1",
                "sayHello1");
    }
}
