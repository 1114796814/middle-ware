package xmu.edu.cn.udp;

import java.net.SocketException;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 14:19
 */
public class User2 {
    public static void main(String[] args) throws SocketException {
        new Thread(new Receive(8888, "127.0.0.1")).start();
        new Thread(new Send(4444, "127.0.0.1", 9999)).start();
    }
}
