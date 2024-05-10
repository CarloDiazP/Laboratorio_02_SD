import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClienteCristian {

    public static void main(String[] args) throws IOException {
        String nombreHost;
        int puerto;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Ingrese el nombre del host: ");
        nombreHost = buffer.readLine();
        System.out.println("Ingrese el puerto: ");
        puerto = Integer.parseInt(buffer.readLine());

        try {
            Socket socket = new Socket(nombreHost, puerto);
            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            {

                System.out.println("Cliente iniciado");
                System.out.println("Ingrese SALIR para terminar la conexión");

                long tiempoCero;
                long tiempoServidor;
                long tiempoInicio;
                long tiempoFinal;

                escritor.println(tiempoCero = System.currentTimeMillis());
                tiempoServidor = Long.parseLong(lector.readLine());
                tiempoInicio = System.currentTimeMillis();
                tiempoFinal =  (tiempoServidor +  (tiempoInicio - tiempoCero))/ 2;
                DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");

                System.out.println("Tiempo del cliente: " + dateFormat.format(new Date(tiempoInicio)));
                System.out.println("Tiempo del servidor: " + dateFormat.format(new Date(tiempoServidor)));
                System.out.println("Tiempo del cliente despues del reinicio:" + dateFormat.format(new Date(tiempoFinal)));
                escritor.println("SALIR");
                socket.close();

            }

        } catch (Exception e) {
            System.out.println("Tiempo del servidor agotado");
           
        }

    }
    

    
}

