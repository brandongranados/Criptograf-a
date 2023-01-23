import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class interfaz extends JFrame {

	private JPanel contentPane;
	private File arch;
	private rsa nuevo;
    private hash nuevoHash;
    private int modo;
    private JTextField respuesta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					interfaz frame = new interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public interfaz() {
		nuevo = new rsa();
	    nuevoHash = new hash();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 644, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton firmarDoc = new JButton("Firmar Documento");
		firmarDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//FIRMAR DOCUMENTO
				
		        try {
		        	if(modo == 0)
		        		nuevo.setParametros(nuevo.MODO_CIFRADOR, nuevo.LLAVE_PRIVADA);
		        	else
		        		nuevo.setParametros(nuevo.MODO_CIFRADOR, nuevo.LLAVE_PUBLICA);
		            nuevo.getArcchivoFirmado(arch);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		});
		firmarDoc.setBounds(97, 47, 169, 25);
		contentPane.add(firmarDoc);
		
		JButton revisarDoc = new JButton("Revisar Documento");
		revisarDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//revisar documento firmado
		        try {
		        	JFileChooser selImagen = new JFileChooser();
		            selImagen.showOpenDialog(selImagen);
		            File arch = selImagen.getSelectedFile();
		            
		            if(modo == 0)
		            	nuevo.setParametros(nuevo.MODO_DESCIFRADOR, nuevo.LLAVE_PUBLICA);
		            else
		            	nuevo.setParametros(nuevo.MODO_DESCIFRADOR, nuevo.LLAVE_PRIVADA);
		            
		            if(nuevo.getRevisarArchivoFirmado(arch))
		            	respuesta.setText("ES QUIEN DICE SER");
		            else
		            	respuesta.setText("Archivo corrompido en el camino");
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		});
		revisarDoc.setBounds(352, 47, 180, 25);
		contentPane.add(revisarDoc);
		
		JButton inpuLlavePriv = new JButton("Ingresar Llave Privada");
		inpuLlavePriv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//INGRESAR LLAVE PRIVADA
				JFileChooser selImagen = new JFileChooser();
	            selImagen.showOpenDialog(selImagen);
	            File archivo = selImagen.getSelectedFile();
	            
	            nuevo.setLlavePrivada(archivo.getAbsolutePath());
			}
		});
		inpuLlavePriv.setBounds(97, 129, 192, 25);
		contentPane.add(inpuLlavePriv);
		
		JButton inpuLlavePub = new JButton("Ingresar Llave Publica");
		inpuLlavePub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//INGRESAR LLAVE PUBLICA
				JFileChooser selImagen = new JFileChooser();
	            selImagen.showOpenDialog(selImagen);
	            File archivo = selImagen.getSelectedFile();
	            
	            nuevo.setLlavePublica(archivo.getAbsolutePath());
			}
		});
		inpuLlavePub.setBounds(352, 129, 198, 25);
		contentPane.add(inpuLlavePub);
		
		JButton generarLlavex = new JButton("Generar Par de Llaves");
		generarLlavex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//GENERAR PAR DE LLAVES
				nuevo.getLLaves();
			}
		});
		generarLlavex.setBounds(298, 183, 192, 25);
		contentPane.add(generarLlavex);
		
		JButton escogerDoc = new JButton("Seleccionar documento y generar digesto");
		escogerDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//SELECCIONAR DOCUMENTO Y GENERAR DIGESTO
				try
				{
					JFileChooser selImagen = new JFileChooser();
		            selImagen.showOpenDialog(selImagen);
		            arch = selImagen.getSelectedFile();
		            
		            nuevo.setDigesto(nuevoHash.getDigesto(arch));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		escogerDoc.setBounds(154, 252, 336, 25);
		contentPane.add(escogerDoc);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Llave Privada");
		comboBox.addItem("Llave Publica");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modo = comboBox.getSelectedIndex();
			}
		});
		
		comboBox.setBounds(131, 183, 135, 24);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Cifrar con:");
		lblNewLabel.setBounds(37, 177, 86, 36);
		contentPane.add(lblNewLabel);
		
		respuesta = new JTextField();
		respuesta.setBounds(164, 289, 368, 19);
		contentPane.add(respuesta);
		respuesta.setColumns(10);
	}
}
