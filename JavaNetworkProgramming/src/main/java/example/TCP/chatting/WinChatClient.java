package example.TCP.chatting;

import com.sun.jdi.event.ExceptionEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class WinChatClient extends Frame implements ActionListener {

    private TextField idTF = null;
    private TextField input = null;
    private TextArea display = null;
    private CardLayout cardLayout = null;

    private BufferedReader br = null;
    private PrintWriter pw = null;
    private Socket socket = null;

    public WinChatClient(String ip) {
        super("채팅 클라이언트");
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        Panel loginPanel = new Panel();
        loginPanel.setLayout(new BorderLayout());
        loginPanel.add("North", new Label("아이디를 입력하여 주신 후 엔터 키를 입력해주세요."));
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
        display.setEditable(false);
        main.add("Center", display);
        main.add("South", input);
        add("main", main);

        try {
            socket = new Socket(ip, 10001);
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("서버와 접속 시 오류가 발생했습니다.");
            System.out.println(e);
            System.exit(1);
        }

        setSize(500, 500);
        cardLayout.show(this, "login");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                pw.println("/quit");
                pw.flush();

                try {
                    socket.close();
                } catch (Exception ignored) {}
                System.out.println("종료합니다");
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("사용법 : java WinChatClient.java ip");
            System.exit(1);
        }
        new WinChatClient(args[0]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == idTF) {
            String id = idTF.getText();
            if (id == null || id.trim().equals("")) {
                System.out.println("아이디를 다시 입력해주세요.");
                return;
            }
            pw.println(id.trim());
            pw.flush();
            WinInputThread thread = new WinInputThread(socket, br);
            thread.start();
            cardLayout.show(this, "main");
            input.requestFocus();
        } else if (e.getSource() == input) {
            String msg = input.getText();
            pw.println(msg);
            pw.flush();
            if (msg.equals("/quit")) {
                try {
                    socket.close();
                } catch (Exception ex) {
                    System.out.println("종료합니다.");
                    System.exit(1);
                }
                input.setText("");
                input.requestFocus();
            }
        }
    }

    class WinInputThread extends Thread {
        private Socket socket = null;
        private BufferedReader br = null;

        public WinInputThread(Socket socket, BufferedReader br) {
            this.socket = socket;
            this.br = br;
        }

        @Override
        public void run() {
            try {
                String line = null;
                while((line = br.readLine()) != null) {
                    display.append(line + "\n");
                }
            } catch (Exception ignored) {}
            finally {
                try {
                    if (br != null) br.close();
                    if (socket != null) socket.close();
                } catch (Exception ignored) {}
            }
        }
    }
}
