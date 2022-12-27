package example.MultiCast;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastChatClient extends Frame implements ActionListener {

    private TextField idTF = null;
    private TextField input = null;
    private TextArea display = null;
    private CardLayout cardLayout = null;

    DatagramSocket socket = null;
    DatagramPacket packet = null;
    InetAddress channel = null;
    int port = 20005;
    String address = "239.0.0.1";
    boolean onAir = true;
    String id = "";

    public MulticastChatClient() {
        super("채팅 클라이언트");
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        Panel loginPanel = new Panel();
        loginPanel.setLayout(new BorderLayout());
        loginPanel.add("North", new Label("아이디를 입력해 주신 후 엔터 키를 입력하세요."));
        idTF = new TextField(20);
        idTF.addActionListener(this);
        Panel c = new Panel();
        c.add(idTF);
        loginPanel.add("Center", c);
        add("login", loginPanel);

        Panel main = new Panel();
        main.setLayout(new BorderLayout());
        input = new TextField();
        input.addActionListener(this);
        display = new TextArea();
        main.add("Center", display);
        main.add("South", input);
        add("main", main);

        try {
            socket = new DatagramSocket(port);
        } catch (Exception e) {
            System.out.println("서버와 접속 시 오류가 발생했습니다.");
            e.printStackTrace();
            System.exit(1);
        }

        setSize(500, 500);
        cardLayout.show(this, "login");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("종료합니다.");
                sendMsg(id + "님이 종료합니다.");
                try {
                    socket.close();
                } catch (Exception exception) {
                    System.exit(0);
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new MulticastChatClient();
    }

    public void sendMsg(String msg) {
        byte[] b = new byte[2000];
        try {
            b = msg.getBytes();
            channel = InetAddress.getByName(address);
            packet = new DatagramPacket(b, b.length, channel, port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == idTF) {
            id = idTF.getText();
            if (id == null || id.trim().equals("")) {
                System.out.println("아이디를 다시 입력하세요.");
                return;
            }
            sendMsg(id + "님이 입장하셨습니다.\n");
            WinInputMulticastThread wit = new WinInputMulticastThread();
            wit.start();
            cardLayout.show(this, "main");
            input.requestFocus();
        } else if (e.getSource() == input) {
            String msg = input.getText();
            sendMsg(id + " : " + msg + "\n");
            if (msg.equals("/quit")) {
                try {
                    socket.close();
                } catch (Exception ignored) {}
                sendMsg(id + "님이 종료합니다.");
                System.out.println("종료합니다.");
                System.exit(1);
            }
            input.setText("");
            input.requestFocus();
        }
    }

    class WinInputMulticastThread extends Thread {
        MulticastSocket receiver = null;
        DatagramPacket packet = null;
        int port = 20005;
        String address = "239.0.0.1";
        public WinInputMulticastThread() {
            try {
                receiver = new MulticastSocket(port);
                channel = InetAddress.getByName(address);
                receiver.joinGroup(channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    byte[] b = new byte[2000];
                    packet = new DatagramPacket(b, b.length);
                    receiver.receive(packet);
                    String msg = new String(packet.getData());
                    if (msg.equals("/quit")) break;
                    display.append(msg);
                }
                receiver.leaveGroup(channel);
                receiver.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
