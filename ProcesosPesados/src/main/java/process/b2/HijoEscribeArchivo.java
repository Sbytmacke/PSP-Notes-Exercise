package process.b2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HijoEscribeArchivo {
    public static void main(String[] args) {
        try {
            // Abre un archivo para escritura
            FileWriter fileWriter = new FileWriter("salida.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Escribe algo en el archivo
            printWriter.append("Hola desde el proceso hijo");
            printWriter.close();

            // Simulamos la ejecución durante 5 segundos
            Thread.sleep(5000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

