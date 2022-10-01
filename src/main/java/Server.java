import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8989;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            String message1 = "???";
            String message2;
            char letter1;
            char letter2;
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    out.println(message1); // город который был внесен

                    // не первое подключение
                    if (!message1.equals("???")) {
                        letter1 = message1.charAt(message1.length() - 1); //последняя буква
                        message2 = in.readLine(); // город, который внес клиент
                        letter2 = message2.charAt(0); // первая буква
                        if (letter2 == letter1) {
                            message1 = message2;
                            out.println("OK");
                        } else {
                            out.println("NOT OK");
                        }
                    } else {
                        // первое подключение
                        message1 = in.readLine(); // город, который внес клиент
                        out.println("OK");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}