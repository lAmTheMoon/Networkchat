package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class OutMessageHandler implements Runnable {

    private BufferedReader buffered;
    private BufferedWriter out;

    public OutMessageHandler(BufferedReader buffered, BufferedWriter out) {
        this.buffered = buffered;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            String name = buffered.readLine();
            sendMessage(name, out);
            while (true) {
                String input = buffered.readLine();
                if ("/exit".equalsIgnoreCase(input)) {
                    sendMessage("/exit", out);
                    break;
                }
                sendMessage(input, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message, BufferedWriter out) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }
}