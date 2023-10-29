package dev.sbytmacke;

import java.util.concurrent.*;
import java.util.stream.Stream;

public class ThreadForService {
    public static void main(String[] args) {
        // No hay que bloquear el main
        // Cada vez que llega una peticion hay que crear un nuevo thread

        // Para los ataques de denegación de servicio, se limita el número de threads que se pueden crear
        // Se puede hacer con un pool de threads, que se reutilizan, o con un pool de threads que se crean y se destruyen

        // 1. Crear un pool de threads, y después se le van asignando tareas
        Stream<String> pool = Stream.of("tarea1", "tarea2", "tarea3");

        // Pool de tareas asíncronas, no bloqueantes
        ExecutorService servicio = Executors.newCachedThreadPool(); // Esto con factoria de threads
        // ExecutorService servicio2 = Executors.newFixedThreadPool(10); // Elegimos 10 threads, con un buffer de threads

        pool.map(tarea -> new Runnable() { // Runnable = tareas (Para THREADS)
            @Override
            public void run() {
                System.out.println("Ejecutando tarea: " + tarea);
            }
        }).forEach(servicio::execute); // submit o execute para ejecutar la tarea
        // A. Submit: devuelve un Future, que es un objeto que representa el resultado de la tarea
        // B. Execute: no devuelve nada, no se puede saber si ha ido bien o mal

        servicio.shutdown(); // Si queremos que acaba la escucha del servicio, pero solo cuando acaben las tareas de su pool
        // servicio.shutdownNow(); // Para parar el servicio, y que no acabe las tareas que tiene pendientes

        // Tareas periódicas
        ScheduledExecutorService servicioPeriodico = Executors.newScheduledThreadPool(10);
        //servicioPeriodico.scheduleAtFixedRate();

        // Con la interfaz Executors, que no es ExecutorService, podemos crear tareas

        // Los callable son como los runnables, pero devuelven un valor (Para TAREAS)
        Callable<String> tarea = () -> {
            System.out.println("Ejecutando tarea callable");
            return "Hola";
        };

        // Futuro, son tareas que se ejecutan en un futuro, no se sabe cuando, pero se ejecutan
        Future<String> futuro = servicio.submit(tarea); // Para ejecutar la tarea

        // OpenMP -> Librería (C y C++), para paralelismo (te reparte por la cara cada thread que tiene que hacer)
    }
}
