package actividad01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.text.Document;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author joanc
 */
public class Traspaso {

    Document doc;

    /**
     * Conversion del fichero operaciones.txt a formato XML en el fichero
     * operacione.xml mediante el uso de JAXB (Creacion de XML mediante un
     * objeto)
     *
     * @throws java.io.IOException
     */
    public static void Traspaso() throws IOException {
        File archivo;
        String fechahora;
        String numcuenta;
        String titular;
        String tipo;
        String cantidad;
        String saldo;
        archivo = new File("operaciones.txt");
        Operacion[] operacion;
        try (FileReader fr = new FileReader(archivo); BufferedReader br = new BufferedReader(fr)) {
            String linia;
            String[] arrayLiniaSpace;
            String[] arrayLiniaBarra;
            operacion = new Operacion[5];
            ArrayList<Operacion> list = new ArrayList<>();
            for (int oper = 0; oper < 5; oper++) {
                linia = br.readLine();
                arrayLiniaSpace = linia.split(" ");
                //primera linia:
                fechahora = arrayLiniaSpace[1] + " " + arrayLiniaSpace[2];
                numcuenta = arrayLiniaSpace[3];
                //segunda linia:
                linia = br.readLine();
                titular = linia;
                //tercera linia:
                linia = br.readLine();
                arrayLiniaBarra = linia.split("-");
                tipo = arrayLiniaBarra[0];
                cantidad = arrayLiniaBarra[1];
                //quarta linia
                linia = br.readLine();
                saldo = linia;
                operacion[oper] = new Operacion(fechahora, numcuenta, titular, tipo, cantidad, saldo);
                System.out.println("\n");
                list.add(operacion[oper]);
            }
            //Generacion del objeto Operaciones apartir del conetido del array de Operacion()
            Operaciones OperXML = new Operaciones(list);
            //Generacion del arbol XML apartir de la clase Operaciones.
            JAXBContext contextObj = JAXBContext.newInstance(Operaciones.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(OperXML, new FileOutputStream("operaciones.xml"));
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
