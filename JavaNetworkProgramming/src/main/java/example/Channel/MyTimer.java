package example.Channel;

public class MyTimer {
    protected static final String filePath = "example.html";

    private static long startTime;
    private static long endTime;

    protected static void start() {
        startTime = System.currentTimeMillis();
    }

    protected static void end(String name) {
        endTime = System.currentTimeMillis();
        System.out.println("[ " + name + " Time : " + (endTime - startTime) + " ]");
    }
}
