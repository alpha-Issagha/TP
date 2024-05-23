import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TCPServer {
    private int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public void launch() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Received: " + line);
                    out.println(line);
                }
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TCPServer <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        TCPServer server = new TCPServer(port);
        server.launch();
    }
}
