package es.ieslavereda.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Toolkit;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FormularioPrincipal extends JFrame {

	private JPanel contentPane;
	private static JFrame frame;
	private File ficheroBBDD;

	/**
	 * Create the frame.
	 */
	public FormularioPrincipal() {
		frame = this;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormularioPrincipal.class.getResource("/es/ieslavereda/images/logo_compacto.png")));
		setTitle("Formulario Principal");

		ficheroBBDD = new File("./configuracion");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 328);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem mISalir = new JMenuItem("Salir");
		mISalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnArchivo.add(mISalir);

		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);

		JMenuItem mIPreferencias = new JMenuItem("Preferencias");
		mIPreferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormularioConfiguracion fc = new FormularioConfiguracion(ficheroBBDD);
				fc.setVisible(true);

			}
		});
		mnEditar.add(mIPreferencias);

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);

		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ayuda a = new Ayuda();
				a.setVisible(true);
			}
		});
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton bGestionJugadores = new JButton("Gesiton de Jugadores");
		bGestionJugadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionJugadores gj = new GestionJugadores(ficheroBBDD);
				gj.setFrameAnterior(frame);
				gj.setFrameActual(gj);
				gj.setVisible(true);
				dispose();
			}
		});

		JButton btnPrueba = new JButton("Prueba");
		btnPrueba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Prueba p = new Prueba(ficheroBBDD);

					p.setVisible(true);
					p.setFrameAnterior(frame);
					frame.setVisible(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);

		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(90, Short.MAX_VALUE)
								.addComponent(bGestionJugadores, GroupLayout.PREFERRED_SIZE, 256,
										GroupLayout.PREFERRED_SIZE)
								.addGap(87))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addGap(158)
								.addComponent(btnPrueba).addContainerGap(158, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(bGestionJugadores)
						.addPreferredGap(ComponentPlacement.RELATED, 190, Short.MAX_VALUE).addComponent(btnPrueba)
						.addContainerGap()));

		contentPane.setLayout(gl_contentPane);
	}
}
