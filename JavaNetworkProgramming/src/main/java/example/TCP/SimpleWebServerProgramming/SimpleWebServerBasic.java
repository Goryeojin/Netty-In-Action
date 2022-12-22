package example.TCP.SimpleWebServerProgramming;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleWebServerBasic {
    public static void main(String[] args) {
        Socket sock = null;
        BufferedReader br = null;

        try {
            ServerSocket server = new ServerSocket(80);
            sock = server.accept();
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (br != null) br.close();
            } catch (Exception ex) {}
            try {
                if (sock != null) sock.close();
            } catch (Exception ex) {}
        }
    }
}
