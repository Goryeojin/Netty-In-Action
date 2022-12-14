package example.Buffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ViewBufferTest {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);

        IntBuffer ib = buf.asIntBuffer();

        System.out.println("position: " + ib.position() + ", limit: " + ib.limit() + ", capacity: " + ib.capacity());
        ib.put(8).put(1024);
        System.out.println("index_0: " + ib.get(0) + ", index_1: " + ib.get(1));
        System.out.println(buf.position());

        while (buf.hasRemaining()) {
            System.out.print(buf.get() + " ");
        }
    }
}
