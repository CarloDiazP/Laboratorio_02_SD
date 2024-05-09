public class Cliente extends Thread {
  private int id;
  private long reloj;
  private Servidor servidor;

  public Cliente(int id, Servidor servidor) {
    this.id = id;
    this.reloj = System.nanoTime();
    this.servidor = servidor;
    servidor.addCliente(this);
  }

  public long getReloj() {
    return reloj;
  }

  public void setReloj(long reloj) {
    this.reloj = reloj;
  }

  public void run() {
    while (true) {
      // simular desfase
      this.reloj += ((Math.random() * 10) * 2) + 1;
      System.out.println("Reloj de cliente " + id + ": " + this.reloj);

      // aplicar algoritmo berkeley para sincronizar
      System.out.println("Reloj acordado de cliente " + id + ": " + this.reloj);
    }
  }
}