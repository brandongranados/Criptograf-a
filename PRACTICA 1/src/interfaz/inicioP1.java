package interfaz;

import java.io.BufferedReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;

public class inicioP1 {
	private PipedWriter salidaHilo1;
    private PipedReader entradaHilo2;
	public static class hilos extends Thread
    {
        private ventanas creaVentana;
        private int opcion;
        private PipedReader entrada;
        private PipedWriter salida;
        private void crearVentanas()
        {
        	try {
                creaVentana = new ventanas();
                creaVentana.setVisible(true);
            } catch (Exception e) {}
        }
        private void crearVentanas(String carrera)
        {
        	try {
                creaVentana = new ventanas(carrera);
                creaVentana.setVisible(true);
            } catch (Exception e) {}
        }
        public hilos(int opcion, PipedWriter salida, PipedReader entrada)
        {
            this.opcion = opcion;
            this.entrada = entrada;
            this.salida = salida;
        }
        public void run()
        {
        	if(opcion == 1)
            {
                PrintWriter enviaSms = new PrintWriter(salida);
                crearVentanas();
                while(true)
                    if(creaVentana.getCierre_de_Ventana())
                    {
                        enviaSms.write(creaVentana.getJSONLlaveRutaNombreArc());
                        enviaSms.close();
                        break;
                    }
                    else
                        try {
                            sleep(10);
                        } catch (Exception e) {}
            }
            else
            {
                BufferedReader recibeSms = new BufferedReader(entrada);
                String comienzaCarrera = "";
                while(true)
                    try {
                        comienzaCarrera = recibeSms.readLine();
                        break;
                    } catch (Exception e) {
                        try {
                            sleep(10);
                        } catch (InterruptedException e1) {}
                    }
                    crearVentanas(comienzaCarrera);
            }
        }
    }
    
    public inicioP1()
    {
        try {
            salidaHilo1 = new PipedWriter();
            entradaHilo2 = new PipedReader(salidaHilo1);
        } catch (Exception e) {}

        hilos hilo = new hilos(1, salidaHilo1 , null);
        hilos hilo2 = new hilos(2, null, entradaHilo2);
	        try {
	            hilo.start();
                hilo2.start();

	            hilo.join();
                hilo2.join();
	        } catch (Exception e) {}
    }
    public static void main(String[] args) {
        inicioP1 nuevo1 = new inicioP1();
    }
}
