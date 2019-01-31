package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        //Ejercico 3
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(35000);
        }catch (IOException e){
            System.err.println("No se pudo escuchar en el puerto 35000");
            System.exit(1);
        }

        Socket clientSocket = null;
        try{
            clientSocket = serverSocket.accept();
        }catch (IOException e){
            System.err.println("No se pudo aceptar");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null){
            System.out.println("Mensaje:" + inputLine);
            if(inputLine.contains("fun") && inputLine.contains("sin")) {
                outputLine = "Respuesta: "+ Math.sin(Double.parseDouble(inputLine.substring(8)));
                out.println(outputLine);
            }
            else if(inputLine.contains("fun") && inputLine.contains("tan")){
                outputLine = "Respuesta: "+ Math.tan(Double.parseDouble(inputLine.substring(8)));
                out.println(outputLine);
            }else{
                out.println("Respuesta: " + Math.cos(Double.parseDouble(inputLine)));
            }
            /**try{
                int num = Integer.parseInt(inputLine);
                System.out.println(num);
                outputLine = "Respuesta: " + Math.pow(num,2);
                out.println(outputLine);
            }catch (Exception e){
                outputLine = "Respuesta: " + inputLine;
                out.println(outputLine);
                if (outputLine.equals("Respuestas: Bye.")) break;
            }**/
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
