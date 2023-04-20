public class Main {

    public static void main(String[] args) {
        try {
            if (args[0].equals("TCP")) {
                TCPServer srv = new TCPServer();
                srv.run();
            } else {
                int port = Integer.parseInt(args[1]);
                UDPServer srv = new UDPServer(port);
                srv.run();
            }
        } catch (NullPointerException e) {
            System.out.println("Argumentos invalidos");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Puerto invalido");
            e.printStackTrace();
        }

    }
}