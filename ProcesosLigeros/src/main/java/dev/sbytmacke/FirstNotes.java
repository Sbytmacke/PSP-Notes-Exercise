package dev.sbytmacke;

import java.util.concurrent.ThreadLocalRandom;

public class FirstNotes {
    int sum = 0; // Compartir la variable de escritura con Threads en un problema de sincronización

    public static void main(String[] args) {
        // Concurrencia: ejecutar varias tareas en un mismo hilo

        // Paralelismo: ejecutar varias tareas al mismo tiempo, el MAIN es un thread

        /* Cada thread tiene su propia pila de ejecución, las variables locales son propias de cada Thread,
         los objetos están en el Heap (montículo) y son comunes, y los mismos atributos estáticos*/

        // Dos formas: Interfaz runnable o crear un Thread
        // 1. Interfaz Runnable, con lambda
        Runnable runnable = () -> {
            System.out.println("Hola desde un thread con runnable");
            System.out.println("Nombre del thread: " + Thread.currentThread().getName());
        };
        Thread thread = new Thread(runnable);
        thread.setName("Thread con runnable");

        // 2. Crear un Thread sin runnable, con lambda
        Thread thread2 = new Thread(() -> {
            System.out.println("Hola desde un thread sin runnable");
            System.out.println("Nombre del thread: " + Thread.currentThread().getName());
        });
        thread2.setName("Thread sin runnable");

        // Ejecutar los threads
        thread.start();
        thread2.start();


        // Herencia de la clase Thread, sin lambda
        MyThread thread3 = new MyThread("Hilo heredado");
        thread3.setName(thread3.getRandomValue());
        thread3.start();

        // El main debe esperar la finalización, como el waitFor() en procesos pesados
        try {
            // Detiene temporalmente la ejecución del thread actual
            Thread.yield(); // Permite que el hilo actual pase el control a otro hilo que esté en espera.
            Thread.yield();
            // Espera a que el hilo actual muera, podemos poner que espere como mucho con X milisegundos,
            // es una operación bloqueante de su padre, no pasa nada si ha muerto antes el hilo, no bloquearía nada
            thread.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* BUFFER OPTIMIZACIÓN */
        // Escribir en memoria principal, estableciendo un tamaño, corremos el riesgo de que no se guarde si se apaga la luz, pero es más rapido
        // que un buffer normal, ya que es buffer normal lee por línea y escribe al momento.

        // Random numérico específico para threads, porque si no, se repite el mismo valor en todos los threads
        System.out.println("Número aleatorio: " + ThreadLocalRandom.current().nextInt(0, 100 + 1));
    }
}
