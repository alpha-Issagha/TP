import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class TCPClient {
    private String hostname;
    private int port;

    public TCPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void launch() {
        try {
            Socket socket = new Socket(hostname, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ASCII"), true);
            BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = userin.readLine()) != null) {
                out.println(line);
                System.out.println("Echo: " + in.readLine());
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java TCPClient <hostname> <port>");
            return;
        }
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        TCPClient client = new TCPClient(hostname, port);
        client.launch();
    }
}
