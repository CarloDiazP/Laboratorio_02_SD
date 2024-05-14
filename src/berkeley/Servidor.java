
public class Servidor extends Thread {
  private long reloj;
  private final int tiempoSincronizacion = 2000;
  private Berkeley monitor;

  public Servidor(Berkeley monitor) {
    this.reloj = System.nanoTime();
    this.monitor = monitor;
  }

  public void run() {
    while (true) {
      try {
        Thread.sleep(this.tiempoSincronizacion);

        this.monitor.establecerTiempoServidor(this.reloj);

        this.monitor.calcularDiferenciasTiempos();

        this.reloj += this.monitor.obtenerPromedio();

        System.out.println("Reloj acordado servidor: " + this.reloj);

        this.monitor.reiniciar();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}