package example.Channel;

import java.io.FileInputStream;

public class StreamTest {
    static FileInputStream in = null;

    public static void main(String[] args) throws Exception {
        in = new FileInputStream("example.html");
        TestThread t = new TestThread(in);
        t.start();

        Thread.sleep(2000);
        System.out.println(in.available());

        t.interrupt();

        Thread.sleep(2000);
        System.out.println(in.available());
    }

    static class TestThread extends Thread {
        FileInputStream in = null;
        public TestThread(FileInputStream o) {
            in = o;
        }

        @Override
        public void run() {
            try {
                int v = 0;
                while ((v = in.read()) != -1) {
                    System.out.println("Thread start...");
                    System.out.println(v);
                    Thread.sleep(1000);
                }
                in.close();
            } catch (Exception e) {
                System.out.println("Thread end..");
            }
        }
    }
}
