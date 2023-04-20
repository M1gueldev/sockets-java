import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    // puerto para el servidor TCP
    private static final int PORT = 9000;

    private ServerSocket socketServidor = null;
    private Socket socketCliente = null;
    private BufferedReader entrada = null;
    private PrintWriter salida = null;

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
                salida.println(str);
                if (str.equals("exit")) break;
            }

        } catch (IOException e) {
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
