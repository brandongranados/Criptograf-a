import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class cifrador {
    static int SWICTH_MODO_OPERACION_ECB = 0;
    static int SWICTH_MODO_OPERACION_CBC = 1;
    static int SWICTH_MODO_OPERACION_CFB = 2;
    static int SWICTH_MODO_OPERACION_OFB = 3;

    static int MODO_CIFRADOR = 0;
    static int MODO_DESCIFRADOR = 1;

    private Cipher cifrado;
    private SecretKey llaveGenerada;
    private IvParameterSpec vector;
    private String algoritmo;
    private byte[] infoCruda, infoProcesada;
    public void setModoOpercion(int modoOperacion)
    {
        switch(modoOperacion)
        {
            case 0://SWICTH_MODO_OPERACION_ECB
                algoritmo = "AES";
            break;
            case 1://SWICTH_MODO_OPERACION_CBC
                algoritmo = "AES/CBC/PKCS5Padding";
            break;
            case 2://SWICTH_MODO_OPERACION_CFB
                algoritmo = "AES/CFB128/NoPadding";
            break;
            case 3://SWICTH_MODO_OPERACION_OFB
                algoritmo = "AES/OFB128/NoPadding";
            break;
        }
    }
    public void setParametros(String llave, String vectorInicializacion)
    {
        try {
            llaveGenerada = new SecretKeySpec(llave.getBytes(), "AES");
            vector = new IvParameterSpec(vectorInicializacion.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setInformacion(byte[] infoCruda)
    {
        this.infoCruda = infoCruda;
    }
    public byte[] getResultadoCifrador(int modoCifrador)
    {
        if(modoCifrador == MODO_CIFRADOR)
            crearCifrador();
        else
            crearCifrador(0);
        try {
            infoProcesada = cifrado.doFinal(infoCruda);
        } catch (Exception e) {
            infoProcesada = new byte[1];
            infoProcesada[0] = 0;
            e.printStackTrace();
        }
        return infoProcesada;
    }
    private void crearCifrador()
    {
        try {
            cifrado = Cipher.getInstance(algoritmo);
            if(algoritmo.equals("AES"))
                cifrado.init(Cipher.ENCRYPT_MODE, llaveGenerada);
            else
                cifrado.init(Cipher.ENCRYPT_MODE, llaveGenerada, vector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void crearCifrador(int i)
    {
        try {
            cifrado = Cipher.getInstance(algoritmo);
            if(algoritmo.equals("AES"))
                cifrado.init(Cipher.DECRYPT_MODE, llaveGenerada);
            else
                cifrado.init(Cipher.DECRYPT_MODE, llaveGenerada, vector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String prueba = "Brandon Antonio Casiano 28949651452";
        cifrador cif = new cifrador();
        cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_ECB);
        cif.setParametros("HOLAHOLAHOLAHOLA", "ABCDABCDABCDABCD");
        cif.setInformacion(prueba.getBytes());
        byte[] mensajeCif = cif.getResultadoCifrador(cif.MODO_CIFRADOR);
        System.out.println(new String(mensajeCif, StandardCharsets.UTF_8));

        cif.setInformacion(mensajeCif);
        byte[] mensaje = cif.getResultadoCifrador(cif.MODO_DESCIFRADOR);
        System.out.println(new String(mensaje, StandardCharsets.UTF_8));
    }
}