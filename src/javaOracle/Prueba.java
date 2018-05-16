package javaOracle;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import oracle.jdbc.internal.OracleTypes;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Prueba extends JFrame {

	private JPanel contentPane;

	private File ficheroBBDD;
	private JFrame frame;
	private JTable table;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Create the frame.
	 */
	public Prueba(File f) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				frame.setVisible(true);
				dispose();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 89, 424, 161);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		this.ficheroBBDD=f;
		
		Connection cn =null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		String sql = "{ ? = call funciongoles(?,?) }";
		
		try {
			
			cn=new ConexionOracle(ficheroBBDD).Conectar();
			cs = cn.prepareCall(sql);
			
			int pos=0;
			
			cs.registerOutParameter(++pos, OracleTypes.CURSOR);
			cs.setString(++pos, "ESPAÑA");
			cs.setInt(++pos, 2010);
			
			cs.execute();
			
			rs = (ResultSet)cs.getObject(1);
			
			String[] titulo = {" Minuto " , " Jugador " , " Local " , " Visitante ", " Fecha " };
			String[] fila = new String[5];
			
			DefaultTableModel d = new DefaultTableModel(null,titulo);
			
			while(rs.next()) {
				fila[0]=rs.getObject(1).toString();
				fila[1]=rs.getObject(2).toString();
				fila[2]=rs.getObject(3).toString();
				fila[3]=rs.getObject(4).toString();
				fila[4]=rs.getObject(5).toString();
				d.addRow(fila);
				table.setModel(d);
			}
			
			rs.close();
			cs.close();
			cn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
