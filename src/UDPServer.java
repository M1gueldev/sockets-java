import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    private DatagramSocket udpSocket;

    public UDPServer(int Port) {
        try {
            udpSocket = new DatagramSocket(Port);
            System.out.println("Created UDP server socket at "
                    + udpSocket.getLocalSocketAddress() + "...");
        } catch (SocketException e) {
            System.out.println("Error socket " + Port + " esta ocupado");
            e.printStackTrace();
        }
    }

    /**
     * Inicia el Servidor en el puerto dado
     */
    public void run() {
        try {
            do {
                DatagramPacket packet = new DatagramPacket(
                        new byte[1024], 1024);

                udpSocket.receive(packet);

                System.out.println("Mensaje: " + getMensage(packet) );

                if (getMensage(packet).equals("\n")) break;

                udpSocket.send(crearRespuesta(packet));
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee el mensaje enviado en un DatagramPacket
     * @param packet El DatagramPacket a leer
     * @return un String con el mensaje enviado al servidor
     */
    private String getMensage(DatagramPacket packet) {
        byte[] msgBuffer = packet.getData();
        int length = packet.getLength();
        int offset = packet.getOffset();
        return new String(msgBuffer, offset, length);
    }

    /**
     * Cuenta la cantidad de Vocales en un texto
     * @param text el Texto a procesar
     * @return la cantidad de vocales que posee el texto
     */
    private int getVocales(String text) {
        return text.replaceAll("[^aeiouAEIOU]", "").length();
    }

    /**
     * Crea un Paquete de Respuesta con la cantidad de vocales
     * contenida en el paquete origen
     * @param template El paquete de Origen
     * @return El paquete de respuesta
     */
    private DatagramPacket crearRespuesta(DatagramPacket template) {
        String mensaje = getMensage(template);
        String msg = String.valueOf(getVocales(mensaje)).concat("\n");
        return new DatagramPacket(msg.getBytes(), msg.length(), template.getAddress(), template.getPort());
    }
}
