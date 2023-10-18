package dev.sbytmacke;

public class MyThread extends Thread {
    private final String something;
    private Integer id;

    public MyThread(String something) {
        this.something = something;
    }

    public String getRandomValue() {
        return something;
    }

    @Override
    public void run() {
        if (id == 1) {
            setPriority(MAX_PRIORITY);
        }

        System.out.println("Hola desde un thread heredado");
        System.out.println("Nombre del thread: " + Thread.currentThread().getName());
    }
}
