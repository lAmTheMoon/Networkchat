package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private final int PORT;
    private final ExecutorService POOL;
    private BlockingQueue<ClientHandler> clients = new LinkedBlockingQueue<>();

    public Server(int port, int poolSize) {
        this.PORT = port;
        this.POOL = Executors.newFixedThreadPool(poolSize);
    }

    public void start() {
        try (var serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is ready!");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                POOL.execute(clientHandler);
            }
        } catch (IOException e) {
            POOL.shutdown();
            e.printStackTrace();
        }
    }

    public void sendMessageToClients(String message) throws IOException {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClientFromQueue(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}