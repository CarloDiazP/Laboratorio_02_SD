import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Filipe
 */
public class Cliente {

    private String serverName;
    private int serverPort;
    private static int count;   
    private Timer timer;        
    private PrintWriter pr;   
    private long t0;     
    private long t3;      
    
    // Constructor
    public Cliente(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        Cliente.count = 0;
        this.timer = new Timer();
        try {
            this.pr = new PrintWriter("C:\\Users\\Filipe\\Desktop\\ClientTest.txt", "UTF-8");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    class Conversation extends TimerTask {
        // Overide run function in TimerTask
        @Override
        public void run() {
            if (count < 10) {
                try {
                    System.out.println("Conectando a ... " + serverName + " na porta " + serverPort);
                    
                    // Connect to the server
                    Socket client = new Socket(serverName, serverPort);
                    System.out.println("Conectado a " + client.getRemoteSocketAddress());
                
                    // Send message to server
                    OutputStream outToServer = client.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    t0 = System.currentTimeMillis();
                    out.writeUTF("Olá de " + client.getLocalSocketAddress());
                
                    // Receive message from server
                    InputStream inFromServer = client.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
                    long t1 = in.readLong();   // receive the the total time on server
                    long t2 = in.readLong();   // receive the sending time on server
                    t3 = System.currentTimeMillis();
                    
                    // Close the connection
                    client.close();
                    
                    // Increment count
                    count ++;
                    
                    //Insere uns tempos pra simular o delay nas msgs
                    //Set delay times to simulate the request/response delays
                    /**/
                    t1 += 100;
                    t2 += 150;
                    t3 += 250;
                    
                    
                    // Gets the RTT (Round trip delay time)
                    long rtt = (t3 - t0) - (t2 - t1);

                    pr.println("###########################################");
                    pr.println("Tempo Cliente Envio: " + t0); 
                    pr.println("Tempo do Servidor Recebimento: " + t1);
                    pr.println("Tempo do Servidor Envio: " + t2);
                    pr.println("Tempo Cliente Recebimento: " + t3);
                    
                    pr.println("*** RTT ***");
                    pr.println("a -> (t3 - t0) = " + (t3 - t0));
                    pr.println("b -> (t2 - t1) = " + (t2 - t1));
                    
                    pr.println("*** RTT divido por 2 ***");
                    pr.println("(a-b)/2 =  " + rtt / 2);

                    //RTT Offset
                    long theta = (t1 - t0) + (t2 - t3 ) / 2;
                    pr.println("*** RTT Offset ***");
                    pr.println("Theta -> (t1 - t0) + (t2 - t3 ) / 2 = " + theta); 
                    
                    
                    long cristianTime = t2 + (rtt / 2);
                    long cristianTimeComOffset = t2 + (theta);
                    pr.println("*** Horario de Cristian ***");
                    pr.println("Horario de Cristian -> t2 + (rtt/2): " + cristianTime); 
                    pr.println("Horario de Cristian -> t2 + (rtt_offset): " + cristianTimeComOffset); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                pr.close(); // flush to the file
                timer.cancel();
                timer.purge();
            }
        }
    }
    
    public static void main(String [] args) {
        
        //Server name
        String serverName = "localhost";
        
        //Server port
        int serverPort = 9092;
        
        //Cria um client que vai conecar no servidor
        Cliente client = new Cliente(serverName, serverPort);
        
        //tmepo que o objeto Timer vai fazer as conexoes
        long period = 6000;
        
        //Instancia a classe Conversation
        Cliente.Conversation  conversation = client.new Conversation();
        
        client.timer.schedule(conversation, 0, period);
    }
}

