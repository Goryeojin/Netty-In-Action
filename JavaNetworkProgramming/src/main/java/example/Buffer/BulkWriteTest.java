package example.Buffer;

import java.nio.ByteBuffer;

public class BulkWriteTest {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        buf.position(5);
        buf.mark();
        System.out.println("Position : " + buf.position() + ", Limit : " + buf.limit());

        byte[] b = new byte[15];
        for (int i=0; i<b.length; i++) {
            b[i] = (byte) i;
        }

        int size = buf.remaining();
        if (b.length < size) {
            size = b.length;
        }
        buf.put(b, 0, size);
        System.out.println("Position : " + buf.position() + ", Limit : " + buf.limit());

        buf.reset();
        doSomething(buf, size);
    }
    public static void doSomething(ByteBuffer buf, int size) {
        for (int i=0; i<size; i++) {
            System.out.println("byte = " + buf.get());
        }
    }
}
