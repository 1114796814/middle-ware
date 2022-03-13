package xmu.edu.cn.udp;

import java.net.SocketException;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 14:18
 */
public class User1 {
    public static void main(String[] args) throws SocketException {
        new Thread(new Receive(9999, "127.0.0.1")).start();
        new Thread(new Send(2222, "127.0.0.1", 8888)).start();
    }
}
