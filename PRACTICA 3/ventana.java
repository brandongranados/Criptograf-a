import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ventana extends JFrame {

	private JPanel contentPane;
	private JTextField llave;
	private JTextField vector;
	private File archSel;
	private cifrador cif;
	private imagenes nuevaIMG;
	private int modo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventana frame = new ventana();
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
	public ventana() {
		cif = new cifrador();
		nuevaIMG = new imagenes();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 297);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		llave = new JTextField();
		llave.setBounds(71, 68, 206, 19);
		contentPane.add(llave);
		llave.setColumns(10);
		
		vector = new JTextField();
		vector.setBounds(325, 68, 206, 19);
		contentPane.add(vector);
		vector.setColumns(10);
		
		JComboBox selecModoOperacion = new JComboBox();
		selecModoOperacion.addItem("ECB");
		selecModoOperacion.addItem("CBC");
		selecModoOperacion.addItem("CFB");
		selecModoOperacion.addItem("OFB");
		selecModoOperacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modo = selecModoOperacion.getSelectedIndex();
			}
		});
		selecModoOperacion.setBounds(606, 65, 92, 24);
		contentPane.add(selecModoOperacion);
		
		JButton cifrarIMG = new JButton("Cifrar Imagen");
		cifrarIMG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//CIFRAR IMAGEN
				try
				{
					JFileChooser selImagen = new JFileChooser();
		            selImagen.showOpenDialog(selImagen);
		            archSel = selImagen.getSelectedFile();
		            
		            String tipoCifrado = "";
					nuevaIMG.setRutaImagen(archSel);
		            nuevaIMG.setModoFlujoImagen(nuevaIMG.MODO_LECTURA_IMAGEN);
		            byte infoCruda[] = nuevaIMG.getImagen();
		            
					switch(modo)
		            {
		                case 0:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_ECB);
		                	tipoCifrado = "ECB";
		                break;
		                case 1:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_CBC);
		                	tipoCifrado = "CBC";
		                break;
		                case 2:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_CFB);
		                	tipoCifrado = "CFB";
		                break;
		                case 3:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_OFB);
		                	tipoCifrado = "OFB";
		                break;
		            }
		            cif.setParametros(llave.getText(), vector.getText());
		            cif.setInformacion(infoCruda);
		            byte infoCifrada[] = cif.getResultadoCifrador(cif.MODO_CIFRADOR);
		            
		            String nombre = nuevaIMG.getNuevaRuta(nuevaIMG.IMAGEN_CIFRADA, tipoCifrado);
		            
		            nuevaIMG.getImagen(infoCifrada, nombre);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		cifrarIMG.setBounds(167, 144, 139, 25);
		contentPane.add(cifrarIMG);
		
		JButton descifrarIMG = new JButton("Descifrar Imagen");
		descifrarIMG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					JFileChooser selImagen = new JFileChooser();
		            selImagen.showOpenDialog(selImagen);
		            archSel = selImagen.getSelectedFile();
		            
		            String tipoCifrado = "";
					nuevaIMG.setRutaImagen(archSel);
		            nuevaIMG.setModoFlujoImagen(nuevaIMG.MODO_ESCRITURA_IMAGEN);
		            byte infoCruda[] = nuevaIMG.getImagen();
		            
		            switch(modo)
		            {
		                case 0:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_ECB);
		                	tipoCifrado = "ECB";
		                break;
		                case 1:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_CBC);
		                	tipoCifrado = "CBC";
		                break;
		                case 2:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_CFB);
		                	tipoCifrado = "CFB";
		                break;
		                case 3:
		                	cif.setModoOpercion(cif.SWICTH_MODO_OPERACION_OFB);
		                	tipoCifrado = "OFB";
		                break;
		            }
		            cif.setParametros(llave.getText(), vector.getText());
		            cif.setInformacion(infoCruda);
		            byte infoDescifrada[] = cif.getResultadoCifrador(cif.MODO_DESCIFRADOR);
		            
		            String nombre = nuevaIMG.getNuevaRuta(nuevaIMG.IMAGEN_DESCIFRADA, tipoCifrado);
		            
		            nuevaIMG.getImagen(infoDescifrada, nombre);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		descifrarIMG.setBounds(519, 144, 154, 25);
		contentPane.add(descifrarIMG);
		
		JLabel lblNewLabel = new JLabel("Ingresar llave");
		lblNewLabel.setBounds(118, 41, 113, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Vector inicialización");
		lblNewLabel_1.setBounds(351, 41, 172, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Modo operación");
		lblNewLabel_2.setBounds(594, 41, 122, 15);
		contentPane.add(lblNewLabel_2);
	}
}
