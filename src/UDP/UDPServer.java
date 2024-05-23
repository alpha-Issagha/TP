import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private int port;

    public UDPServer(int port) {
        this.port = port;
    }

    public void launch() {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            while (true) {
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength(), "ISO-8859-15");
                System.out.println("Received from " + packet.getAddress() + ": " + received);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java UDPServer <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        UDPServer server = new UDPServer(port);
        server.launch();
    }
}
