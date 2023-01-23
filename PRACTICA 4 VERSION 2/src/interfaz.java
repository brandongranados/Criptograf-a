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
				try
				{
					JFileChooser selImagen = new JFileChooser();
		            selImagen.showOpenDialog(selImagen);
		            arch = selImagen.getSelectedFile();
		            
		            nuevo.setDigesto(nuevoHash.getDigesto(arch));
		            nuevo.setParametros(nuevo.MODO_CIFRADOR);
		        	
		            nuevo.getArcchivoFirmado(arch);
				}
				catch(Exception e)
				{
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
		            
		            nuevo.setParametros(nuevo.MODO_DESCIFRADOR);
		            
		            
		            if(nuevo.getRevisarArchivoFirmado(arch))
		            	respuesta.setText("ES QUIEN DICE SER");
		            else
		            	respuesta.setText("CORRUPTO");
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		});
		revisarDoc.setBounds(352, 47, 180, 25);
		contentPane.add(revisarDoc);
		
		JButton inpuLlavePub = new JButton("Ingresar Llave");
		inpuLlavePub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//INGRESAR LLAVE
				
				
				JFileChooser selImagen = new JFileChooser();
	            selImagen.showOpenDialog(selImagen);
	            File archivo = selImagen.getSelectedFile();
	            
	            nuevo.setLlave(archivo.getAbsolutePath());
			}
		});
		inpuLlavePub.setBounds(221, 118, 198, 25);
		contentPane.add(inpuLlavePub);
		
		respuesta = new JTextField();
		respuesta.setBounds(133, 188, 368, 19);
		contentPane.add(respuesta);
		respuesta.setColumns(10);
	}
}
