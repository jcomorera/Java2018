package actividad01;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author joanc
 */
public class Principal {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        //Ejecucion del programa traspaso:
        Traspaso.Traspaso();
        //Ejecucion del programa lector:
        Lector l = new Lector();
        Operacion[] oper = l.Lector();
        for (int i = 0; i < 5; i++){
            System.out.println("\n");
            System.out.println(oper[i].getFechahora());
            System.out.println(oper[i].getNumcuenta());
            System.out.println(oper[i].getPropietario());
            System.out.println(oper[i].getTipo());
            System.out.println(oper[i].getCantidad());
            System.out.println(oper[i].getSaldo());
            System.out.println("\n");
        }
        //Ejecucion del programa buscador:
        Buscador b = new Buscador();
        b.Ejecutar_XPath();
    }
}
