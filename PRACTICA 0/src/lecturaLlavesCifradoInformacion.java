import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class lecturaLlavesCifradoInformacion {

    private File lectura;
    private BufferedReader entrada;
    private byte[] llavePriv, llavePub;
    private PrivateKey llavePrivada;
    private PublicKey llavePublica;
    private Cipher cifrarLlavePriv, cifrarLlavePub;
    private PKCS8EncodedKeySpec llaveCodificadaPriv;
    private X509EncodedKeySpec llaveCodificadaPub;

    public lecturaLlavesCifradoInformacion()
    {

        try {
            lectura = new File("private.priv");
            entrada = new BufferedReader(new FileReader(lectura));
            llavePriv = Base64.getDecoder().decode(entrada.readLine());
            entrada.close();

            lectura = new File("public.pub");
            entrada = new BufferedReader(new FileReader("public.pub"));
            llavePub = Base64.getDecoder().decode(entrada.readLine());
            entrada.close();
        } catch (IOException e) {}

        try {
            llaveCodificadaPriv = new PKCS8EncodedKeySpec(llavePriv);
            llaveCodificadaPub = new X509EncodedKeySpec(llavePub);

            llavePrivada = KeyFactory.getInstance("RSA").generatePrivate(llaveCodificadaPriv);
            llavePublica = KeyFactory.getInstance("RSA").generatePublic(llaveCodificadaPub);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cifrarLlavePriv = Cipher.getInstance("RSA");
            cifrarLlavePub = Cipher.getInstance("RSA");

            cifrarLlavePub.init(Cipher.ENCRYPT_MODE, llavePublica);
            cifrarLlavePriv.init(Cipher.DECRYPT_MODE, llavePrivada);

            lectura = new File("hola.txt");
            entrada = new BufferedReader(new FileReader(lectura));

            byte datosCifrados[] = cifrarLlavePub.doFinal(entrada.readLine().getBytes());
            System.out.println(new String(datosCifrados, StandardCharsets.UTF_8));

            entrada.close();

            byte datosDescifrados[] = cifrarLlavePriv.doFinal(datosCifrados);
            System.out.println(new String(datosDescifrados, StandardCharsets.UTF_8));

        } catch (Exception e) {}

    }
    public static void main(String[] args) {
        lecturaLlavesCifradoInformacion nuevo2 = new lecturaLlavesCifradoInformacion();
    }
}
