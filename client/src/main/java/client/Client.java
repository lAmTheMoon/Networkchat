package client;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;

public class Client {

    private final String HOST;
    private final int PORT;
    private final File LOG_FILE = new File("client", "file.log");

    public Client(String host, int port) {
        this.HOST = host;
        this.PORT = port;
    }

    public void start() {
        try (var clientSocket = new Socket(Inet4Address.getByName(HOST), PORT);
             var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             var out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
             var writeOut = new PrintWriter(new FileWriter(LOG_FILE, true));
             var buffered = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server connection...");
            System.out.println(in.readLine());
            new Thread(new OutMessageHandler(buffered, out)).start();
            while (true) {
                String serverMessage = in.readLine();
                System.out.println(serverMessage);
                writeMessageToFile(serverMessage, writeOut);
                if (serverMessage.contains("/exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMessageToFile(String message, PrintWriter writeOut) {
        writeOut.append(message).append("\n");
        writeOut.flush();
    }
}