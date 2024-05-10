package lamport;

// Se importa librerías de estructuras de datos de Java
import java.util.ArrayList; // Class
import java.util.List; // Interface

public class LamportClock {
    private int clock;

    public LamportClock() {
        this.clock = 0; // Estado inicial del reloj
    }

    public synchronized int tick() {
        this.clock++; // Aumenta en uno el reloj y lo retorna
        return this.clock;
    }

    public synchronized void update(int receivedTime) {
        // Actualiza el tiempo del reloj con el máximo entre el tiempo
        // actual y el tiempo recibido como argumento más 1.
        this.clock = Math.max(this.clock, receivedTime) + 1;
    }

    public int getTime() {
        // Retorna el valor del reloj actual
        return this.clock;
    }

    public static void main(String[] args) {
        // Se crea una lista de hilos para simular concurrencia
        List<Thread> threads = new ArrayList<>();
        // Se instancia el reloj
        LamportClock clock = new LamportClock();
        // Se crearán 5 hilos
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Obtenemos el tiempo actual
                    int time = clock.tick();

                    System.out.println(
                            // "Thread " + Thread.currentThread().getId() + " created event with Lamport
                            // time " + time);
                            // En versiones java +19 se usa threadId() en lugar de getId()
                            "Thread " + Thread.currentThread().threadId() + " created event with Lamport time " + time);
                    try {
                        // Simulamos un tiempo de espera aleatorio
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Obtenemos el tiempo actualizado
                    int receivedTime = clock.tick();
                    // En versiones java +19 se usa threadId() en lugar de getId()
                    System.out.println(
                            "Thread " + Thread.currentThread().threadId() + " received event with Lamport time "
                                    + receivedTime);
                    // System.out.println("Thread " + Thread.currentThread().getId() + " received
                    // event with Lamport time "
                    // + receivedTime);
                    // Actualizamos el tiempo del reloj
                    clock.update(receivedTime);
                }
            });
            // Agregamos el hilo a la lista de hilos
            threads.add(thread);
            // Iniciamos el hilo
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                // Esperamos a que todos los hilos terminen
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final Lamport time: " + clock.getTime());
    }
}