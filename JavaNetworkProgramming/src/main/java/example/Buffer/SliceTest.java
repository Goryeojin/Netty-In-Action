package example.Buffer;

import java.nio.ByteBuffer;

public class SliceTest {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        buf.put((byte) 0).put((byte) 1).put((byte) 2).put((byte) 3).put((byte) 4).put((byte) 5).put((byte) 6).put((byte) 7).put((byte) 8).put((byte) 9);
        buf.position(3);
        buf.limit(9);

        ByteBuffer buf2 = buf.slice();
        System.out.println("1) Position: " + buf2.position() + ", Limit: " + buf.limit() + ", Capacity: " + buf2.capacity());

        while (buf2.hasRemaining()) {
            System.out.print(buf2.get() + " ");
        }

        buf.put(3, (byte) 10);
        System.out.println("\n=> buf의 3 값을 10으로 바꿈");

        System.out.println("Original Buffer Value : " + buf.get(3));
        System.out.println("Slice Buffer Value : " + buf2.get(0));

        buf2.put(4, (byte) 11);
        System.out.println("=> buf2의 4 값을 11로 바꿈");

        System.out.println("Original Buffer Value : " + buf.get(4));
        System.out.println("Duplicate Buffer Value : " + buf2.get(1));
    }
}
