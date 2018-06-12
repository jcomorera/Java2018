
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author joanc
 */
public class server {

    public static String[] procesarCliente(Socket socket, Double saldo) {
        String respuesta = "¡La orden introducida no es valida!";
        String[] retorno = new String[2];
        retorno[1] = "nofin";
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            BufferedReader br_socket = new BufferedReader(new InputStreamReader(is));
            // obtenemos la orden
            String line = br_socket.readLine();
            if (line.charAt(0) == 'i'&&line.length()>1) {
                System.out.println("Relizando ingreso..");
                String cantidad = line.substring(2, line.length());
                cantidad = sinEspacios(cantidad);
                if (isNumeric(cantidad)) {
                    saldo += Double.parseDouble(cantidad);
                } else {
                    System.out.println("Valor de entrada no numerico!");
                }
                respuesta = "Ingreso " + cantidad.toString() + " Saldo actual: " + saldo;

            } else if (line.charAt(0) == 'r'&&line.length()>1) {
                System.out.println("Relizando reintegro..");
                String cantidad = line.substring(2, line.length());
                cantidad = sinEspacios(cantidad);
                if (isNumeric(cantidad)) {
                    saldo -= Double.parseDouble(cantidad);

                } else {
                    System.out.println("Valor de entrada no numerico!");
                }
                respuesta = "Reintegro " + cantidad.toString() + " Saldo actual: " + saldo;
            } else if (line.charAt(0) == 's') {
                System.out.println("Mostrando saldo..");
                respuesta = "Saldo actual:" + saldo.toString();
            } else if (line.charAt(0) == 'f') {
                System.out.println("Cerrando conexion...");
                respuesta = "Conexión cerrada.";
                retorno[1] = "fin";
            } 
            char resupuestachar[] = respuesta.toCharArray();
            for (int i = 0; i < respuesta.length(); i++) {
                char b = resupuestachar[i];
                os.write(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        retorno[0] = saldo.toString();
        return retorno;
    }

    public static void main(String[] args) {
        Double saldo = 1000.00;
        Boolean salir = false;
        try {
            ServerSocket serverSocket = new ServerSocket();
            // Creamos un Socket Adress para una  máquina y numero de puerto
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            // Enlaza el serverSocket a una direccion especifica (IP  y numero de puerto).
            serverSocket.bind(addr);
            String[] retorno = new String[2];
            System.out.println("A la escucha de un cliente...");
            while (!salir) {
                // Estamos a la escucha de una connexión a este socket y la aceptamos
                Socket newSocket = serverSocket.accept();
                retorno = procesarCliente(newSocket, saldo);
                if (retorno[1] == "fin") {
                    salir = true;
                }
                retorno[0] = sinEspacios(retorno[0]);
                saldo = Double.parseDouble(retorno[0]);
                newSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Función que dictamina si el valor String introducido es numerico.
     *
     * @param teclado contiene el valor introducido en forma de cadena de texto.
     * @return un boolean (true=es numerico/false= no es numerico)
     */
    public static boolean isNumeric(String teclado) {
        try {
            Double.parseDouble(teclado);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metodo que utilizo para eliminar los espacios en blanco en una cadena.
     *
     * @param str
     * @return str (La string de entrada sin espacios)
     */
    public static String sinEspacios(String str) {
        String cadenaSinBlancos = "";
        for (int x = 0; x < str.length(); x++) {
            if (str.charAt(x) != ' ') {
                cadenaSinBlancos += str.charAt(x);
            }
        }
        return cadenaSinBlancos;
    }
}
