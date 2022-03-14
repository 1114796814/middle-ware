package xmu.edu.cn.group;

import org.apache.rocketmq.client.exception.MQClientException;
import xmu.edu.cn.Producer;
import xmu.edu.cn.group.ConsumerGroup;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/14 10:00
 */
public class UserGroup1 {
    public static void main(String[] args) throws MQClientException {
        new Thread(new Producer("101.132.164.244:9876", "producer2",
                "sayHello1")).start();
        new ConsumerGroup("101.132.164.244:9876", "consumer1",
                "sayHello1");
    }
}
