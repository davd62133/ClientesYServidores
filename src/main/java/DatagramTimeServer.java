import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatagramTimeServer {

    DatagramSocket socket;

    public DatagramTimeServer() {
        try {
            socket = new DatagramSocket(4445);
            } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void startServer() {
            byte[] buf = new byte[256];
            try {
                while(true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String dString = new Date().toString();
                    buf = dString.getBytes();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                    //Espera 5 segundos
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        socket.close();
    }
    public static void main(String[] args){
        DatagramTimeServer ds = new DatagramTimeServer();
        ds.startServer();
        }
}
