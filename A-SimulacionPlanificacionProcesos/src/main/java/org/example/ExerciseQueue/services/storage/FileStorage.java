package org.example.ExerciseQueue.services.storage;

import javafx.util.Pair;
import org.example.ExerciseQueue.models.Task;

import java.io.*;
import java.util.ArrayList;

public class FileStorage {

    // Ruta relativa al archivo CSV en el directorio
    static String filePath = "procesos.csv";

    public static Pair<Integer, ArrayList<Task>> readFile() {


        ArrayList<Task> listTasks = new ArrayList<>();
        Integer sliceTime = null;

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fileReader)) {

            String line;

            // Leer la primera línea (cabecera) y descartarla
            br.readLine();

            // Leer la segunda línea para obtener la rodaja de tiempo
            sliceTime = Integer.parseInt(br.readLine());

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    int priority = Integer.parseInt(parts[1]);
                    int timeExecute = Integer.parseInt(parts[2]);
                    int timeEnter = Integer.parseInt(parts[3]);

                    Task task = new Task(id, priority, timeExecute, timeEnter);
                    listTasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pair<>(sliceTime, listTasks);
    }

    public static void writeFile(int timeSlice, ArrayList<Task> listTasks) {

        // Escritura en el archivo CSV
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            // Cabecera, que debemos omitir al leer
            bufferedWriter.write("ID,Priority,TimeExecute,TimeEnter");
            bufferedWriter.newLine();

            // Segunda línea para saber la rodaja del tiempo en la que se ejecutarán los procesos
            bufferedWriter.write(String.valueOf(timeSlice));
            bufferedWriter.newLine();

            for (Task task : listTasks) {
                String line = task.getId()
                        + "," + task.getPriority()
                        + "," + task.getTimeExecute()
                        + "," + task.getTimeEnter();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
