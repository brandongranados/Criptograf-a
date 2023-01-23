package practica2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class interfaz extends JFrame {

	private JPanel contentPane;
	private JTextField capturaAlfa;
	private JTextField capturaBeta;
	private JTextField capturaModulo;
	private aee euclidesExt;
	private Gson obJSON;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1082, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, -37, 1082, 544);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(new Color(1, 85, 187));
		panelIzquierdo.setBounds(-13, 0, 358, 546);
		panel.add(panelIzquierdo);
		panelIzquierdo.setLayout(null);
		
		capturaAlfa = new JTextField();
		capturaAlfa.setForeground(new Color(0, 0, 0));
		capturaAlfa.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		capturaAlfa.setBackground(new Color(255, 255, 255));
		capturaAlfa.setBounds(57, 75, 257, 27);
		panelIzquierdo.add(capturaAlfa);
		capturaAlfa.setColumns(10);
		
		capturaBeta = new JTextField();
		capturaBeta.setBounds(57, 141, 257, 27);
		panelIzquierdo.add(capturaBeta);
		capturaBeta.setColumns(10);
		
		capturaModulo = new JTextField();
		capturaModulo.setBounds(57, 189, 257, 27);
		panelIzquierdo.add(capturaModulo);
		capturaModulo.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Alfa");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(171, 45, 40, 35);
		panelIzquierdo.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Beta");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1.setBounds(155, 114, 56, 15);
		panelIzquierdo.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Modulo");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setBounds(155, 172, 77, 15);
		panelIzquierdo.add(lblNewLabel_2);
		
		TextArea resultado = new TextArea();
		resultado.setEditable(false);
		resultado.setEnabled(false);
		resultado.setBounds(55, 246, 264, 189);
		panelIzquierdo.add(resultado);
		
		JLabel panelDerecho = new JLabel("");
		panelDerecho.setIcon(new ImageIcon("/home/brandon/Documentos/cryptography-scaled-e1593335036543.jpg"));
		panelDerecho.setBounds(338, -50, 920, 715);
		panel.add(panelDerecho);
		
		JButton enviar = new JButton("Mostrar Funciones");
		enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				euclidesExt = new aee();
				obJSON = new Gson();
				
				euclidesExt.setModulo(Integer.parseInt(capturaModulo.getText()));
				euclidesExt.setAlfa(Integer.parseInt(capturaAlfa.getText()));
				euclidesExt.setBeta(Integer.parseInt(capturaBeta.getText()));
				objBandera estado = obJSON.fromJson(euclidesExt.getMensajeRespuesta(), objBandera.class);
				if(!estado.boolEstado)
					resultado.setText(estado.msm);
				else
					resultado.setText("EK : "+euclidesExt.getFuncionEK()+"\n DK : "+euclidesExt.getFuncionDK());
			}
		});
		enviar.setFont(new Font("Bitstream Charter", Font.BOLD, 16));
		enviar.setBackground(new Color(0, 0, 0));
		enviar.setForeground(new Color(255, 255, 255));
		enviar.setBounds(94, 456, 178, 25);
		panelIzquierdo.add(enviar);
	}
}
