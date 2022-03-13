package xmu.edu.cn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 13:50
 */
public class ClientTCP {
    public static void main(String[] args) {
        System.out.println("客户端");
        try {
            Scanner sc = new Scanner(System.in);
            Socket client = new Socket("localhost", 64000);
            new Thread(() -> {
                InputStream is = null;
                try {
                    is = client.getInputStream();
                    byte[] b = new byte[1024];
                    while (true) {
                        int len = is.read(b);
                        String msg = new String(b, 0, len);
                        System.out.println(new Date()+" 收到服务端发来消息："+msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, "接收消息").start();
            new Thread(() -> {
                OutputStream os = null;
                try {
                    os = client.getOutputStream();
                    String string = null;
                    while (true) {
                        string = sc.next();
                        os.write(string.getBytes(StandardCharsets.UTF_8));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        os.close();
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, "发送消息").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
