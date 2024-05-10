import java.util.ArrayList;

public class Servidor extends Thread {
  private long reloj;
  private ArrayList<Cliente> clientes;
  private final int tiempoSincronizacion = 2000;
  private long[] diffTiempos;
  private long diffMedia;

  public Servidor() {
    this.clientes = new ArrayList<Cliente>();
    this.reloj = System.nanoTime();
    this.diffTiempos = new long[clientes.size()];
  }

  public void addCliente(Cliente cliente) {
    this.clientes.add(cliente);
  }

  private void sincronizarClientes() {
    long suma = 0;
    
    for (int i = 0; i < clientes.size(); i++) {
      this.diffTiempos[i] = clientes.get(i).getReloj() - this.reloj;
    }
    
    for (int i = 0; i < diffTiempos.length; i++) {
      suma += diffTiempos[i];
    }
    
    diffMedia = suma / clientes.size() + 1;
    
    // establecer nuevo tiempo del servidor
    this.reloj = this.reloj + this.diffMedia;

    for (int i = 0; i < clientes.size(); i++) {
      clientes.get(i).setReloj(clientes.get(i).getReloj() - (diffTiempos[i] + diffMedia));
    }
  }

  public void run() {
    while (true) {
      try {
        Thread.sleep(this.tiempoSincronizacion);

        sincronizarClientes();

        
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}