package encriptar_m09;

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
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 *
 * @author joanc
 */
public class Encriptar_M09 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        //BufferedReader bf = new BufferedReader(new FileReader("entrada.txt"));
        File fichero_entrada = new File("entrada.txt");
        File clave_privada = new File("claveprivada.dat");
        File fichero_salida = new File("encriptado.dat");
        try {
            if (clave_privada.exists()) {
                clave_privada.delete();
            }
            if (fichero_salida.exists()) {
                fichero_salida.delete();
            }
            System.out.println("Crear clave publica y privada");
            //Creacion y obtencion de las claves
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);//tama�o de la clave
            KeyPair clavesRSA = keyGen.generateKeyPair();

            //Clave privada
            PrivateKey clavePrivada = clavesRSA.getPrivate();
            //Clave publica
            PublicKey clavePublica = clavesRSA.getPublic();

            //Creacion de ficheros.
            crearfichero(clave_privada);
            guardarLlave(clavePrivada, "claveprivada.dat");
            if (!fichero_salida.exists()) {
                crearfichero(fichero_salida);
            }

            //Ciframos con clave publica el texto plano utilizando RSA
            Cipher cifrador = Cipher.getInstance("RSA");
            cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
            try (FileInputStream in = new FileInputStream(fichero_entrada);
                    FileOutputStream out = new FileOutputStream(fichero_salida)) {
                processFile(cifrador, in, out);
            }

        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
    /**
     * Metodo que crea ficheros.
     * @param fichero 
     */
    public static void crearfichero(File fichero) {
        try {
            // A partir del objeto File creamos el fichero físicamente
            if (fichero.createNewFile()) {
                System.out.println("El fichero se ha creado correctamente");
            } else {
                System.out.println("No ha podido ser creado el fichero");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Metodo que guarda una llave en un fichero.
     * @param key
     * @param nombreFichero
     * @throws Exception 
     */
    private static void guardarLlave(Key key, String nombreFichero) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(nombreFichero);
        fos.write(publicKeyBytes);
        fos.close();
    }

    /**
     * Metodo que sirve para cifrar y cifrar un fichero.
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

}
