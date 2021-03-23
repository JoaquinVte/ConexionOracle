package es.ieslavereda.view;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import es.ieslavereda.tools.ConexionOracle;
import es.ieslavereda.tools.SqlTools;
import oracle.jdbc.OracleTypes;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class GestionJugadores extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6818414690672375695L;
	private JPanel contentPane;
	private JTable table;
	private File f;
	private JFrame frameAnterior;
	private JFrame frameActual;

	

	public JFrame getFrameActual() {
		return frameActual;
	}



	public void setFrameActual(JFrame frameActual) {
		this.frameActual = frameActual;
	}



	/**
	 * Create the frame.
	 */
	public GestionJugadores(File f) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestionJugadores.class.getResource("/es/ieslavereda/images/logo_compacto.png")));
		setResizable(false);
		setTitle("Gestion de Jugadores");
		
		
		
		this.f=f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JComboBox<String> comboBox = new JComboBox<String>();
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
		        	
		        	con = (new ConexionOracle(f)).Conectar();
		        	
		        	 cs = con.prepareCall(SqlTools.ConstruirLlamadaProcedimiento("prueba", "MOSTRAREQUIPO", 2));
		        	
		        	//String sql = "{ call MOSTRAREQUIPO (?,?)}";
		        	//cs = con.prepareCall(sql);
		        	
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
		            
		            table.getColumnModel().getColumn(0).setMaxWidth(75);
		            table.getColumnModel().getColumn(1).setMaxWidth(75);
		        	
		        }catch(Exception e1){		        	
		        	e1.printStackTrace();		        
		        }finally{
		        	SqlTools.close(rs, cs,null, con);
		        }			
			}
		});
		btnConsultar.setBounds(268, 12, 117, 25);
		contentPane.add(btnConsultar);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormularioPrincipal fp=new FormularioPrincipal();
				fp.setVisible(true);
				dispose();
			}
		});
		btnAtras.setBounds(646, 363, 117, 25);
		contentPane.add(btnAtras);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 100, 751, 251);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tm = (DefaultTableModel) table.getModel();
				
				String jugador = String.valueOf(tm.getValueAt(table.getSelectedRow(),2));
				frameActual.setVisible(false);
				FormularioJugador fj=new FormularioJugador(jugador,f);
				fj.setVisible(true);
				fj.setFrameAnterior(frameActual);
							
			}
		});
		scrollPane.setViewportView(table);
		
		try{
			ConexionOracle co=new ConexionOracle(this.f);
			
	        Connection con = co.Conectar();
	        String sql="SELECT EQUIPO FROM EQUIPOS";
	        
	        Statement s = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,  ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = s.executeQuery(sql);
	        
	        comboBox.removeAllItems();
	        while(rs.next()){
	        	comboBox.addItem(rs.getString("EQUIPO"));
	        }
	        SqlTools.close(rs, s,null, con);
        
		}catch(Exception e){
			e.printStackTrace();        	
		}
		
	}



	public JFrame getFrameAnterior() {
		return frameAnterior;
	}



	public void setFrameAnterior(JFrame frame) {
		this.frameAnterior = frame;
	}
}
