package example.URL;

import java.net.MalformedURLException;
import java.net.URL;

public class URLInfo {

    public static void main(String[] args) {
        if (args.length != 1) System.exit(1);

        URL url= null;
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.out.println("잘못된 URL 형식입니다.");
            System.out.println(e);
            System.exit(1);
        }

        System.out.println("protocol : " + url.getProtocol());
        System.out.println("host : " + url.getHost());
        System.out.println("port : " + url.getPort());
        System.out.println("file name : " + url.getPath());
        System.out.println("parameter : " + url.getQuery());
    }
}
