package example.Buffer;

import java.nio.ByteBuffer;

public class BulkReadTest {
    public static void main(String[] args) {

        ByteBuffer buf = ByteBuffer.allocate(10);
        buf.put((byte) 0).put((byte) 1).put((byte) 2).put((byte) 3).put((byte) 4);
        buf.mark();
        buf.put((byte) 5).put((byte) 6).put((byte) 7).put((byte) 8).put((byte) 9);
        buf.reset();

        byte[] b = new byte[15];

        int size = buf.remaining();
        if (b.length < size) {
            size = b.length;
        }
        buf.get(b, 0, size);
        System.out.println("Position : " + buf.position() + ", Limit : " + buf.limit());

        doSomething(b, size);
    }

    public static void doSomething(byte[] b, int size) {
        for (int i=0; i<size; i++) {
            System.out.println("byte = " + b[i]);
        }
    }
}
