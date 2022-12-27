package example.NIO.buffer;

import java.io.*;

public class NonBuffer {
    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            copy("c:/TestFile.doc", "c:/TestFile2.doc");
            long endTime = System.currentTimeMillis();
            System.out.println("NonBuffer 처리시간 : " + (endTime - startTime) + " milli seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(String fileFrom, String fileTo) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(fileFrom);
            out = new FileOutputStream(fileTo);
            while (true) {
                int byteData = in.read();
                if (byteData == -1) break;
                out.write(byteData);
            }
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }
}
