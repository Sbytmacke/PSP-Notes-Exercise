package dev.sbytmacke;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AddMatrix {
    // Con reales, no son de fiar, los decimales no son exacto
    // Si te pasas del número de CORES va peor, compiten los threads por la CPU
    // Indicamos a cada hilo la fila inicial por la que debe sumar
    public static void main(String[] args) {
        AddMatrix addMatrix = new AddMatrix();
        int[][] matrix = addMatrix.generateRandomMatrix(1000, 1000);
        addMatrix.sumMatrix(matrix, 2);
        System.out.println("--------------------------------------------------");
        addMatrix.sumMatrix(matrix, 4);
        System.out.println("--------------------------------------------------");
        addMatrix.sumMatrix(matrix, 8);
        System.out.println("--------------------------------------------------");
        addMatrix.sumMatrix(matrix, 16);
    }

    private int[] divideAndConquer(int[][] matrix, int numThreads) {
        // Recorrer la matriz, y sumar los valores de la misma, repartiendose el trabajo
        // Cada thread recorrerá una parte de la matriz, sin chocarse entre ellos y sumará los valores de la misma
        int[] rowsToStartAddInEachThread = new int[numThreads]; // Cada índice es la fila donde empezará el Thread a sumar

        int rowsPerThread = matrix.length / numThreads; // Filas repartidas equitativamente
        int extraRows = matrix.length % numThreads; // Filas sobrantes que no se han repartido

        System.out.println("Filas por Thread: " + rowsPerThread);
        System.out.println("Filas extras: " + extraRows);

        int startRow = 0;
        for (int i = 0; i < numThreads; i++) {
            rowsToStartAddInEachThread[i] = startRow;
            if (extraRows > 0) {
                // Sumamos el extraRow
                startRow = startRow + rowsPerThread + extraRows;
                extraRows--;
            } else {
                startRow = startRow + rowsPerThread;
            }
        }
        return rowsToStartAddInEachThread;
    }

    public int[][] generateRandomMatrix(int rows, int columns) {
        // Creamos una matriz de números enteros, de 500x500
        int[][] matrix = new int[rows][columns]; // Recogemos los datos con Threads, para ver lo rápido que es
        // Rellenar con números aleatorios
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }

    public void sumMatrix(int[][] matrix, int numThreads) {
        int[] allRowsToStartSumByThread = divideAndConquer(matrix, numThreads);

        // Iniciamos todos a 0 la suma de cada Thread
        // Suma local de cada Thread, cada índice es la suma de un Thread, en LONG para no ocasionar desbordamiento
        long[] totalSumsByThread = new long[numThreads];

        // Crear los threads
        AtomicLong totalTimeSumming = new AtomicLong(0L);
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int startRow = allRowsToStartSumByThread[i];
            final int endRow = (i == numThreads - 1) ? matrix.length - 1 : allRowsToStartSumByThread[i + 1] - 1;
            int indexThread = i;
            threads[i] = new Thread(() -> {
                long threadTime = sum(matrix, startRow, endRow, totalSumsByThread, indexThread);
                totalTimeSumming.addAndGet(threadTime);
            });
        }

        // Ejecutamos los threads
        for (Thread thread : threads) {
            thread.start();
        }
        // Padre espera a que todos los threads terminen
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Tiempo total tardado: " + totalTimeSumming.get() + " milisegundos");

        // Imprimir la suma de cada Thread
        int totalSum = 0;
        for (int i = 0; i < numThreads; i++) {
            System.out.println("Suma del Thread " + (i + 1) + ": " + totalSumsByThread[i]);
            totalSum += totalSumsByThread[i];
        }
        System.out.println("Suma total: " + totalSum);
    }

    private long sum(int[][] matrix, int startRow, int endRow, long[] totalSumsByThread, int indexThread) {
        long startTimeThread = System.currentTimeMillis();
        // Recorremos la matriz solo en las filas asignadas al hilo
        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                totalSumsByThread[indexThread] += matrix[i][j];
            }
        }
        long endTimeThread = System.currentTimeMillis();
        System.out.println("Tiempo tardado por Thread " + (indexThread + 1) + ": " + (endTimeThread - startTimeThread) + " milisegundos");

        return (endTimeThread - startTimeThread);
    }

}
