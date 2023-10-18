package process.b2;

import java.io.File;
import java.io.IOException;

public class B2 {
    public static void main(String[] args) {
        int exit = 0;
        while (exit < 2) {
            exit++;
            String absolutePath = new File(B2.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();


            String path = "C:/Users/chivi/Desktop/git-ephemeral/PractiseExam/target/classes/process/b2/HijoEscribeArchivo.class";
            String className = "process.b2.HijoEscribeArchivo.class"; // Si no con el class
            // Hay que copiar el fichero al ubuntu, con el mismo className de paquete

            // Proceso hijo para ejecutar el programa HijoEscribeArchivo
            ProcessBuilder task = new ProcessBuilder();
            //task.directory(new File(path));
            task.command("java", path);
            task.redirectError(new File("errores1.txt"));

            try {
                Process p1 = task.start();

                // Verificar si los procesos siguen en ejecución
                while (p1.isAlive()) {
                    Thread.sleep(2000); // Comprobar cada 3 segundos
                }

                // Si el proceso deja de ejecutarse, lo reinicio
                if (!p1.isAlive()) {
                    System.out.println("El proceso hijo 1 ha dejado de ejecutarse. Reiniciando...");
                }


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

