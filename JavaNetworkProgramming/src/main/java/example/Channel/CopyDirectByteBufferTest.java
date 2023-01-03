package example.Channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyDirectByteBufferTest extends MyTimer{
    public static void main(String[] args) throws Exception {
        start();
        copyDirectBuffer();
        end("copyDirectBuffer");
    }

    public static void copyDirectBuffer() throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fis = new FileInputStream(filePath);
            fos = new FileOutputStream("index.html");
            in = fis.getChannel();
            out = fos.getChannel();

            ByteBuffer buf = ByteBuffer.allocateDirect((int) in.size());
            in.read(buf);
            buf.flip();
            out.write(buf);
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }
}
