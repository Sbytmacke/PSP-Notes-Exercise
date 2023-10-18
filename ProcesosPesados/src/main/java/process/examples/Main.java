package process.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder();
/*Scanner sc= new Scanner(System.in);
int c=sc.nextInt();
System.out.println(c); */
        // Run this on Windows, cmd, /c = terminate after this run
        //para ejecución en la powershell de windows (configuración de ejecución por defecto):
        processBuilder.command("bash", "-c", "ping -c 4 google.com");
        //Para ejecución en WSL:
        //processBuilder.command("pwd");
        try {

            Process process = processBuilder.start();

            // Es bloqueante, no puedo hacer nada hasta que no termine :(
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int codError = process.waitFor();
            System.out.println("\nCódigo de error de retorno : " + codError);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
