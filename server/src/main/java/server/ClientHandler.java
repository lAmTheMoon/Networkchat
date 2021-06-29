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

    public ClientHandler(Socket CLIENT_SOCKET, Server SERVER) {

        this.CLIENT_SOCKET = CLIENT_SOCKET;
        this.SERVER = SERVER;
        try {
            out = new BufferedWriter(new OutputStreamWriter(CLIENT_SOCKET.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (CLIENT_SOCKET;
             var in = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
             var writeOut = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            sendMessage("Connection established! Please, enter your name. Enter \"/exit\" to exit.", out);
            clientUser = new User(in.readLine());
            String message = processMessage("connected to chat.");
            while (true) {
                writeMessageToFile(message, writeOut);
                SERVER.sendMessageToClients(message);
                if (message.contains("/exit")) {
                    SERVER.removeClientOfSet(this);
                    break;
                }
                message = processMessage(in.readLine());
            }
        } catch (IOException e) {
            SERVER.removeClientOfSet(this);
            e.printStackTrace();
        } finally {
            shutDownStreams();
        }
    }

    private String processMessage(String message) {
        return "[" + DATE_FORMAT.format(new Date()) + "] " + clientUser.getNAME() + ": " + message;
    }

    public void sendMessage(String message, BufferedWriter out) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }

    private void writeMessageToFile(String message, PrintWriter writeOut) {
        writeOut.append(message).append("\n");
        writeOut.flush();
    }

    private void shutDownStreams() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedWriter getOut() {
        return out;
    }
}