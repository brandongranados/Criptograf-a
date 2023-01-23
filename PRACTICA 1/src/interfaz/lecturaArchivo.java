package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

public class lecturaArchivo {
	private File archivoSel;
    private JFileChooser selctorArch;
    private BufferedReader leerArch;
    private String contArch;
    public lecturaArchivo()
    {
        contArch = "";
        selctorArch = new JFileChooser();
        selctorArch.showOpenDialog(null);
        archivoSel = selctorArch.getSelectedFile();
        try {
            leerArch = new BufferedReader(new FileReader(archivoSel));
            String linea;
            while((linea = leerArch.readLine())!= null)
                contArch += linea+"#69#";
            leerArch.close();
        } catch (Exception e) {}
        finally
        {
            try {
                leerArch.close();
            } catch (IOException e) {}
        }
    }
    public lecturaArchivo(boolean selec)
    {
        contArch = "";
        selctorArch = new JFileChooser();
        selctorArch.showOpenDialog(null);
        archivoSel = selctorArch.getSelectedFile();
        try {
            leerArch = new BufferedReader(new FileReader(archivoSel));
            String linea;
            while((linea = leerArch.readLine())!= null)
                contArch += linea;
            leerArch.close();
        } catch (Exception e) {}
        finally
        {
            try {
                leerArch.close();
            } catch (IOException e) {}
        }
    }
    public String getContenidoArchivo()
    {
        return contArch;
    }
    public String getRutaNombreArchivo()
    {
        return archivoSel.getPath().replace(".txt", "_C.txt");
    }
    public String getRutaNombreArchivo(boolean descifrar)
    {
        return archivoSel.getPath().replace("_C.txt", "_D.txt");
    }
}
