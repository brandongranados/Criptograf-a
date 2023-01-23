import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class hash
{
    public byte[] getDigesto(File ruta)
    {
        try {
            DataInputStream ent = new DataInputStream(new FileInputStream(ruta));
            byte crudo[] = new byte[ent.available()];
            ent.read(crudo);
            ent.close();

            MessageDigest digesto = MessageDigest.getInstance("SHA-256");
            byte crudoDigesto[] = digesto.digest(crudo);
            return crudoDigesto;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return new byte[1];
    }
    public byte[] getDigesto(byte arch[])
    {
        try {
            MessageDigest digesto = MessageDigest.getInstance("SHA-256");
            byte crudoDigesto[] = digesto.digest(arch);
            return crudoDigesto;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return new byte[1];
    }
}