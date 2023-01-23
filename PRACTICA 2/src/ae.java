import java.util.ArrayList;

import com.google.gson.Gson;

class ae
{
    private int alfa, modulo;
    private boolean banderaAE;
    private ArrayList<String> arrayObJSON;
    private Gson generaJSON;
    public ae()
    {
        banderaAE = false;
        arrayObJSON = new ArrayList<String>();
        generaJSON = new Gson();
    }
    public String getSistemaEcuacionesJSON()
    {
        arrayObJSON.remove(arrayObJSON.size()-1);
        return generaJSON.toJson(arrayObJSON);
    }
    public void setAlfa(int alfa)
    {
        this.alfa = alfa;
    }
    public void setModulo(int modulo)
    {
        this.modulo = modulo;
    }
    public boolean getMCD()
    {
        aeValida(modulo, alfa);
        return banderaAE;
    }
    private void creaEcuacion(ecuacion agregar)
    {
        agregar.alfaRecursivo *= -1;
        agregar.xModulo = 1;
        arrayObJSON.add(generaJSON.toJson(agregar));
    }
    private int aeValida(int moduloRecursivo, int alfaRecursivo)
    {

        int mcd = moduloRecursivo%alfaRecursivo;
        ecuacion nuevaEcuacion = new ecuacion();

        nuevaEcuacion.moduloRecursivo = moduloRecursivo;
        nuevaEcuacion.alfaRecursivo = alfaRecursivo;
        nuevaEcuacion.xAlfa = (int)moduloRecursivo/alfaRecursivo;
        nuevaEcuacion.mcd = mcd;

        creaEcuacion(nuevaEcuacion);

        if(mcd == 1)
            banderaAE = true;

        if(mcd == 0)
            return mcd;
        else
            return aeValida(alfaRecursivo, mcd);
    }
}