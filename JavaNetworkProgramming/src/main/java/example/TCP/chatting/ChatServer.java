package example.TCP.chatting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10001);
            System.out.println("접속을 기다립니다.");
            HashMap map = new HashMap();
            while(true) {
                Socket socket = server.accept();
                ChatThread thread = new ChatThread(socket, map);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class ChatThread extends Thread {
    private Socket socket;
    private String id;
    private BufferedReader br;
    private HashMap map;
    private boolean initFlag = false;

    public ChatThread(Socket socket, HashMap map) {
        this.socket = socket;
        this.map = map;
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            id = br.readLine();
            broadcast(id + "님이 접속하셨습니다.");
            System.out.println("접속한 사용자의 아이디는 " + id + "입니다.");
            synchronized (map) {
                map.put(this.id, pw);
            }
            initFlag = true;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.equals("/quit")) break;
                if (line.indexOf("/to ") == 0) sendMsg(line);
                else broadcast(id + " : " + line);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
          synchronized (map) {
              map.remove(id);
          }
          broadcast(id + "님이 접속 종료했습니다.");
          try {
              if (socket != null) socket.close();
          } catch (Exception ignored) {}
        }
    }

    public void sendMsg(String msg) {
        int start = msg.indexOf(" ") + 1;
        int end = msg.indexOf(" ", start);

        if (end != -1) {
            String to = msg.substring(start, end);
            String msg2 = msg.substring(end + 1);
            Object obj = map.get(to);
            if (obj != null) {
                PrintWriter pw = (PrintWriter) obj;
                pw.println(id + "님이 다음의 귓속말을 보내셨습니다. : " + msg2);
                pw.flush();
            }
        }
    }

    public void broadcast(String msg) {
        synchronized (map) {
            map.forEach((k, v) -> {
                PrintWriter pw = (PrintWriter) v;
                pw.println(msg);
                pw.flush();
            });
//            Collection collection = map.values();
//            Iterator iter = collection.iterator();
//            while(iter.hasNext()) {
//                PrintWriter pw = (PrintWriter) iter.next();
//                pw.println(msg);
//                pw.flush();
//            }
        }
    }
}
