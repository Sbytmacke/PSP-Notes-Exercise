package process.examples;

import java.io.*;

//Ejecutar en WSL
public class ProcesosConInyOut {
    public static void main(String[] args) {
        try {
            //Busca la palabra hola en la entrada recibida y devuelve las línea que la contienen
            Process p = Runtime.getRuntime().exec("grep hola");
            OutputStream entradaProceso = p.getOutputStream();
            PrintWriter entrada = new PrintWriter(new OutputStreamWriter(entradaProceso));
            entrada.println("Esta es la primera linea");
            entrada.println("Esta es la segunda  linea y ponemos hola");
            entrada.println("Esta es la tercera linea");
            entrada.println("Esta hola es la cuarta linea");
            entrada.println("Esta es la quinta linea");
            entrada.close();

            InputStream salidaProceso = p.getInputStream();
            BufferedReader salida = new BufferedReader(new InputStreamReader(salidaProceso));
            String linea;
            while ((linea = salida.readLine()) != null)
                System.out.println(linea);
            salida.close();
            p.waitFor();
            System.out.println("Resultado cmd:" + p.exitValue());


        } catch (IOException e) {
            System.out.println("Error In/Out");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Error en waitFor");
            e.printStackTrace();
        }

    }
}
