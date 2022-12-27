package example.URL;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebSpiderWithURLConnection {
    public static void main(String[] args) {

        if (args.length != 2) System.exit(1);
        URL url = null;
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.out.println(e);
            System.exit(1);
        }

        FileOutputStream fos = null;
        try {
            URLConnection urlConn = url.openConnection();
            String contentType = urlConn.getContentType();
            long d1 = urlConn.getDate();
            Date d = new Date(d1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            String sdate = format.format(d);

            System.out.println("Content-type : " + contentType);
            System.out.println("읽어온 시간 : " + sdate);

            InputStream in = urlConn.getInputStream();
            fos = new FileOutputStream(args[1]);

            byte[] buffer = new byte[512];
            int readCnt = 0;

            System.out.println("읽어오기 시작");
            while ((readCnt = in.read(buffer)) != -1)
                fos.write(buffer, 0, readCnt);
            System.out.println(args[1] + "파일에 모두 저장했습니다.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (Exception ignored) {}
        }
    }
}
