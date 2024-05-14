public class Main {
  public static void main(String[] args) {
    Berkeley berkeley = new Berkeley();
    // Crear un hilo servidor e iniciarlo //
    Servidor servidor = new Servidor(berkeley);
    servidor.start();

    Cliente clientes[] = new Cliente[3];
    // Crear hilos clientes e iniciarlos //
    for (int i = 0; i < 3; i++) {
      clientes[i] = new Cliente(i, berkeley);
      berkeley.addCliente(clientes[i]);
    }

    for (int i = 0; i < 3; i++) {
      clientes[i].start();
    }
  }
}