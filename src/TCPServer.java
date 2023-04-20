import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class TCPServer {

    // puerto para el servidor TCP
    private static final int PORT = 9000;

    private ServerSocket socketServidor = null;
    private Socket socketCliente = null;
    private BufferedReader entrada = null;
    private PrintWriter salida = null;

    private final Pattern DATE_REGEX = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}");
    private final Pattern PLACA = Pattern.compile("[0-9]{4}-[A-Z]{3}");
    public TCPServer() {
        try {
            socketServidor = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("No puede escuchar en el puerto: " + PORT);
            System.exit(-1);
        }
    }

    /**
     * Inicia el servidor en el puerto 9000 por defecto
     * @exception IOException en Caso de algun error en los strams de entrada o salida de datos
     */
    public void run() {
        System.out.println("Escuchando: " + socketServidor);
        try {
            // abriendo un socket para el cliente
            socketCliente = socketServidor.accept();
            // Establece canal de entrada
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            // Establece canal de salida
            salida = new PrintWriter(new BufferedWriter(new
                    OutputStreamWriter(socketCliente.getOutputStream())), true);
            while (true) {
                String str = entrada.readLine();
                System.out.println("Cliente: " + str);
                salida.println("Cliente: "+ socketCliente);
                if (!PLACA.matcher(str).find()) {
                    salida.println("Placa invalida");
                } else if (!DATE_REGEX.matcher(str).find()) {
                    salida.println("Fecha invalida");
                } else {

                    //String date = DATE_REGEX.matcher(str).group(1);
                    String date = str.substring(0, 11);
                    // String placa = PLACA.matcher(str).group();
                    String placa = str.substring(11);
                    System.out.println("Fecha: " + date);
                    System.out.println("Placa: " + placa);
                    int fecha = new SimpleDateFormat("dd/MM/yyyy").parse(date).getDay();
                    placa = placa.replaceAll("[A-Z]", "").replaceAll("-","");
                    boolean aux = true;
                    String last = placa.substring(placa.length() - 1);
                    System.out.println(fecha + " " + placa + " " + date + ";;" +  new SimpleDateFormat("dd/MM/yyyy").parse(date).toString());
                    if (fecha == 1){
                        if (last == "1" || last == "2"){
                            salida.println("Tiene Restriccion");
                            aux = false;
                        }
                    }
                    if (fecha == 2){
                        if (last.equals("3") || last.equals("4")){
                            salida.println("Tiene Restriccion");
                            aux = false;
                        }
                    }
                    if (fecha == 3){
                        if (last.equals("5") || last.equals("6")){
                            salida.println("Tiene Restriccion");
                            aux = false;
                        }
                    }
                    if (fecha == 4){
                        if (last.equals("7") || last.equals("8")){
                            salida.println("Tiene Restriccion");
                            aux = false;
                        }
                    }
                    if (fecha == 5){
                        if (last.equals("9") || last.equals("0")){
                            salida.println("Tiene Restriccion");
                            aux = false;
                        }
                    }

                    if (aux) {
                        salida.println("NO Tiene Restriccion");
                    }

                }
                if (str.equals("exit")) break;
            }

        } catch (IOException | ParseException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        end();
    }

    /**
     * Cierra todos los streams que se utilizaron en el proceso
     * @exception NullPointerException En caso de que algun stream sea null
     */
    private void end() {
        try {
            salida.close();
            entrada.close();
            socketCliente.close();
            socketServidor.close();

        } catch (Exception e) {
            System.out.println("Error IO: " + e.getMessage() );
        }
    }
}
