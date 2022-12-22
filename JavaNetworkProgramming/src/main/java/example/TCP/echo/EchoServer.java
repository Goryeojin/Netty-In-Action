package example.TCP.echo;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(10001);
            System.out.println("접속을 기다립니다");
            Socket socket = server.accept();
            InetAddress inetAddr = socket.getInetAddress();
            InetSocketAddress socketAddr = (InetSocketAddress) socket.getRemoteSocketAddress();
            System.out.println(inetAddr.getHostAddress() + "로부터 접속했습니다");
            System.out.println(socketAddr.getPort());

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("클라이언트로부터 전송받은 문자열 : " + line);
                pw.println(line);
                pw.flush();
            }
            pw.close();
            br.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
