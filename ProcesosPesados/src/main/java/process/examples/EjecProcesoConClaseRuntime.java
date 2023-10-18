package process.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EjecProcesoConClaseRuntime {

    public static void main(String[] args) {

  /*  if (args.length < 1) {
      System.err.println("ERROR: Especificar comando a ejecutar.");
      System.exit(1);
    } */
        try {
            //  Runtime.getRuntime().exec("notepad.exe algo.txt");
            Process p = Runtime.getRuntime().exec("cmd /c dir");
            InputStream salidaProceso = p.getInputStream();
            BufferedReader salida = new BufferedReader(new InputStreamReader(salidaProceso));
            String linea;
            while ((linea = salida.readLine()) != null)
                System.out.println(linea);
            salida.close();
            int codSalida = p.waitFor();
            System.out.println(codSalida);
        } catch (IOException | InterruptedException ex) {
            System.err.println("ERROR: de E/S al ejecutar comando.");
            ex.printStackTrace();
        }

    }

}
