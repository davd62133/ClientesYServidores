import java.net.*;
import java.io.*;
import java.nio.file.Files;

public class HttpServer {
public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(new Integer(System.getenv("PORT")));
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        while (true) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            MultiThread multiThread = new MultiThread(clientSocket);
            multiThread.start();
        }
    }
}

class MultiThread extends Thread{
    Socket socket;
    public MultiThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine = in.readLine();
            String outputLine;
            String format;
            String result;
            byte[] bytes = null;
            if (inputLine != null) {
                inputLine = inputLine.split(" ")[1];
                if (inputLine.endsWith(".html")) {
                    bytes = Files.readAllBytes(new File("./" + inputLine).toPath());
                    result = "" + bytes.length;
                    format = "text/html";
                } else if (inputLine.endsWith(".png")) {
                    bytes = Files.readAllBytes(new File("./" + inputLine).toPath());
                    result = "" + bytes.length;
                    format = "image/png";
                } else if(inputLine.endsWith(".jpg")){
                    bytes = Files.readAllBytes(new File("./" + inputLine).toPath());
                    result = "" + bytes.length;
                    format = "image/jpg";
                }else {
                    bytes = Files.readAllBytes(new File("./index.html").toPath());
                    result = "" + bytes.length;
                    format = "text/html";
                }
            } else {
                bytes = Files.readAllBytes(new File("./index.html").toPath());
                result = "" + bytes.length;
                format = "text/html";
            }
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: "
                    + format
                    + "\r\n"
                    + result
                    + "\r\n\r\n";

            byte[] hByte = outputLine.getBytes();
            byte[] rta = new byte[bytes.length + hByte.length];
            for (int i = 0; i < hByte.length; i++) {
                rta[i] = hByte[i];
            }
            for (int i = hByte.length; i < hByte.length + bytes.length; i++) {
                rta[i] = bytes[i - hByte.length];
            }
            socket.getOutputStream().write(rta);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}