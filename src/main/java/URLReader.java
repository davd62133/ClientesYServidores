import java.io.*;
import java.net.*;

public class URLReader {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.google.com.co/search?q=google&oq=google&aqs=chrome..69i57j0l5.1535j0j7&sourceid=chrome&ie=UTF-8#69i57j0l5");

        //Ejercicio1

        System.out.println("Protocol: " + url.getProtocol());
        System.out.println("Authority: " + url.getAuthority());
        System.out.println("Host: " + url.getHost());
        System.out.println("Port: " + url.getPort());
        System.out.println("Path: " + url.getPath());
        System.out.println("Query: " + url.getQuery());
        System.out.println("File: " + url.getFile());
        System.out.println("Ref: " + url.getRef());

        //Ejercicio2
        url = new URL("http://www.google.com");
        String html = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {
                html += inputLine;
                }
            }catch (IOException x) {
                System.err.println(x);
            }
        String ruta = "/home/2123162/IdeaProjects/ClientesYServicios/resultados.html";
        File file = new File(ruta);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = null;
        fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(html);
        bw.close();


    }


}