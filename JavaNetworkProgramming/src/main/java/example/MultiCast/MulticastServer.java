package example.MultiCast;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastServer extends Thread {
    DatagramSocket socket = null;
    DatagramPacket packet = null;
    InetAddress channel = null;
    int port = 20001;
    String address = "239.0.0.1";
    boolean onAir = true;

    public MulticastServer() throws IOException {
        super("멀티캐스트 방송국");
        socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        String msg = "멀티캐스트 방송이 잘 들리시나요?";
        byte[] b = new byte[100];
        while (onAir) {
            try {
                b = msg.getBytes();
                channel = InetAddress.getByName(address);
                packet = new DatagramPacket(b, b.length, channel, port);
                socket.send(packet);
                try {
                    sleep(500);
                    System.out.println("방송중입니다.");
                } catch (InterruptedException ignored) {}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new MulticastServer().start();
    }
}
