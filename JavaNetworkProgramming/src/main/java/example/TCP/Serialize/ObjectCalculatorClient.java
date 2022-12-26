package example.TCP.Serialize;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.NumberFormat;

public class ObjectCalculatorClient {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("사용법: java ObjectCalculatorClient.java ip");
            System.exit(0);
        }
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            socket = new Socket(args[0], 10001);

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String line = null;

            while (true) {
                System.out.println("첫 번째 숫자를 입력해주세요.");
                line = input.readLine();
                int op1 = 0;
                try {
                    op1 = Integer.parseInt(line);
                } catch (NumberFormatException nfe) {
                    op1 = 0;
                }
                System.out.println("두 번째 숫자를 입력해주세요.");
                line = input.readLine();
                int op2 = 0;
                try {
                    op2 = Integer.parseInt(line);
                } catch (NumberFormatException nfe) {
                    op2 = 0;
                }
                System.out.println("+, -, *, / 중 하나를 입력하세요.");
                line = input.readLine();
                String opcode = "+";
                if (line.equals("+") || line.equals("-") || line.equals("*") || line.equals("/")) {
                    opcode = line;
                }
                else {
                    opcode = "+";
                }
                SendData data = new SendData(op1, op2, opcode);
                oos.writeObject(data);
                oos.flush();
                String msg = (String)ois.readObject();
                System.out.println(msg);
                System.out.println("계속 하시겠습니까? (Y/n)");
                line = input.readLine();
                if (line.equals("n")) break;
                System.out.println("다시 계산을 시작합니다.");
            }
            System.out.println("프로그램을 종료합니다.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (oos != null) oos.close();
                if (ois != null) ois.close();
                if (socket != null) socket.close();
            } catch (Exception ignored) {}
        }
    }
}
