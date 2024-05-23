import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UDPClient {
    private String hostname;
    private int port;

    public UDPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void launch() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(hostname);
            BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = userin.readLine()) != null) {
                byte[] buf = line.getBytes("ISO-8859-15");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java UDPClient <hostname> <port>");
            return;
        }
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        UDPClient client = new UDPClient(hostname, port);
        client.launch();
    }
}
