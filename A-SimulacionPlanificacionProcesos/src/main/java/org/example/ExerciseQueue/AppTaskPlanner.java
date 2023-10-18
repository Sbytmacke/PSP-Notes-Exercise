package org.example.ExerciseQueue;

import javafx.util.Pair;
import org.example.ExerciseQueue.factory.ProcessFactory;
import org.example.ExerciseQueue.models.QueuePriorities;
import org.example.ExerciseQueue.models.Task;
import org.example.ExerciseQueue.services.storage.FileStorage;
import org.example.ExerciseQueue.utils.Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AppTaskPlanner {

    public static void main(String[] args) {
        // Configuración de la simulación
        int sliceTimeQuantumWrite = 2; // Quantum de tiempo en el que se ejecuta cada task
        int numPriorities = 3; // Número de prioridades de los tasks máxima
        int numTasks = 20; // Número de tasks a generar
        int maxDurationTasks = 60; // Duración máxima de un task, desde 1 a maxDurationProcess
        int maxTimeEnter = 10; // Tiempo en el que entrará el proceso máximo, desde 1 a maxTimeEnter

        // Generamos el CSV con patrón Factory
        ProcessFactory processFactory = new ProcessFactory();
        ArrayList<Task> listTasks = processFactory.createListProcesses(numPriorities, numTasks, maxDurationTasks, maxTimeEnter);

        // Escribimos en el archivo CSV, las tasks generadas aleatoriamente
        FileStorage.writeFile(sliceTimeQuantumWrite, listTasks);

        // Leer datos del archivo CSV
        Pair<Integer, ArrayList<Task>> pairSliceTimeAndProcess = FileStorage.readFile();
        int sliceTimeQuantumRead = pairSliceTimeAndProcess.getKey(); // Quantum de tiempo en el que se ejecuta cada task
        ArrayList<Task> tasks = pairSliceTimeAndProcess.getValue();

        // Ordenamos por tiempo de entrada los procesos
        Collections.sort(tasks, Comparator.comparingInt(Task::getTimeEnter));

        // Creamos la cola de prioridades de los tasks leídos del archivo CSV, ordenados y ejecutamos la simulación deseada
        QueuePriorities<Task> queuePriorities = new QueuePriorities<>(numPriorities);
        for (Task task : tasks) {
            queuePriorities.add(task.getPriority(), task);
        }

        Simulation.simulationRoundRobin(queuePriorities, sliceTimeQuantumRead);
    }
}
