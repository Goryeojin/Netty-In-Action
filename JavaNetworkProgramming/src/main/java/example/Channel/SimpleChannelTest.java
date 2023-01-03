package example.Channel;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class SimpleChannelTest {
    public static void main(String[] args) throws Exception {
        ReadableByteChannel src = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        while ((src.read(buf)) != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                dest.write(buf);
            }
            buf.clear();
        }
    }
}
