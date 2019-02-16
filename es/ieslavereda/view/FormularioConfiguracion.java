package es.ieslavereda.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.ieslavereda.tools.ConexionOracle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class FormularioConfiguracion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9166214514797178279L;
	private JPanel contentPane;
	private JTextField tFHost;
	private JTextField tFPuerto;
	private JTextField tFUsuario;
	private JTextField tFPassword;
	private JButton btnGuardar;
	private File fichero;

	/**
	 * Create the frame.
	 */
	public FormularioConfiguracion(File ficheroBBDD) {
		setTitle("Configuracion de la conexion");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(FormularioConfiguracion.class.getResource("/images/logo_compacto.png")));
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 317, 297);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblServidor = new JLabel("Servidor");

		tFHost = new JTextField();
		tFHost.setColumns(10);

		JLabel lblPuerto = new JLabel("Puerto");

		tFPuerto = new JTextField();
		tFPuerto.setText("1521");
		tFPuerto.setColumns(10);

		JLabel lblUsuario = new JLabel("Usuario");

		tFUsuario = new JTextField();
		tFUsuario.setColumns(10);

		JLabel lblPassword = new JLabel("Password");

		tFPassword = new JTextField();
		tFPassword.setColumns(10);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					(new ConexionOracle(tFHost.getText(), tFPuerto.getText(), tFUsuario.getText(), tFPassword.getText(),
							ficheroBBDD)).guardar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// Mensaje de confirmacion con logo
				JFrame frm = new JFrame();
				frm.setIconImage((new ImageIcon("./src/images/logo_compacto.png").getImage()));
				frm.setBounds(100, 100, 100, 100);
				

				JOptionPane jp = new JOptionPane("Configuracion Guardada.", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = jp.createDialog(frm, "Aviso");
				
				dialog.setResizable(false);
				dialog.setVisible(true);

				
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblServidor)
								.addComponent(lblPuerto).addComponent(lblUsuario).addComponent(lblPassword))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(btnGuardar)
								.addComponent(tFPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(tFUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(tFPuerto, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(tFHost, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(30)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblServidor).addComponent(
						tFHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblPuerto).addComponent(
						tFPuerto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(49)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblUsuario).addComponent(
						tFUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblPassword).addComponent(
						tFPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE).addComponent(btnGuardar)
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		fichero = ficheroBBDD;

		if (fichero != null) {
			try {
				ConexionOracle co = new ConexionOracle(fichero);
				co.cargar();
				tFHost.setText(co.getHost());
				tFUsuario.setText(co.getUsuario());
				tFPuerto.setText(co.getPuerto());
				tFPassword.setText(co.getPassword());

			} catch (Exception e) {
				e.toString();
			}

		}
	}
}
