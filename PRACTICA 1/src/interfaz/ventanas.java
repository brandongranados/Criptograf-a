package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.awt.event.ActionEvent;

public class ventanas extends JFrame{
	private JPanel ventana;
	private JButton btnCifrado;
	private JButton btnDescifrar;
	private boolean ventanaCerrada;
	private lecturaArchivo leerArch;
	private cifrarArchivo cifrarArch;
	private descifrarArchivo descifrarArch;
	private escrituraArchivo escribeArch;
	private msmJSON msm;
	private Gson objJSON;
	public ventanas() 
	{
		ventanaCerrada = false;
		cifrarArch = new cifrarArchivo();
		escribeArch = new escrituraArchivo();
		creaVentanas();
	}
	public ventanas(String obGSON) 
	{
		ventanaCerrada = false;
		escribeArch = new escrituraArchivo();
		setJSONLlaveRutaNombreArc(obGSON);
		creaVentanas();
	}
	public boolean getCierre_de_Ventana()
	{
		return ventanaCerrada;
	}
	public String getJSONLlaveRutaNombreArc()
	{
		msm = new msmJSON();
		objJSON = new Gson();
		msm.llave = Base64.getEncoder().encodeToString(cifrarArch.getLLaveSimetrica().getEncoded());
		msm.rutaArch = leerArch.getRutaNombreArchivo();
		return objJSON.toJson(msm);
	}
	private void setJSONLlaveRutaNombreArc(String msmJson)
	{
		objJSON = new Gson();
		msm = objJSON.fromJson(msmJson, msmJSON.class);
	}
	private void botonCifrado()
	{
		leerArch = new lecturaArchivo();
		cifrarArch.setContenidoArch(leerArch.getContenidoArchivo());
		escribeArch.setContenidoArchivo(cifrarArch.getArchCifrado());
		escribeArch.setRutaArchivo(leerArch.getRutaNombreArchivo());
		escribeArch.getArchivo();
	}
	private void botonDescifrado()
	{
		leerArch = new lecturaArchivo(true);
		descifrarArch = new descifrarArchivo(new SecretKeySpec(Base64.getDecoder().decode(msm.llave), "DES"));
		descifrarArch.setArchCifrado(leerArch.getContenidoArchivo());
		escribeArch.setContenidoArchivo(descifrarArch.getArchDescifrado(true));
		escribeArch.setRutaArchivo(leerArch.getRutaNombreArchivo(true));
		escribeArch.getArchivo();
	}
	private void creaVentanas()
	{
		setTitle("PRACTICA 1");
		setAutoRequestFocus(false);
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 496);
		ventana = new JPanel();
		ventana.setBackground(Color.GRAY);
		ventana.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ventana);
		ventana.setLayout(null);
		
		btnCifrado = new JButton("Cifrar Archivo");
		btnCifrado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonCifrado();
				dispose();
				try {
					Thread.sleep(4500);
				} catch (InterruptedException e) {}
				ventanaCerrada = true;
			}
		});
		btnCifrado.setForeground(Color.WHITE);
		btnCifrado.setBackground(Color.BLACK);
		btnCifrado.setBounds(38, 42, 157, 93);
		ventana.add(btnCifrado);
		
		btnDescifrar = new JButton("Descifrar Archivo");
		btnDescifrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonDescifrado();
				dispose();
				try {
					Thread.sleep(4500);
				} catch (InterruptedException e) {}
				ventanaCerrada = true;
			}
		});
		btnDescifrar.setForeground(Color.WHITE);
		btnDescifrar.setBackground(Color.BLACK);
		btnDescifrar.setBounds(509, 56, 157, 93);
		ventana.add(btnDescifrar);
	}
}
