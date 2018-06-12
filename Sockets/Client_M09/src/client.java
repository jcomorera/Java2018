
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author joanc
 */
public class client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String orden="orden";
        try {
            while(orden.charAt(0)!='f'){
                System.out.print("\nOrden:");
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                orden = br1.readLine();
                Socket clientSocket = new Socket();
                //conectamos con el servidor.
                InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
                clientSocket.connect(addr);
                InputStream is = clientSocket.getInputStream();
                OutputStream os = clientSocket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                //pasamos al servidor la orden a realizar
                pw.println(orden);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                //obtenemos del servidor la informacion byte a byte.
                char buffer[] = new char[1];
                while (br.read(buffer) != -1) {
                    System.out.print(buffer[0]);
                }
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
