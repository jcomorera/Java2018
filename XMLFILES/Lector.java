package actividad01;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author joanc
 */
public class Lector {

    ManejadorSAX sh;
    SAXParser parser;
    File ficheroXML;
    String salida;

    /**
     * Metodo abrir_XML_SAX(File fichero) que abre el fichero xml con un lector
     * SAX. Llamamos el constructor del manejador para el tratamiento de
     * eventos.
     *
     * @param fichero
     * @return 0/-1
     */
    public int abrir_XML_SAX(File fichero) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //Se crea un objeto SAXParser para interpretar el documento XML.
            parser = factory.newSAXParser();
            //Se crea un instancia del manejador que será el que recorra el documento XML secuencialmente
            sh = new ManejadorSAX();
            ficheroXML = fichero;
            return 0;

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Metodo recorrerSAXyMostrar(), trata el parser para manejar el documento
     * XML.
     *
     * @return cadena_resultado , muestra el resultado del manejador.
     */
    public String recorrerSAXyMostrar() {
        //Se da la salida al parser para que comience a manejar el documento XML que se le pasa como parámetro
        //con el manejador que también se le pasa. Esto recorrera secuencialmente el documento XML y cuando detecte
        //un comienzo o fin de elemento o un texto entonces lo tratará (según la implementación hecha del manejador)
        try {
            parser.parse(ficheroXML, sh);
            return (sh.salida);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return salida;
        }
    }

    /**
     * Metodo Lector(), que realiza el tratamiento del lector SAX.
     * Obteniendo una String con los valores de los atributos de la Operacion separados por punto y como.
     * Los procesa creando un array de Operacion como dato de salida.     *
     * @return Operacion[], devuelve un array de Operacion sobre el contenido del xml.
     */
    public Operacion[] Lector() {
        String fechahora;
        String numcuenta;
        String propietario;
        String tipo;
        String cantidad;
        String saldo;
        int ind=0;
        int i=0;
        
        Lector lectorSAX = new Lector();
        File f = new File("operaciones.xml");
        if (!f.exists()) {
            System.out.println("Fichero " + f.getName() + " no existe");
        } else if (lectorSAX.abrir_XML_SAX(f) == -1) {
            System.out.println("Error al abrir el archivo para SAX");
        } else {
            System.out.println("¡Ya se ha creado el SAX!");
            String SalidaLectorSax = lectorSAX.recorrerSAXyMostrar();
            System.out.println(SalidaLectorSax);
            String[] arrayLiniaCSV=SalidaLectorSax.split(";");
            Operacion[] oper=new Operacion[5];
            while(i<=24){
                fechahora=arrayLiniaCSV[i];
                i++;
                numcuenta=arrayLiniaCSV[i];
                i++;
                propietario=arrayLiniaCSV[i];
                i++;
                tipo=arrayLiniaCSV[i];
                i++;
                cantidad=arrayLiniaCSV[i];
                i++;
                saldo=arrayLiniaCSV[i];
                i++;
                oper[ind]=new Operacion(fechahora,numcuenta,propietario,tipo,cantidad,saldo);
                ind++;
            }
            return oper;
        }
        return null;
    }
    

    public class ManejadorSAX extends DefaultHandler {

        int retorno;
        String fechahora;
        String numcuenta;
        String propietario;
        String tipo;
        String cantidad;
        String saldo;
        String salida="";

        @Override
        public void startDocument() throws SAXException {
            System.out.println("\nPrincipio del documento...");
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("\nFin del documento...");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            for (int i = 0; i < atts.getLength(); i++) {
                if (atts.getQName(i).equals("fechahora")) {
                    retorno = 1;
                    fechahora = atts.getValue(i)+";";
                }
            }
            switch (qName) {
                case "numcuenta":
                    retorno = 2;
                    break;
                case "propietario":
                    retorno = 3;
                    break;
                case "tipo":
                    retorno = 4;
                    break;
                case "cantidad":
                    retorno = 5;
                    break;
                case "saldo":
                    retorno = 6;
                    break;
                default:
                    retorno = 0;
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            switch (retorno) {
                case 2:
                    numcuenta = (String.valueOf(ch, start, length)).replace("\n", "")+";";
                    break;
                case 3:
                    propietario = (String.valueOf(ch, start, length)).replace("\n", "")+";";
                    break;
                case 4:
                    tipo = (String.valueOf(ch, start, length)).replace("\n", "")+";";
                    break;
                case 5:
                    cantidad = (String.valueOf(ch, start, length)).replace("\n", "")+";";
                    break;
                case 6:
                    saldo = (String.valueOf(ch, start, length)).replace("\n", "")+";";
                    break;
                default:
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {


            switch (name) {
                case "numcuenta":
                    salida+=fechahora;
                    salida+=numcuenta;
                    break;
                case "propietario":
                    salida+=propietario;
                    break;
                case "tipo":
                     salida+=tipo;
                    break;
                case "cantidad":
                    salida+=cantidad;
                    break;
                case "saldo":
                    salida+=saldo;
                    break;
                default:
                    break;
            }
        }

    }
}
