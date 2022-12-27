package example.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class PostConnection {
    public static void main(String[] args) {
        if (args.length != 2) System.exit(1);

        try {
            String id = URLEncoder.encode(args[0]);
            String pwd = URLEncoder.encode(args[1]);
            String query = "id=" + id + "&pwd=" + pwd;
            String u = "http://localhost/postform.html";
            System.out.println(u + query);
            URL url = new URL(u);
            URLConnection connection = url.openConnection();
            HttpURLConnection hurl = (HttpURLConnection) connection;
            hurl.setRequestMethod("POST");
            hurl.setDoOutput(true);
            hurl.setDoInput(true);
            hurl.setUseCaches(false);
            hurl.setDefaultUseCaches(false);

            PrintWriter out = new PrintWriter(hurl.getOutputStream());
            out.println(query);
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(hurl.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
