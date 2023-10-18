package process.b3;

import java.io.File;
import java.io.IOException;

public class B3 {
    public static void main(String[] args) {
        // Directorio al que deseas cambiar
        File nuevoDirectorio = new File("/etc");

        try {
            // Creamos un proceso builder
            ProcessBuilder builder = new ProcessBuilder("ls");

            // Establecemos el directorio de trabajo, donde se ejecutará el comando
            builder.directory(nuevoDirectorio);

            // Redirigimos la salida estándar
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            Process proceso = builder.start();

            // Esperamos a que el proceso termine
            int exitCode = proceso.waitFor();

            if (exitCode == 0) {
                System.out.println("Comando ejecutado correctamente.");
            } else {
                System.err.println("Error al ejecutar el comando.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

