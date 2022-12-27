package example.NIO.buffer;

import java.io.*;

public class SmallBuffer {
    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            copy("c:/TestFile.doc", "c:/TestFile2.doc");
            long endTime = System.currentTimeMillis();
            System.out.println("SmallBuffer 처리시간 : " + (endTime - startTime) + " milli seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(String fileFrom, String fileTo) throws IOException {
        BufferedInputStream inBuf = null;
        BufferedOutputStream outBuf = null;
        try {
            inBuf = new BufferedInputStream(new FileInputStream(fileFrom), 2048);
            outBuf = new BufferedOutputStream(new FileOutputStream(fileTo), 2048);
            while (true) {
                int byteData = inBuf.read();
                if (byteData == -1) break;
                outBuf.write(byteData);
            }
        } finally {
            if (inBuf != null) inBuf.close();
            if (outBuf != null) outBuf.close();
        }
    }
}
