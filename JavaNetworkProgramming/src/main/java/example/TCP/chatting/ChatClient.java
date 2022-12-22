package example.TCP.chatting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("사용법 : java ChatCleint.java id 접속할 서버 ip");
            System.exit(1);
        }

        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        boolean endFlag = false;
        try {
            socket = new Socket(args[1], 10001);
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            pw.println(args[0]);
            pw.flush();

            InputThread thread = new InputThread(socket, br);
            thread.start();
            String line = null;
            while((line = input.readLine()) != null) {
                pw.println(line);
                pw.flush();
                if (line.equals("/quit")) {
                    endFlag = true;
                    break;
                }
            }
            System.out.println("클라이언트의 접속을 종료합니다.");
        } catch (Exception e) {
            if (!endFlag) System.out.println(e);
        } finally {
            try {
                if (pw != null) pw.close();
                if (br != null) br.close();
                if (socket != null) socket.close();
            } catch (Exception ignored) {}
        }
    }
}

class InputThread extends Thread {
    private Socket socket = null;
    private BufferedReader br = null;
    public InputThread(Socket socket, BufferedReader br) {
        this.socket = socket;
        this.br = br;
    }

    @Override
    public void run() {
        try {
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
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