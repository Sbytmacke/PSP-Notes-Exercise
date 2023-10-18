package org.example.ExerciseQueue.models;

import java.util.LinkedList;

public class Queue<T> {
    private final LinkedList<T> elementos;

    public Queue() {
        elementos = new LinkedList<T>();
    }

    // Se añade al final
    public void add(T elemento) {
        elementos.add(elemento);
    }

    // Se remueve del principio
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return elementos.poll();
    }

    public boolean isEmpty() {
        return elementos.isEmpty();
    }

    public int size() {
        return elementos.size();
    }

    public void remove(T procesoActual) {
        elementos.remove(procesoActual);
    }

    public T get(int index) {
        return elementos.get(index);
    }

    public T peekFirst() {
        return elementos.peek();
    }

    public LinkedList<T> getAll() {
        return elementos;
    }
}

