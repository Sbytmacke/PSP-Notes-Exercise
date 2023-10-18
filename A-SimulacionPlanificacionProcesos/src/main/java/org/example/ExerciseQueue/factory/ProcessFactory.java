package org.example.ExerciseQueue.factory;

import org.example.ExerciseQueue.models.Task;

import java.util.ArrayList;

public class ProcessFactory {
    int id = 0;

    private Task createProcess(int id, int priority, int timeExecute, int timeEnter) {
        return new Task(id, priority, timeExecute, timeEnter);
    }

    public ArrayList<Task> createListProcesses(int numPriorities, int numProcesses, int maxTimeExecuteProcess, int maxTimeEnterProcess) {
        ArrayList<Task> listTasks = new ArrayList<>();

        for (int i = 0; i < numProcesses; i++) {
            id = id + 1;
            int randomPriority = (int) (Math.random() * numPriorities);
            int randomTimeExecute = (int) (Math.random() * maxTimeExecuteProcess) + 1;
            int randomTimeEnter = (int) (Math.random() * maxTimeEnterProcess) + 1;

            Task task = createProcess(id, randomPriority, randomTimeExecute, randomTimeEnter);
            listTasks.add(task);
        }

        return listTasks;
    }
}
