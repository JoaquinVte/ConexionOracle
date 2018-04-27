package javaOracle;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import oracle.jdbc.OracleTypes;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class GestionJugadores extends JFrame {

	private JPanel contentPane;
	private JFrame ventanaPrincipal;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionJugadores frame = new GestionJugadores();
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
	public GestionJugadores() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(12, 12, 244, 24);
		contentPane.add(comboBox);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String equipo = comboBox.getSelectedItem().toString();
				
				Connection con = null;
		        ResultSet rs = null;
		        CallableStatement cs = null; 
		        
		        try{
		        	
		        	con = (new ConexionOracle()).Conectar();
		        	
		        	cs = con.prepareCall(SqlTools.ConstruirLlamadaProcedimiento("prueba", "MOSTRAREQUIPO", 2));
		        	
		        	int pos=0;
		        	cs.setString(++pos, equipo);
		        	cs.registerOutParameter(++pos, OracleTypes.CURSOR);
		        	
		        	cs.execute();
		        	
		        	rs = (ResultSet) cs.getObject(2);   // Nuestro cursor, convertido en ResultSet
		        	
		        	String Titulos[]={" DORSAL "," PUESTO "," NOMBRE "," MUNDIALES JUGADOS "};
		            String fila[]=new String[4];
		            
		            DefaultTableModel modelo = new DefaultTableModel(null, Titulos);
		            
		            while (rs.next()) {        
		                fila[0] = rs.getString("N1");
		                fila[1] = rs.getString("C2");
		                fila[2] = rs.getString("C3");
		                fila[3] = rs.getString("C4");
		                modelo.addRow(fila);
		            }

		            table.setModel(modelo);
		            
		        	
		        }catch(Exception e1){
		        	e1.printStackTrace();
		        }
		
				
				
			}
		});
		btnConsultar.setBounds(268, 12, 117, 25);
		contentPane.add(btnConsultar);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setBounds(646, 363, 117, 25);
		contentPane.add(btnAtras);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 100, 751, 251);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		try{
			ConexionOracle co=new ConexionOracle();
			
	        Connection con = co.Conectar();
	        String sql="SELECT EQUIPO FROM EQUIPOS";
	        
	        Statement s = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,  ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = s.executeQuery(sql);
	        
	        comboBox.removeAllItems();
	        while(rs.next()){
	        	comboBox.addItem(rs.getString("EQUIPO"));
	        }
	        SqlTools.close(rs, s, con);
        
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
