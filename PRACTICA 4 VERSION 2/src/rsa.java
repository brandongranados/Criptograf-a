import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
public class rsa {
    static int MODO_CIFRADOR = 0;
    static int MODO_DESCIFRADOR = 1;
    static int LLAVE_PUBLICA = 0;
    static int LLAVE_PRIVADA = 1;

    private byte[] digesto, firmaElectronica;
    private Cipher cifrador;
    private PrivateKey llavePrivada;
    private PublicKey llavePublica;
    private File ruta;
    private DataInputStream ent;
    private DataOutputStream sal;
    private int modoCifrado, selLLAVE;

    public rsa()
    {
        try {
            //GENERADOR DE LA INSTANCIA DE CIFRADO
            cifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding");     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //AREA DE SET
    public void setDigesto(byte[] digesto)
    {
        this.digesto = digesto;
    }
    public void setParametros(int modoCifrado, int selLLAVE)
    {
        this.modoCifrado = modoCifrado;
        this.selLLAVE = selLLAVE;
    }
    public void setParametros(int modoCifrado)
    {
        this.modoCifrado = modoCifrado;
    }
    public void setLlavePublica(String rutaLlave)
    {
        try {
            //LEER LLAVE COMO BYTES
            ent = new DataInputStream(new FileInputStream(rutaLlave));
            byte llavePublicaBytes[] = new byte[ent.available()];
            ent.read(llavePublicaBytes);
            ent.close();

            //GENERAR LA LLAVE A PARTIR DEL ARREGLO DE BYTES
            X509EncodedKeySpec llavePublicaCodificada = new X509EncodedKeySpec(llavePublicaBytes);
            llavePublica = KeyFactory.getInstance("RSA").generatePublic(llavePublicaCodificada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                ent.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void setLlavePrivada(String rutaLlave)
    {
        try {
            //LEER LLAVE COMO BYTES
            ent = new DataInputStream(new FileInputStream(rutaLlave));
            byte llavePrivadaBytes[] = new byte[ent.available()];
            ent.read(llavePrivadaBytes);
            ent.close();

            //GENERAR LA LLAVE A PARTIR DEL ARREGLO DE BYTES
            PKCS8EncodedKeySpec llavePrivadaCodificada = new PKCS8EncodedKeySpec(llavePrivadaBytes);
            llavePrivada = KeyFactory.getInstance("RSA").generatePrivate(llavePrivadaCodificada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                ent.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //AREA DE GET
    public void getLLaves()
    {
        try {
            //GENERADOR DE LLAVES
            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);

            //OBJETO QUE CONTIENE EL PAR DE LLAVES
            KeyPair parLlaves = generador.genKeyPair();

            //creacion de llaves
            llavePrivada = parLlaves.getPrivate();
            llavePublica = parLlaves.getPublic();

            //ESCRIBIR LLAVE PRIVADA EN ARCHIVO
            sal = new DataOutputStream(new FileOutputStream("keyPriv.priv"));
            sal.write(llavePrivada.getEncoded());
            sal.close();

            //ESCRIBIR LLAVE PUBLICA EN ARCHIVO
            sal = new DataOutputStream(new FileOutputStream("keyPub.pub"));
            sal.write(llavePublica.getEncoded());
            sal.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                sal.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void getArcchivoFirmado(File ruta)
    {
        this.ruta = ruta;
        crearFirmaElectronica();
        firmarDocumento();
    }
    public boolean getRevisarArchivoFirmado(File ruta)
    {
        this.ruta = ruta;
        return compararFirmasDocumentos();
    }
    public void setLlave(String rutaLlave)
    {
        try {
        	File ruta = new File(rutaLlave);
            String ext = ruta.getName().replaceAll(".pub", "");

            if( ruta.getName().length() > ext.length() )//ingreso llave publica
            {
                selLLAVE = LLAVE_PUBLICA;
                setLlavePublica(rutaLlave);
            }
            else//ingreso llave privada
            {
                selLLAVE = LLAVE_PRIVADA;
                setLlavePrivada(rutaLlave);
            }
                

        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    //FUNCIONES PRIVADAS
    private void firmarDocumento()
    {
        String nombreArch = ruta.getName().split(Pattern.quote("."))[0];
        String ext = ruta.getName().split(Pattern.quote("."))[1];
        String rutaDoc = ruta.getAbsolutePath().replaceAll(ruta.getName(), "");

        try {
            ent = new DataInputStream(new FileInputStream(ruta));
            byte crudo[] = new byte[ent.available()];
            ent.read(crudo);
            ent.close();

            sal = new DataOutputStream(new FileOutputStream(rutaDoc+nombreArch+"Firmado."+ext));
            sal.write(crudo);
            sal.write(firmaElectronica);
            sal.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void crearFirmaElectronica()
    {
        try {

            if(selLLAVE == LLAVE_PRIVADA)
                cifrador.init(Cipher.ENCRYPT_MODE, llavePrivada);
            else//LLAVE PUBLICA
                cifrador.init(Cipher.ENCRYPT_MODE, llavePublica);

            firmaElectronica = cifrador.doFinal(digesto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void descomponerFirmaElectronica()
    {
        try {

            if(selLLAVE == LLAVE_PRIVADA)
                cifrador.init(Cipher.DECRYPT_MODE, llavePrivada);
            else
                cifrador.init(Cipher.DECRYPT_MODE, llavePublica);

            digesto = cifrador.doFinal(firmaElectronica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean compararFirmasDocumentos()
    {
        try {
            hash crearHash = new hash();
            byte digestoFirma[];
            byte crudo[];
            byte infoCruda[];

            //SE LEE EL ARCHIVO FIRMADO EN CRUDO
            ent = new DataInputStream(new FileInputStream(ruta));
            crudo = new byte[ent.available()];
            ent.read(crudo);
            ent.close();

            //SE DESCOMPONE EL CRUDO DEL ARCHIVO EN LA INFO ORIINAL Y LA FIRMA
            infoCruda = new byte[crudo.length-256];
            firmaElectronica = new byte[256];

            for(int i=0; i<infoCruda.length; i++)
                infoCruda[i] = crudo[i];
            for(int i=0; i<firmaElectronica.length; i++)
                firmaElectronica[i] = crudo[i+infoCruda.length];

            descomponerFirmaElectronica();
            digestoFirma = crearHash.getDigesto(infoCruda);

            //se validan los dos digestos para identificar la autenticidad
            for(int i=0; i<digestoFirma.length; i++)
                if(digestoFirma[i] != digesto[i])
                	return false;
                

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}