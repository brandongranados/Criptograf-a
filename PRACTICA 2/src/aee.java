import java.util.ArrayList;

import com.google.gson.Gson;

public class aee {
    private ae euclides;
    private objBandera estado;
    private ArrayList<String> listEcuacion;
    private Gson obJSON;
    private int alfa, modulo, beta, alfaInverso, betaInverso;
    private ecuacion resAEE;
    private boolean evitaCalculoDoble, evitaCalculoDoble2;
    public aee()
    {
        listEcuacion = new ArrayList<String>();
        estado = new objBandera();
        euclides = new ae();
        obJSON = new Gson();
        evitaCalculoDoble = true;
        evitaCalculoDoble2 = true;
    }
    public void setBeta(int beta)
    {
        this.beta = beta%modulo;
    }
    public void setAlfa(int alfa)
    {
        this.alfa = alfa%modulo;
        euclides.setAlfa(alfa%modulo);
    }
    public void setModulo(int modulo)
    {
        this.modulo = modulo;
        euclides.setModulo(modulo);
    }
    public String getMensajeRespuesta()
    {
        generaListEcuacionesAEE();        
        return obJSON.toJson(estado);
    }
    public String getFuncionEK()
    {
        if(evitaCalculoDoble)
            calculaAlfaInverso();
        if(evitaCalculoDoble2)
            calculaBetaInverso();
        if(estado.boolEstado)
            return "C = "+alfa+"P + "+beta+" mod "+modulo;
        else
            return "";
    }
    public String getFuncionDK()
    {
        if(evitaCalculoDoble)
            calculaAlfaInverso();
        if(evitaCalculoDoble2)
            calculaBetaInverso();
        if(estado.boolEstado)
            return "P = "+alfaInverso+"[C + "+betaInverso+"] mod "+modulo;
        else
            return "";
    }
    private void calculaAlfaInverso()
    {
        if(estado.boolEstado)
        {
            String temp = listEcuacion.get(listEcuacion.size()-1);
            listEcuacion.remove(listEcuacion.size()-1);
            resAEE = analizaEcuaciones(temp);
            if(resAEE.alfaRecursivo < 0)
            {
                resAEE.alfaRecursivo *= -1;
                resAEE.xAlfa *= -1;
            }
            if(resAEE.moduloRecursivo < 0)
            {
                resAEE.moduloRecursivo *= -1;
                resAEE.xModulo *= -1;
            }
            if(Math.abs(resAEE.alfaRecursivo) == alfa)
                alfaInverso = resAEE.xAlfa;
            else
                alfaInverso = resAEE.xModulo;

            if(alfaInverso < 0)
                alfaInverso = modulo - alfaInverso;
        }
        else
            alfaInverso = 0;

        evitaCalculoDoble = false;
    }
    private void calculaBetaInverso()
    {
        betaInverso = modulo - beta;
        evitaCalculoDoble2 = false;
    }
    private void generaListEcuacionesAEE()
    {
        estado.boolEstado = euclides.getMCD();
        if(estado.boolEstado)
            estado.msm = "OK";
        else
            estado.msm = "Alfa no valido favor de escoger otro";
        listEcuacion = obJSON.fromJson(euclides.getSistemaEcuacionesJSON(), ArrayList.class);
    }
    private ecuacion analizaEcuaciones(String sustX)
    {
        ecuacion a = obJSON.fromJson(sustX, ecuacion.class);
        int signo = 1;

        if( listEcuacion.size() == 0)
            return a;

        ecuacion b = obJSON.fromJson(listEcuacion.get(listEcuacion.size()-1), ecuacion.class);
        listEcuacion.remove(listEcuacion.size()-1);
        
        if(a.alfaRecursivo < 0)
            signo = -1;

	b.moduloRecursivo *= signo;
	b.alfaRecursivo *= signo;

	b.xModulo *= a.xAlfa;
	b.xAlfa *= a.xAlfa;
		
	b.xAlfa += a.xModulo;

        b.mcd = 1;
        return analizaEcuaciones(obJSON.toJson(b));

    }
}
