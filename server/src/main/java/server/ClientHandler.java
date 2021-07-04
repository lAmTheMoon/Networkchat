package server;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {

    private final File LOG_FILE = new File("server", "file.log");
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private final Socket CLIENT_SOCKET;
    private final Server SERVER;
    private User clientUser;
    private BufferedWriter out;
    private BufferedReader in;
    private PrintWriter writeOut;

    public ClientHandler(Socket clientSocket, Server server) {
        this.CLIENT_SOCKET = clientSocket;
        this.SERVER = server;
        try {
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writeOut = new PrintWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            sendMessage("Connection established! Please, enter your name. Enter \"/exit\" to exit.");
            clientUser = new User(in.readLine());
            String message = processMessage("connected to chat.");
            while (true) {
                writeMessageToFile(message);
                SERVER.sendMessageToClients(message);
                if (message.contains("/exit")) {
                    SERVER.removeClientFromQueue(this);
                    break;
                }
                message = processMessage(in.readLine());
            }
        } catch (IOException e) {
            SERVER.removeClientFromQueue(this);
            e.printStackTrace();
        } finally {
            shutDownStreams();
        }
    }

    private String processMessage(String message) {
        return "[" + DATE_FORMAT.format(new Date()) + "] " + clientUser.getNAME() + ": " + message;
    }

    public void sendMessage(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }

    private void writeMessageToFile(String message) {
        writeOut.append(message).append("\n");
        writeOut.flush();
    }

    private void shutDownStreams() {
        try {
            CLIENT_SOCKET.close();
            out.close();
            in.close();
            writeOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}