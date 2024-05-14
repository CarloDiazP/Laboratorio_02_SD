public class Cliente extends Thread {
  private int id;
  private long reloj;
  private Berkeley monitor;

  public Cliente(int id, Berkeley monitor) {

    this.id = id;
    this.reloj = System.nanoTime();
    this.monitor = monitor;
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

      this.monitor.establecerDiferenciasTiempo(this.reloj, this.id);

      // actualizar reloj cliente
      this.reloj += this.monitor.getDiffTiempo(this.id);

      // aplicar algoritmo berkeley para sincronizar
      System.out.println("Reloj acordado de cliente " + id + ": " + this.reloj);

    }
  }
}