package dev.sbytmacke.services;

public class ThreadSum implements Runnable {
    private final int[][] matrix;
    private int startRow;
    private int endRow;
    private long localSum;

    public ThreadSum(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public void run() {
        sumMatrix();
    }

    private void sumMatrix() {
        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                localSum += matrix[i][j];
            }
        }
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getLocalSum() {
        return localSum;
    }
}
