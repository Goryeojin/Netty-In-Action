package example.TCP.SimpleWebServerProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleWebServer {
    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(80);
            while (true) {
                System.out.println("접속을 대기합니다.");
                Socket socket = server.accept();
                System.out.println("새로운 스레드를 시작합니다.");
                HttpThread thread = new HttpThread(socket);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class HttpThread extends Thread {
    private final Socket socket;
    BufferedReader br = null;
    PrintWriter pw = null;
    public HttpThread(Socket socket) {
        this.socket = socket;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        BufferedReader input = null;
        try {
            String line = br.readLine();
            int start = line.indexOf(" ") + 2;
            int end = line.lastIndexOf("HTTP") - 1;
            String fileName = line.substring(start, end);
            if (fileName.equals(""))
                fileName = "index.html";
            System.out.println("사용자가 " + fileName + "을 요청했습니다.");
            File f = new File(getClass().getClassLoader().getResource("index.html").getFile());
            input = new BufferedReader(new FileReader(f));
            String fline = null;
            while ((fline = input.readLine()) != null) {
                pw.println(fline);
                pw.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (input != null) input.close();
            } catch (Exception ignored) {}
            try {
                if (br != null) br.close();
            } catch (Exception ignored) {}
            try {
                if (pw != null) pw.close();
            } catch (Exception ignored) {}
            try {
                if (socket != null) socket.close();
            } catch (Exception ignored) {}
        }
    }
}
