package rendimiento_m09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author joanc
 */
public class Rendimiento_M09 {

    public static void main(String[] args) throws FileNotFoundException {

        File fichero = new File("entrada.txt");
        long startAES, stopAES, startRSA, stopRSA, startDES3, stopDES3;

        startAES = System.currentTimeMillis();
        encriptarAES(fichero);
        stopAES = System.currentTimeMillis();
        System.out.println("La encriptacion en AES: " + (startAES - stopAES) + " milisegundos.");

        startRSA = System.currentTimeMillis();
        encriparRSA(fichero);
        stopRSA = System.currentTimeMillis();
        System.out.println("La encriptacion en RSA: " + (startRSA - stopRSA) + " milisegundos.");

        startDES3 = System.currentTimeMillis();
        TripleDES(fichero);
        stopDES3 = System.currentTimeMillis();
        System.out.println("La encriptacion en TripleDES: " + (startDES3 - stopDES3) + " milisegundos.");
    }

    /**
     * Metodo que encripta un fichero en algoritmo AES.
     *
     * @param fichero
     */
    public static void encriptarAES(File fichero) {

        try {
            FileReader fr = new FileReader(fichero);
            BufferedReader bf = new BufferedReader(fr);
            //Creamos la clave secreta usando el algoritmo AES y deﬁnimos un tamaño de clave de 128 bits
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey clave = kg.generateKey();

            //Creamos un objeto Cipher con el algoritmo AES,
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, clave);
            String cadena;
            byte textoPlano[] = null;
            while ((cadena = bf.readLine()) != null) {
                textoPlano = cadena.getBytes();
            }
            //Realizamos el cifrado de la información con el método doFinal()
            byte textoCifrado[] = c.doFinal(textoPlano);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que encripta un fichero en algoritmo RSA.
     *
     * @param fichero
     * @throws FileNotFoundException
     */
    public static void encriparRSA(File fichero) throws FileNotFoundException {

        try {
            FileReader fr = new FileReader(fichero);
            BufferedReader bf = new BufferedReader(fr);
            //Creaci�n y obtenci�n del par de claves
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);//tama�o de la clave
            KeyPair clavesRSA = keyGen.generateKeyPair();

            //Clave privada
            PrivateKey clavePrivada = clavesRSA.getPrivate();
            //Clave p�blica
            PublicKey clavePublica = clavesRSA.getPublic();
            String cadena;
            byte mensaje[] = null;
            while ((cadena = bf.readLine()) != null) {
                mensaje = cadena.getBytes();
            }

            //Ciframos con clave p�blica el texto plano utilizando RSA
            Cipher cifrador = Cipher.getInstance("RSA");
            cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);

            //Realizaci�n del cifrado del texto plano
            byte[] mensajeCifrado = cifrador.doFinal(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que encripta el fichero de entrada en formato TripleDES.
     *
     * @param fichero
     */
    public static void TripleDES(File fichero) {
        try {
            FileReader fr = new FileReader(fichero);
            BufferedReader bf = new BufferedReader(fr);
            //Creamos la clave secreta usando el algoritmo DES y deﬁnimos un tamaño de clave de 56 bits
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            keygen.init(56);
            SecretKey key = keygen.generateKey();
            //Creamos un objeto Cipher con el algoritmo DES,
            //con la clave creada anteriormente
            Cipher desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            String cadena;
            byte mensaje[] = null;
            while ((cadena = bf.readLine()) != null) {
                mensaje = cadena.getBytes();
            }
            //Realizamos el cifrado de la información con el método doFinal()
            String mensajeCifrado = new String(desCipher.doFinal(mensaje));
            mensajeCifrado = new String(desCipher.doFinal(mensaje));
            mensajeCifrado = new String(desCipher.doFinal(mensaje));
            System.out.println(mensajeCifrado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
