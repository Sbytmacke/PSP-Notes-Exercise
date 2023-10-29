package dev.sbytmacke;

import java.util.Random;

public class SumMatrixByThreads {
    // Con reales, no son de fiar, los decimales no son exacto
    // Si te pasas del número de CORES va peor, compiten los threads por la CPU
    public static void main(String[] args) {
        SumMatrixByThreads sumMatrixByThreads = new SumMatrixByThreads();
        int[][] matrix = sumMatrixByThreads.generateRandomMatrix(5000, 5000);
        long timeInit = System.currentTimeMillis();
        int numThreads = Runtime.getRuntime().availableProcessors() - 4;
        sumMatrixByThreads.sumMatrix(matrix, numThreads);
        System.out.println("Tiempo tardado: " + (System.currentTimeMillis() - timeInit));
        System.out.println("Para: " + numThreads + " threads");
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
        for (int posThread = 0; posThread < numThreads; posThread++) {
            rowsToStartAddInEachThread[posThread] = startRow;
            if (extraRows > 0) {
                // Sumamos el extraRow
                startRow = startRow + rowsPerThread + 1;
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
                matrix[i][j] = 1;//random.nextInt(10);
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
        Thread[] threads = new Thread[numThreads];
        for (int posThread = 0; posThread < numThreads; posThread++) {
            final int startRow = allRowsToStartSumByThread[posThread];
            final int endRow = (posThread == threads.length - 1) ? matrix.length - 1 : allRowsToStartSumByThread[posThread + 1] - 1;
            int indexThread = posThread; // Me pide hacer otra variable al meterlo en lambda
            threads[posThread] = new Thread(() -> {
                sum(matrix, startRow, endRow, totalSumsByThread, indexThread);
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

        // Imprimir la suma de cada Thread
        int totalSum = 0;
        for (int i = 0; i < numThreads; i++) {
            System.out.println("Suma del Thread " + (i + 1) + ": " + totalSumsByThread[i]);
            totalSum += totalSumsByThread[i];
        }
        System.out.println("Suma total: " + totalSum);
    }

    private long sum(int[][] matrix, int startRow, int endRow, long[] totalSumsByThread, int indexThread) {
        // Crear variables locales para la suma y volcar solamente al final del bucle

        long startTimeThread = System.currentTimeMillis();
        long sumTotal = 0;
        // Recorremos la matriz solo en las filas asignadas al hilo
        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sumTotal += matrix[i][j];
            }
        }
        long endTimeThread = System.currentTimeMillis();
        System.out.println("Tiempo tardado por Thread " + (indexThread + 1) + ": " + (endTimeThread - startTimeThread) + " milisegundos");

        totalSumsByThread[indexThread] = sumTotal;
        return (endTimeThread - startTimeThread);
    }
}
