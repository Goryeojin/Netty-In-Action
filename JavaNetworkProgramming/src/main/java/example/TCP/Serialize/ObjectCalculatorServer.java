package example.TCP.Serialize;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ObjectCalculatorServer {
    public static void main(String[] args) {

        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            ServerSocket server = new ServerSocket(10001);
            System.out.println("클라이언트 접속 대기");
            socket = server.accept();

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            Object obj = null;
            Outer:
            while((obj = ois.readObject()) != null) {
                SendData data = (SendData) obj;
                int op1 = data.getOp1();
                int op2 = data.getOp2();
                String opcode = data.getOpcode();
                int result = 0;
                switch (opcode) {
                    case "+" :
                        result = op1 + op2;
                        break;
                    case "-" :
                        result = op1 - op2;
                        break;
                    case "*" :
                        result = op1 * op2;
                        break;
                    case "/" :
                        if (op2 == 0) {
                            oos.writeObject("0으로 나눌 수 없습니다.");
                            oos.flush();
                            continue Outer;
                        } else
                            result = op1 / op2;
                        break;
                    default :
                        oos.writeObject("잘못된 연산자를 입력하셨습니다.");
                        oos.flush();
                        continue Outer;
                }
                oos.writeObject(op1 + opcode + op2 + " = " + result);
                oos.flush();
                System.out.println("결과를 전송했습니다.");
            }
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
