package actividad01;

import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author joanc
 */
public class Buscador {
    XPathExpression xp;
    Element elemento;
    Document doc;
    XPath xpath;
    
    /**
     * Metodo Ejecutar_XPath(), muestra por pantalla el resutlado de la consulta.
     * @return salida :El contenido de la consulta realizada.
     * @throws ParserConfigurationException
     * @throws SAXException 
     */
    public String Ejecutar_XPath() throws ParserConfigurationException, SAXException{
        String salida="";
        try{
            Abrir_DOM();
            String consulta="//numcuenta[.='ES11-1111-2222-3333-4444-5555']/../*";
            xp=xpath.compile(consulta);
            Object result=xp.evaluate(doc,XPathConstants.NODESET);
            NodeList nodo=(NodeList) result;
            System.out.println(salida);
            for(int i=0;i<nodo.getLength();i++){
                salida=salida+"\n"+nodo.item(i).getNodeName()+": "+nodo.item(i).getTextContent();
            }
            
        }catch(XPathExpressionException | DOMException e){
            System.out.println("error!");
            e.printStackTrace();
        }
        System.out.println(salida);
        return salida;
    }
    
    //Generamos un DOM apartir del fichero xml de operaciones.
    /**
     * Metodo Abrir_DOM() que genera el DOM apartir del fichero operaciones.xml
     * @return 0 / -1 Depentiendo de si la ejecuccion ha sido correcta.
     * @throws ParserConfigurationException
     * @throws SAXException 
     */
    public int Abrir_DOM() throws ParserConfigurationException, SAXException{
        try{
            xpath=XPathFactory.newInstance().newXPath();
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            doc=dbf.newDocumentBuilder().parse(new InputSource(new FileInputStream("operaciones.xml")));
            return 0;
        }catch(IOException | ParserConfigurationException | SAXException e){
            e.printStackTrace();
            return -1;
        }
    }
}
