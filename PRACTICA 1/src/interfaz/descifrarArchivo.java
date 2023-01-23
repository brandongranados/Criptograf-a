package interfaz;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class descifrarArchivo {
	private Cipher descifrador;
    private String archCifrado;
    public descifrarArchivo(SecretKey llave)
    {
        try {
            descifrador = Cipher.getInstance("DES");
            descifrador.init(Cipher.DECRYPT_MODE, llave);
        } catch (Exception e) {}
    }
    public void setArchCifrado(String archCifrado)
    {
        this.archCifrado = archCifrado;
    }
    public String getArchDescifrado()
    {
        try {
            return new String(descifrador.doFinal(archCifrado.getBytes()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return e.getMessage();   
        }
    }
    public String getArchDescifrado(boolean select)
    {
        try {
            return new String(descifrador.doFinal(Base64.getDecoder().decode(archCifrado)), StandardCharsets.UTF_8).replace("#69#", "\n");
        } catch (Exception e) {
            return "";   
        }
    }
}
