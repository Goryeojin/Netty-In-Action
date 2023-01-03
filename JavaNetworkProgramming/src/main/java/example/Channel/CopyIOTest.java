package example.Channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyIOTest extends MyTimer{

    public static void main(String[] args) throws Exception {
        start();
        copyIO();
        end("copyIO");
    }

    public static void copyIO() throws Exception {
        FileInputStream fis = new FileInputStream(filePath);

        byte[] buf = new byte[fis.available()];

        try (fis; FileOutputStream fos = new FileOutputStream("index.html")) {
            fis.read(buf);
            fos.write(buf);
        }
    }
}
