package org.example.ExerciseQueue.models;

public class Task {
    private final int id;
    // Se refiere al tiempo en el que el proceso entra a la cola de prioridades
    private final int timeEnter;
    private int priority;
    // Se refiere al tiempo que el proceso necesita para ejecutarse por completo
    private int timeExecute;

    // Tiempo que el proceso se está ejecutando para aplicar envejecimiento
    private int agingTime = 0;

    public Task(int id, int priority, int timeExecute, int timeEnter) {
        this.id = id;
        this.priority = priority;
        this.timeExecute = timeExecute;
        this.timeEnter = timeEnter;
    }

    public int getId() {
        return id;
    }

    public int getTimeExecute() {
        return timeExecute;
    }

    public int getTimeEnter() {
        return timeEnter;
    }

    public int getPriority() {
        return priority;
    }

    public void reduceTimeExecute(int time) {
        timeExecute -= time;
    }

    public int getAgingTime() {
        return agingTime;
    }

    public void setAgingTime(int agingTime) {
        this.agingTime = agingTime;
    }

    public void increasePriority() {
        if (this.priority > 0) {
            this.priority--;
        }
    }

    public void increaseAgingTime() {
        this.agingTime++;
    }
}
