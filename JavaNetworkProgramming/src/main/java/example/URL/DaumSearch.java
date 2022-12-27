package example.URL;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DaumSearch {
    public static void main(String[] args) {
        if (args.length != 2) System.exit(1);
        try {
            String keyword = URLEncoder.encode(args[0]);
            String query = "query=" + keyword;
            String u = "https://search.naver.com/search.naver";
            System.out.println(u + query);
            URL url = new URL(u);
            URLConnection conn = url.openConnection();
            HttpURLConnection hurlc = (HttpURLConnection) conn;
            hurlc.setRequestMethod("GET");
            hurlc.setDoOutput(true);
            hurlc.setDoInput(true);
            hurlc.setUseCaches(false);
            hurlc.setDefaultUseCaches(false);

            PrintWriter out = new PrintWriter(hurlc.getOutputStream());
            out.println(query);
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(hurlc.getInputStream()));
            PrintWriter pw = new PrintWriter(new FileWriter(args[1]));
            String inputLine = null;

            while ((inputLine = in.readLine()) != null) pw.println(inputLine);
            in.close();
            pw.close();
            System.out.println("검색된 결과가 " + args[1] + "파일에 저장되었습니다.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
