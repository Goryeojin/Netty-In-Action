package example.UDP.time;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPTimeClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.exit(1);
        }
        String ip = args[0];
        int port = 0;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.exit(1);
        }
        InetAddress inetAddr = null;
        try {
            inetAddr = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.exit(1);
        }

        DatagramSocket dsock = null;
        try {
            dsock = new DatagramSocket();
            String line = null;
            DatagramPacket sendPacket = new DatagramPacket("".getBytes(), "".getBytes().length, inetAddr, port);
            dsock.send(sendPacket);

            byte[] buffer = new byte[200];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            dsock.receive(receivePacket);

            String msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);
            System.out.println("서버로부터 전달받은 시간 : " + msg.trim());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (dsock != null) dsock.close();
        }
    }
}
