package example.Buffer;

import java.nio.ByteBuffer;

public class DuplicateTest {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        buf.put((byte) 0).put((byte) 1).put((byte) 2).put((byte) 3).put((byte) 4).put((byte) 5).put((byte) 6).put((byte) 7).put((byte) 8).put((byte) 9);
        buf.position(3);
        buf.limit(9);
        buf.mark();

        ByteBuffer buf2 = buf.duplicate();
        System.out.println("1) Position: " + buf2.position() + ", Limit: " + buf.limit() + ", Capacity: " + buf2.capacity());

        buf2.position(7);
        buf2.reset();
        System.out.println("reset() 호출 후 Position: " + buf2.position());

        buf2.clear();
        System.out.println("2) Position: " + buf2.position() + ", Limit: " + buf.limit() + ", Capacity: " + buf2.capacity());

        while (buf2.hasRemaining()) {
            System.out.print(buf2.get() + " ");
        }
        buf.put(0, (byte) 10);
        System.out.println("\n=> buf의 0 값을 10으로 바꿈");

        System.out.println("Original Buffer Value : " + buf.get(0));
        System.out.println("Duplicate Buffer Value : " + buf2.get(0));

        buf2.put(1, (byte) 11);
        System.out.println("=> buf2의 1 값을 11로 바꿈");

        System.out.println("Original Buffer Value : " + buf.get(1));
        System.out.println("Duplicate Buffer Value : " + buf2.get(1));
    }
}
