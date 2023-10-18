package redirects.examples;

import java.io.File;
import java.io.IOException;

public class ProcRedirFichAFich {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("ERROR: indicar: fichero_entrada fichero_salida patrón");
            return;
        }
        String nomFichEntrada = args[0];
        String nomFichSalida = args[1];
        String patron = args[2];

        System.out.printf("Buscando patrón \"%s\" en fichero %s, salida a fichero %s\n",
                patron, nomFichEntrada, nomFichSalida);

        ProcessBuilder pb = new ProcessBuilder("grep", patron);
        pb.redirectInput(new File(nomFichEntrada));
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(nomFichSalida)));
        pb.redirectError(new File("ferrores.txt"));
        try {
            Process p = pb.start();
        } catch (IOException e) {
            System.err.println("ERROR: de E/S");
            e.printStackTrace();
        }

    }

}
