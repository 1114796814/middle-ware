package xmu.edu.cn.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 13:44
 */
public class ServerTCP {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("服务端启动……");
            ServerSocket serverSocket = new ServerSocket(64000);
            Socket server = serverSocket.accept();
            new Thread(() -> {
                InputStream is = null;
                try {
                    is = server.getInputStream();
                    byte[] b = new byte[1024];
                    while (true) {
                        int len = is.read(b);
                        String msg = new String(b, 0, len);
                        System.out.println(new Date()+" 收到客户端发来消息："+msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, "接收消息").start();
            new Thread(() -> {
                OutputStream os = null;
                try {
                    os = server.getOutputStream();
                    String string = null;
                    while(true){
                        string = scanner.next();
                        os.write(string.getBytes(StandardCharsets.UTF_8));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        os.close();
                        server.close();
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
