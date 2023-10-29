package dev.sbytmacke.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SumMatrixExecutor {
    // Con reales, no son de fiar, los decimales no son exacto
    // Si te pasas del número de CORES va peor, compiten los threads por la CPU
    public static void main(String[] args) throws InterruptedException {
        SumMatrixExecutor sumMatrixByExecutor = new SumMatrixExecutor();

        int[][] matrix = sumMatrixByExecutor.generateRandomMatrix(5000, 5000);

        int numThreads = 7;//Runtime.getRuntime().availableProcessors() - 4;

        sumMatrixByExecutor.sumMatrix(matrix, numThreads);
    }

    public void sumMatrix(int[][] matrix, int numThreads) throws InterruptedException {

        ExecutorService servicio = Executors.newCachedThreadPool();

        // Crear la pool de threads
        List<ThreadSum> poolThreads = new ArrayList<>();

        for (int posThread = 0; posThread < numThreads; posThread++) {
            poolThreads.add(new ThreadSum(matrix));
        }

        // Establecemos las filas de inicio y fin de cada Thread
        setStartAndEndRowsToSumEachThread(matrix, poolThreads);

        // Realiza el run de la tarea de cada Thread
        long timeInit = System.currentTimeMillis();
        poolThreads.forEach(servicio::execute);
        System.out.println("Tiempo total tardado: " + (System.currentTimeMillis() - timeInit) + " milisegundos");
        System.out.println("Para: " + numThreads + " threads");

        // Termina cuando todos los hilos han terminado
        servicio.shutdown();

        // Esperar a que terminen todos los Threads
        Thread.sleep(1000);

        if (servicio.awaitTermination(1, TimeUnit.SECONDS)) {
            long totalSum = 0L;
            for (ThreadSum thread : poolThreads) {
                totalSum += thread.getLocalSum();
            }
            System.out.println("Suma total: " + totalSum);
        }
    }

    private void setStartAndEndRowsToSumEachThread(int[][] matrix, List<ThreadSum> listThreads) {

        int rowsPerThread = matrix.length / listThreads.size(); // Filas repartidas equitativamente
        int extraRows = matrix.length % listThreads.size(); // Filas sobrantes que no se han repartido

        System.out.println("Filas por Thread: " + rowsPerThread);
        System.out.println("Filas extras: " + extraRows);

        int startRow = 0;
        for (int posThread = 0; posThread < listThreads.size(); posThread++) {
            // Inicio de la fila
            System.out.print("Thread " + (posThread + 1) + " empieza en fila " + startRow);
            listThreads.get(posThread).setStartRow(startRow);

            // Si hay filas extras, se las repartimos a los threads
            if (extraRows > 0) {
                startRow = startRow + rowsPerThread + 1;
                extraRows--;
            } else {
                // Si no hay filas extras, se reparten equitativamente
                startRow = startRow + rowsPerThread;
            }

            // Fin de la fila
            int endRow = startRow - 1;
            System.out.print(" y acaba en fila " + endRow + "\n");
            listThreads.get(posThread).setEndRow(endRow);
        }
    }

    public int[][] generateRandomMatrix(int rows, int columns) {
        int[][] matrix = new int[rows][columns];

        // Rellenar con números aleatorios
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 1;//random.nextInt(10);
            }
        }
        return matrix;
    }
}
