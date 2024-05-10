
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorCristian {
    public static void main(String[] args) throws IOException{

        int puerto;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese el puerto: ");

        puerto = Integer.parseInt(buffer.readLine());

        try{
            ServerSocket servidor = new ServerSocket(puerto);
            Socket cliSocket = servidor.accept();
            PrintWriter escritor = new PrintWriter(cliSocket.getOutputStream(), true);
            BufferedReader lector = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
            {
                String inputLine;
                System.out.println("Servidor iniciado");
                while (true) {
                    inputLine = lector.readLine();
                    if (inputLine.equals("SALIR")) {
                        System.out.println("Conexión terminada");
                        escritor.println("Saliendo del servidor");
                        break;
                    }

                    escritor.println(System.currentTimeMillis() + 5000);
                    
                }

                servidor.close();
            }


        } catch (Exception e) {
            System.out.println("Error en el servidor");
        }
        
    }

}
