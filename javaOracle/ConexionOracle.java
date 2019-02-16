package javaOracle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author PC-ORACLE
 */

public class ConexionOracle {

	private Connection conexion;
	private String host;// = "192.168.1.42";
	private String puerto;// = "1521";
	private String usuario;// = "MUNDIAL";
	private String password;// = "1234";
	private File f;

	public Connection getConexion() {
		return conexion;
	}

	public ConexionOracle(File f) {
		this.f = f;
		try {
			this.cargar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ConexionOracle(String host, String puerto, String usuario, String password, File f) {
		super();
		this.host = host;
		this.puerto = puerto;
		this.usuario = usuario;
		this.password = password;
		this.f = f;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public Connection Conectar() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String BaseDeDatos = "jdbc:oracle:thin:@" + host + ":" + puerto + ":ORCL";
			conexion = DriverManager.getConnection(BaseDeDatos, usuario, password);
			if (conexion != null)
				System.out.println("Conexión realizada con éxisto a MUNDIAL");
			else {
				JOptionPane.showMessageDialog(null, "Error al conectar. Revise Configuracion", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Conexión fallida");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("FALLOOOOO EXCEPCION!!!");
			//e.printStackTrace();
		}

		return conexion;
	}

	public void setFile(File f) {
		this.f = f;
	}

	public void guardar() throws FileNotFoundException, IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));

			out.writeObject(host);
			out.writeObject(puerto);
			out.writeObject(usuario);
			out.writeObject(password);

		} catch (Exception e) {

			System.out.print(e);

		} finally {
			out.close();
		}
	}

	public void cargar() throws FileNotFoundException, IOException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));

			this.host = (String) in.readObject();
			this.puerto = (String) in.readObject();
			this.usuario = (String) in.readObject();
			this.password = (String) in.readObject();

		} catch (EOFException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
	}

}
