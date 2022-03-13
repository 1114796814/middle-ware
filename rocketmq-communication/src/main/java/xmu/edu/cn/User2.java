package xmu.edu.cn;

import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 18:07
 */
public class User2 {
    public static void main(String[] args) throws MQClientException {
        new Thread(new Producer("101.132.164.244:9876", "producer2",
                "sayHello1")).start();
        new Consumer("101.132.164.244:9876", "consumer2",
                "sayHello2");
    }
}
