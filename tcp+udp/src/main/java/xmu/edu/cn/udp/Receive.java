package xmu.edu.cn.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Date;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/03/13 18:34
 */
public class Receive implements Runnable {
    DatagramSocket datagramSocket = null;
    public Integer port;
    public String hostName;
    public Receive(Integer port, String hostName) throws SocketException {
        this.port = port;
        this.hostName = hostName;
        datagramSocket = new DatagramSocket(port);
    }
    @Override
    public void run() {
        try {
            while (true) {
                byte[] bytes = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
                datagramSocket.receive(datagramPacket);
                byte[] data = datagramPacket.getData();
                System.out.println(new Date() + "收到： " + new String(data, 0, datagramPacket.getLength()));
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
    }
}
