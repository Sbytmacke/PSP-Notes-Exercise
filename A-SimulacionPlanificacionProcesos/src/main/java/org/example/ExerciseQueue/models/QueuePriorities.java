package org.example.ExerciseQueue.models;

import java.util.ArrayList;
import java.util.List;

// La mayor prioridad es 0, la menor es colasPrioridades.size()-1
public class QueuePriorities<T> {

    // ArrayList de colas, cada índice es una prioridad (No se puede Array por el genérico)
    private final List<Queue<T>> queuesPriorities;
    int numPriorities;

    public QueuePriorities(int numPrioridades) {
        this.numPriorities = numPrioridades;

        if (numPrioridades <= 0) {
            throw new IllegalArgumentException("El número de prioridades debe ser mayor que 0");
        }

        // Creamos las colas, en función de las prioridades que haya
        queuesPriorities = new ArrayList<>(numPrioridades);
        for (int i = 0; i < numPrioridades; i++) {
            queuesPriorities.add(new Queue<T>());
        }
    }

    // Añadir un elemento a la cola de una prioridad específica
    public void add(int prioridad, T elemento) {
        if (prioridad < 0 || prioridad >= queuesPriorities.size()) {
            throw new IllegalArgumentException("Prioridad no válida");
        }

        queuesPriorities.get(prioridad).add(elemento);
    }

    // Remover el primero que entró en la cola (FIFO), con mayor prioridad
    public T pollFirstMorePriority() {
        for (int i = 0; i < queuesPriorities.size(); i++) {
            if (!queuesPriorities.get(i).isEmpty()) {
                return queuesPriorities.get(i).poll();
            }
        }
        return null;
    }

    public T peekFirstMorePriority() {
        for (int i = 0; i < queuesPriorities.size(); i++) {
            if (!queuesPriorities.get(i).isEmpty()) {
                return queuesPriorities.get(i).peekFirst();
            }
        }
        return null;
    }

    public boolean isEmpty() {
        for (int i = 0; i < queuesPriorities.size(); i++) {
            if (!queuesPriorities.get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public List<Queue<T>> getAll() {
        return queuesPriorities;
    }

    public void remove(T procesoActual) {
        for (int i = 0; i < queuesPriorities.size(); i++) {
            queuesPriorities.get(i).remove(procesoActual);
        }
    }

    public int size() {
        return queuesPriorities.size();
    }

    public Queue<T> get(int index) {
        return queuesPriorities.get(index);
    }

    public T pollFirstByPriority(int priority) {
        if (!queuesPriorities.get(priority).isEmpty()) {
            return queuesPriorities.get(priority).poll();
        }

        return null;
    }
}
