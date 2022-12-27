package example.URL;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class WebSpider {
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
            InputStream in = url.openStream();
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
