package es.ieslavereda.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Ayuda extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 102323559127369421L;
	private JPanel contentPane;

	
	/**
	 * Create the frame.
	 */
	public Ayuda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ayuda.class.getResource("/images/logo_compacto.png")));
		setResizable(false);
		setTitle("OracleMundialJava v1.0");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 369, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[358px]", "[80px][][][][][grow][][][]"));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Ayuda.class.getResource("/images/logo_compacto.png")));
		contentPane.add(lblNewLabel, "cell 0 0,grow");
		
		JLabel lblIesLaVereda = new JLabel("IES La Vereda");
		lblIesLaVereda.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblIesLaVereda, "cell 0 1,alignx center,aligny center");
		
		JLabel lblJoaquinVicenteAlonso = new JLabel("Joaquin Vicente Alonso Saiz");
		contentPane.add(lblJoaquinVicenteAlonso, "cell 0 3,alignx center");
		
		JLabel lblCurso = new JLabel("Curso 2017 / 2018");
		contentPane.add(lblCurso, "cell 0 4,alignx center");
		
		JTextPane txtpnOasdfsd = new JTextPane();
		txtpnOasdfsd.setFont(new Font("Dialog", Font.PLAIN, 13));
		txtpnOasdfsd.setBackground(UIManager.getColor("Label.background"));
		txtpnOasdfsd.setEditable(false);
		txtpnOasdfsd.setText("Ejercicio de programacion y BBDD de 1º de DAW.\nEn este ejercicio se practica la comuncicacion con\nuna BBDD Oracle ejecutada en remoto, asi como\nla ejecucion de procedimientos almacenados y \nla manipulacion de los datos obtenidos \n(VARCHAR,NUMBER,BLOB,CURSORES,..)");
		contentPane.add(txtpnOasdfsd, "cell 0 5,alignx center,growy");
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnAceptar, "cell 0 7,alignx center");
	}
}
