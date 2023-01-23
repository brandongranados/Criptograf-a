package interfaz;

import java.io.FileWriter;

public class escrituraArchivo {
	private FileWriter escribir;
    private String archSalida, rutaArch;
    private Runtime ejecutaEdtorTexto;
    public void setContenidoArchivo(String archSalida)
    {
        this.archSalida = archSalida;
    }
    public void setRutaArchivo(String rutaArch)
    {
        this.rutaArch = rutaArch;
    }
    public void getArchivo()
    {
        try {
            escribir = new FileWriter(rutaArch);
            escribir.write(archSalida);
            escribir.close();
            ejecutaEdtorTexto = Runtime.getRuntime();
            rutaArch = "gedit "+rutaArch;
            ejecutaEdtorTexto.exec(rutaArch);
            ejecutaEdtorTexto.freeMemory();
        } catch (Exception e) {}
    }
}
