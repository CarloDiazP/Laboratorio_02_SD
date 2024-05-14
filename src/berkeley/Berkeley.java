import java.util.ArrayList;

public class Berkeley {
  private ArrayList<Cliente> clientes;
  private long relojServer;
  private ArrayList<Long> diffTiempo;
  private long sumDiffs;
  private int contadorClientes = 0;

  Berkeley() {
    this.clientes = new ArrayList<Cliente>();
    this.diffTiempo = new ArrayList<Long>();
  }

  public void addCliente(Cliente cliente) {
    this.clientes.add(cliente);
    this.diffTiempo.add(0L);
    this.contadorClientes++;
  }

  public synchronized void establecerTiempoServidor(long reloj) {
    this.relojServer = reloj;
    try {
      notifyAll(); // los clientes siguen con su reloj
      wait(); // el servidor se pone a esperar
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public synchronized void establecerDiferenciasTiempo(long reloj, int id) {

    try {
      if (relojServer == 0)
        wait();
      this.diffTiempo.set(id, reloj - relojServer);
      this.sumDiffs += reloj;
      contadorClientes--;
      if (contadorClientes == 0) {
        notify(); // si se sincronizaron los clientes el servidor continua
      }
      wait();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  public synchronized void calcularDiferenciasTiempos() {
    long media = (this.sumDiffs / (this.clientes.size() + 1));  // algoritmo Berkeley
    for (int i = 0; i < this.clientes.size(); i++)
      this.diffTiempo.set(i, (-this.diffTiempo.get(i) + media));

    notifyAll(); // se notifica a todos los clientes
  }

  public synchronized long obtenerPromedio() {
    return this.sumDiffs / (this.clientes.size() + 1); // cálculo para establecer el tiempo de sincronizacion con el reloj del servidor
  }

  public synchronized long getDiffTiempo(int n) { // cálculo para sincronizar el reloj del cliente con el del servidor
    return this.diffTiempo.get(n);
  }

  public synchronized void reiniciar() { // reiniciar programa
    this.relojServer = 0;
    this.contadorClientes = this.clientes.size();
    this.sumDiffs = 0;
  }
}
