import java.io.File;
import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class llavesEnArchivo {
    private KeyPairGenerator gneradorLlaves;
    private KeyPair parllaves;
    private PublicKey llavePublica;
    private PrivateKey llavePrivada;
    private String privateKey, publicKey;
    private File archPrivate, archPublic;
    private FileWriter salidaArch;
    public llavesEnArchivo()
    {
        try {
            gneradorLlaves = KeyPairGenerator.getInstance("RSA");
            gneradorLlaves.initialize(2048);
            parllaves = gneradorLlaves.generateKeyPair();
            llavePrivada = parllaves.getPrivate();
            llavePublica = parllaves.getPublic();

            privateKey = Base64.getEncoder().encodeToString(llavePrivada.getEncoded());
            publicKey = Base64.getEncoder().encodeToString(llavePublica.getEncoded());

        } catch (NoSuchAlgorithmException e) {}
        try {
            archPrivate = new File("private.priv");
            salidaArch = new FileWriter(archPrivate);
            salidaArch.write(privateKey);
            salidaArch.close();

            archPublic = new File("public.pub");
            salidaArch = new FileWriter(archPublic);
            salidaArch.write(publicKey);
            salidaArch.close();
            
        } catch (Exception e) {}
    }
    public static void main(String[] args) {
        llavesEnArchivo nuevo = new llavesEnArchivo();
    }
}
