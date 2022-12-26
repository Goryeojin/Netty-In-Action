package example.UDP.time;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("사용법 : java UDPEchoServer.java port");
            System.exit(1);
        }
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("port 번호는 양의 정수로 입력해주세요.");
            System.exit(1);
        }

        DatagramSocket dsock = null;
        try {
            System.out.println("접속 대기상태입니다.");
            dsock = new DatagramSocket(port);
            String line = null;
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                dsock.receive(receivePacket);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
                String sdate = format.format(new Date());

                System.out.println(receivePacket.getAddress().getHostAddress() + "에 현재 시간 " + sdate + "을 전송합니다.");

                DatagramPacket sendPacket = new DatagramPacket(sdate.getBytes(), sdate.getBytes().length, receivePacket.getAddress(), receivePacket.getPort());
                dsock.send(sendPacket);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (dsock != null) dsock.close();
        }
    }
}
