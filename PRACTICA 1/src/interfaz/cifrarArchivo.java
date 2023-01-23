package interfaz;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class cifrarArchivo {
    private Cipher cifrar;
    private KeyGenerator generadorLlaves;
    private SecretKey llave;
    private String contenido;
    public cifrarArchivo()
    {
        try {
            generadorLlaves = KeyGenerator.getInstance("DES");
            llave = generadorLlaves.generateKey();

            cifrar = Cipher.getInstance("DES");
            cifrar.init(Cipher.ENCRYPT_MODE, llave);
        } catch (Exception e) {}

    }
    public void setContenidoArch(String contenido)
    {
        this.contenido = contenido;
    }
    public String getArchCifrado()
    {
        try {
            return Base64.getEncoder().encodeToString(cifrar.doFinal(contenido.getBytes()));
        } catch (Exception e) {
            return e.getMessage();
        }
        
    }
    public SecretKey getLLaveSimetrica()
    {
        return llave;
    }
}
