package javaOracle;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Struct;
import java.sql.Types;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import oracle.jdbc.internal.OracleTypes;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Seleccionador implements Serializable {
	String name;
	double salary;

	public Seleccionador(String name, double salary) {

		this.name = name;
		this.salary = salary;
	}
}

class Persona implements Serializable {
	int dni;
	String nombre;
	String apellidos;

	public Persona(int dni, String name, String apellidos) {

		this.nombre = name;
		this.dni = dni;
		this.apellidos = apellidos;
	}
	public Struct getStruct(Connection c) throws Exception {
		Object[] atr = {this.dni,this.nombre,this.apellidos};		
		return c.createStruct("PERSONA", atr);
	}
}

public class Prueba extends JFrame {

	private JPanel contentPane;
	private JFrame frameAnterior;
	private File ficheroBBDD;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public Prueba(File f) throws Exception {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frameAnterior.setVisible(true);
				dispose();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 807, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 80, 775, 381);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		ficheroBBDD = f;

		Connection con = null;
		PreparedStatement pstmt = null;
		Struct str=null;
		Object[] attributes;
		try {
			Seleccionador seleccionador = new Seleccionador("Luis", 25000);
			Persona per = new Persona(25000, "Luis", "");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(seleccionador);
			byte[] seleccionadorAsBytes = baos.toByteArray();

			con = new ConexionOracle(ficheroBBDD).Conectar();
			
			
			pstmt = con.prepareStatement("INSERT INTO SELECCIONADORES (ID,SELECCIONADOR,PERSO) VALUES(?,?,?)");
			ByteArrayInputStream bais = new ByteArrayInputStream(seleccionadorAsBytes);
			pstmt.setInt(1, 8);
			pstmt.setBinaryStream(2, bais, seleccionadorAsBytes.length);
			pstmt.setObject(3, per.getStruct(con));
			pstmt.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
		 Statement stmt = con.createStatement();
		
		 ResultSet rs = stmt.executeQuery("SELECT * FROM SELECCIONADORES");
		 while (rs.next()) {
		 byte[] st = rs.getBytes(2);
		 ByteArrayInputStream baip = new ByteArrayInputStream(st);
		 ObjectInputStream ois = new ObjectInputStream(baip);
		 Seleccionador emp = (Seleccionador) ois.readObject();
		 System.out.println(rs.getInt(1));
		 System.out.println(emp.name);
		 System.out.println(emp.salary);
		 
		 str = (Struct)rs.getObject(3);
		 System.out.println("-------------- Objeto ----------");
		 attributes = str.getAttributes();
		
		 System.out.println(attributes[0]);
		 System.out.println(attributes[1]);
		 System.out.println(attributes[2]);
		 
		 }
		 stmt.close();
		 rs.close();
		con.close();
	}

	public void setFrameAnterior(JFrame frameAnterior) {
		this.frameAnterior = frameAnterior;
	}
}
