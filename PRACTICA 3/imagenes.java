import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class imagenes {
    static int MODO_LECTURA_IMAGEN = 0;
    static int MODO_ESCRITURA_IMAGEN = 1;
    static int IMAGEN_CIFRADA = 0;
    static int IMAGEN_DESCIFRADA = 1;

    private DataInputStream entrada;
    private DataOutputStream salida;
    private File arch;
    private int modo;
    private byte[] cabecera;
    public imagenes()
    {
        cabecera = new byte[54];
    }
    public void setModoFlujoImagen(int modo)
    {
        this.modo = modo;
    }
    public void setRutaImagen(File ruta)
    {
        arch = arch = ruta;
    }
    public byte[] getCabecera()
    {
        return cabecera;
    }
    public byte[] getImagen()//MODO_LECTURA_IMAGEN
    {
        byte inf[] = new byte[1];
        inf[0] = 0;
        try {
        	entrada = new DataInputStream(new FileInputStream(arch));
            byte info[] = new byte[entrada.available()];
            byte crudo[] = new byte[entrada.available()-54];
            entrada.read(info);
            for(int i=0; i<54; i++)
                cabecera[i] = info[i];
            for(int i=0; i<crudo.length; i++)
                crudo[i] = info[i+54];
            entrada.close();
            return crudo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                entrada.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return inf;
    }
    public void getImagen(byte info[], String nombre)//MODO_ESCRITURA_IMAGEN
    {
        try {
            salida = new DataOutputStream(new FileOutputStream(new File(nombre)));
            salida.write(cabecera);
            salida.write(info);
            salida.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                salida.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String getNuevaRuta(int imgTipo, String contCif)
    {
        String carpetasRutas = arch.getPath().replaceAll(arch.getName(), "");
        String nombreArch = "";
        String temp = "e"+contCif;

        if(imgTipo == IMAGEN_CIFRADA)
            nombreArch = arch.getName().replaceAll(".bmp", "")+"e"+contCif+".bmp";
        else//IMAGEN_DESCIFRADA
        	nombreArch = arch.getName().replaceAll(".bmp", "").replaceAll(temp, "")+"d"+contCif+".bmp";
        return carpetasRutas+nombreArch;
    } 
}