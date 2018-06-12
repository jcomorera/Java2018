package desencriptar_m09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author joanc
 */
public class Desencriptar_M09 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        File fichero_entrada = new File("encriptado.dat");
        File fichero_salida = new File("desencriptado.txt");

        try {
            if (fichero_salida.exists()) {
                fichero_salida.delete();
            }
            //Lectura de la clave privada des de fichero.
            PrivateKey clavePrivada = leerLlavePrivada("claveprivada.dat");

            Cipher cifrador = Cipher.getInstance("RSA");
            //Desencriptacion utilizando la clave privada
            cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);

            //Creacion del fichero
            if (!fichero_salida.exists()) {
                crearfichero(fichero_salida);
            }
            //Desencriptacion del fichero de entrada y escritura en el fichero de salida
            try (FileInputStream in = new FileInputStream(fichero_entrada);
                    FileOutputStream out = new FileOutputStream(fichero_salida)) {
                processFile(cifrador, in, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Metodo del cual realizaremos la creacion de los ficheros
     * @param fichero01 
     */
    public static void crearfichero(File fichero01) {
        try {
            // A partir del objeto File creamos el fichero físicamente
            if (fichero01.createNewFile()) {
                System.out.println("El fichero se ha creado correctamente");
            } else {
                System.out.println("No ha podido ser creado el fichero");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Metodo que cifra o decifra el fichero de entrada.
     * @param ci
     * @param in
     * @param out
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.IOException 
     */
    static private void processFile(Cipher ci, InputStream in, OutputStream out)
            throws javax.crypto.IllegalBlockSizeException,
            javax.crypto.BadPaddingException,
            java.io.IOException {
        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = ci.update(ibuf, 0, len);
            if (obuf != null) {
                out.write(obuf);
            }
        }
        byte[] obuf = ci.doFinal();
        if (obuf != null) {
            out.write(obuf);
        }
    }

    /**
     * Metodo que lee la llave privada
     * @param nombreFichero
     * @return
     * @throws Exception 
     */
    private static PrivateKey leerLlavePrivada(String nombreFichero) throws Exception {
        FileInputStream fis = new FileInputStream(nombreFichero);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
        return keyFromBytes;
    }

}
