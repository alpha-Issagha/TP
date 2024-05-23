import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class MinMaxTCPServer {
    private int port;
    private ExecutorService executor;

    public MinMaxTCPServer(int port) {
        this.port = port;
        this.executor = Executors.newFixedThreadPool(10);
    }

    public void launch() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String min = null;
        private String max = null;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String line;
                while ((line = in.readLine()) != null) {
                    if (min == null || line.compareTo(min) < 0) {
                        min = line;
                    }
                    if (max == null || line.compareTo(max) > 0) {
                        max = line;
                    }
                    out.println("[ " + min + " .. " + max + " ]");
                }
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MinMaxTCPServer <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        MinMaxTCPServer server = new MinMaxTCPServer(port);
        server.launch();
    }
}
