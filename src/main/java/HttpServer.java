import java.net.*;
import java.io.*;
import java.nio.file.Files;

public class HttpServer {
public static void main(String[] args) throws IOException {
        //System.out.println(MultiThread.getResource("descarga.png"));
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
		MultiThread multiThread = new MultiThread(clientSocket);
	        multiThread.start();
            } catch (IOException e) {
                System.err.println("Accept failed.");               
            }
            
        }
    }
}

class MultiThread extends Thread{
    Socket socket;
    public MultiThread(Socket socket){
        this.socket = socket;
    }
    static String getResource(String rsc) {
        String val = "";
        System.out.println(rsc);
        try {

            //InputStream i = cLoader.getResourceAsStream(rsc);
            InputStream i = Class.forName("MultiThread").getClassLoader().getResourceAsStream(rsc);
            BufferedReader r = new BufferedReader(new InputStreamReader(i));

            String l;
            while((l = r.readLine()) != null) {
                val = val + l;
            }
            i.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return val;
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
                    //File file = new File("public/" + inputLine);
                    //FileInputStream fis = new FileInputStream(file);
                    bytes = Files.readAllBytes(new File("public/" + inputLine).toPath());
                    //bytes = MultiThread.class.getResourceAsStream("/public/" + inputLine).;
                    //bytes = getResource(inputLine).getBytes();
                    //bytes = Files.readAllBytes(fis.)
                    result = "" + bytes.length;
                    format = "text/html";
                } else if (inputLine.endsWith(".png")) {
                    bytes = Files.readAllBytes(new File("public/" + inputLine).toPath());
                    //bytes = getResource(inputLine.replace("/","")).getBytes();
                    result = "" + bytes.length;
                    format = "image/png";
                } else if(inputLine.endsWith(".jpg")){
                    bytes = Files.readAllBytes(new File("public/" + inputLine).toPath());
                    //bytes = getResource(inputLine.replace("/","")).getBytes();
                    result = "" + bytes.length;
                    format = "image/jpg";
                }else {
                    bytes = Files.readAllBytes(new File("public/index.html").toPath());
                    //bytes = getResource("index.html").getBytes();
                    result = "" + bytes.length;
                    format = "text/html";
                }
            } else {
                bytes = Files.readAllBytes(new File("public/index.html").toPath());
                //bytes = getResource("index.html").getBytes();
                result = "" + bytes.length;
                format = "text/html";
            }
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: "
                    + format
                    + "\r\n\r\n"
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
