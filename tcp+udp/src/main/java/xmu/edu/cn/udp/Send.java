package xmu.edu.cn.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 18:44
 */
public class Send implements Runnable {
    DatagramSocket datagramSocket = null;
    Scanner scanner = new Scanner(System.in);
    public Integer myport;
    public String hostName;
    public Integer toPort;

    public Send(Integer myport, String hostName, Integer toPort) throws SocketException {
        this.myport = myport;
        this.hostName = hostName;
        this.toPort = toPort;
        datagramSocket = new DatagramSocket(myport);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = scanner.next();
                byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                DatagramPacket datagramPacket =
                        new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress(this.hostName, this.toPort));
                datagramSocket.send(datagramPacket);
            }
        } catch (Exception e) {
            datagramSocket.close();
        }
    }
}
