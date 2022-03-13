package xmu.edu.cn;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 14:19
 */
public class SendUDP {
    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        new Thread(()->{
            try {
                while (true){
                    byte[] bytes = new byte[1024];
                    DatagramPacket datagramPacket = new DatagramPacket(bytes,0,bytes.length,new InetSocketAddress("localhost",6666));
                    datagramSocket.receive(datagramPacket);
                    byte[] data = datagramPacket.getData();
                    System.out.println(new Date()+"收到： "+ new String(data,0,datagramPacket.getLength()));
                }
            }catch (Exception e){

            }finally {

            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            try {
                while (true) {
                    String str = scanner.next();
                    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket datagramPacket =
                            new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress("127.0.0.1", 6666));

                    datagramSocket.send(datagramPacket);
                }
            }catch (Exception e){
                datagramSocket.close();
            }
        }, "发送").start();
    }
}
