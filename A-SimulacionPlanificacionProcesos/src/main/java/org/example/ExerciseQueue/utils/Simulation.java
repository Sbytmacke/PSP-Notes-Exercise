package org.example.ExerciseQueue.utils;

import org.example.ExerciseQueue.models.Queue;
import org.example.ExerciseQueue.models.QueuePriorities;
import org.example.ExerciseQueue.models.Task;

public class Simulation {
    public static void simulationRoundRobin(QueuePriorities<Task> queuesPriorities, int sliceTimeQuantum) {
        Queue<Task> ioQueue = new Queue<>(); // Cola para las tareas en E/S

        while (!queuesPriorities.isEmpty()) {

            Task currentTask = queuesPriorities.pollFirstMorePriority();

            if (currentTask != null) {
                // Simulation: IO or Standard
                if (Math.random() < 0.05) {
                    simulationIO(queuesPriorities, currentTask, ioQueue);
                } else {
                    simulationStandard(queuesPriorities, sliceTimeQuantum, currentTask);
                }

            }
        }
    }

    private static void simulationStandard(QueuePriorities<Task> queuesPriorities, int sliceTimeQuantum, Task currentTask) {

        // Cogemos siempre la rodaja de tiempo, pero si el tiempo restante es menor, simplemente ejecutamos ese tiempo menor
        int executionTime = (sliceTimeQuantum < currentTask.getTimeExecute()) ? sliceTimeQuantum : currentTask.getTimeExecute();

        System.out.println("Executing task: " + currentTask.getId() + " for " + executionTime + " quantums" + " - (priority " + currentTask.getPriority() + ")");
        currentTask.reduceTimeExecute(executionTime);
        currentTask.increaseAgingTime();

        if (currentTask.getTimeExecute() > 0) {
            queuesPriorities.add(currentTask.getPriority(), currentTask);
        } else {
            System.out.println("END: " + currentTask.getId());
        }

        solutionStarvation(queuesPriorities, currentTask, executionTime);
    }


    /*
     * Resolución para la inanición:
     * Ejecutamos un proceso con menor prioridad, para que se ejecute al menos una vez.
     */
    private static void solutionStarvation(QueuePriorities<Task> queuesPriorities, Task currentTask, int executionTime) {
        // Early return de filtro
        if (!(currentTask.getAgingTime() >= 10)) {
            return;
        }
        // Early return de filtro
        if (!(currentTask.getPriority() >= 0 && currentTask.getPriority() < queuesPriorities.size() - 1)) {
            return;
        }

        currentTask.setAgingTime(0);

        Task taskToAge = queuesPriorities.pollFirstByPriority(currentTask.getPriority() + 1);
        if (taskToAge != null) {
            System.out.println("Executing aged task: " + taskToAge.getId() + " for " + executionTime + " quantums" + " - (priority " + taskToAge.getPriority() + ")");
            taskToAge.reduceTimeExecute(executionTime);

            if (taskToAge.getTimeExecute() > 0) {
                queuesPriorities.add(taskToAge.getPriority(), taskToAge);
            } else {
                System.out.println("END: " + taskToAge.getId());
            }
        }
    }

    private static void simulationIO(QueuePriorities<Task> queuesPriorities, Task currentTask, Queue<Task> ioQueue) {

        int timeInSecondsWaitIO = 1;

        System.out.println("IO Request: " + currentTask.getId());
        ioQueue.add(currentTask);

        // Procesa la cola de E/S
        Task ioTask = ioQueue.poll();
        if (ioTask != null) {
            System.out.println("Executing I/O for task: " + ioTask.getId() + " for " + timeInSecondsWaitIO + " seconds" + " - (priority " + currentTask.getPriority() + ")");

            try {
                Thread.sleep(timeInSecondsWaitIO * 1000); // Simulamos 1 quantum de E/S
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ioTask.reduceTimeExecute(timeInSecondsWaitIO); // Simulamos que cada E/S dura 1 quantum

            if (ioTask.getTimeExecute() > 0) {
                queuesPriorities.add(ioTask.getPriority(), ioTask);
            } else {
                System.out.println("END: " + ioTask.getId());
            }
        }
    }
}
