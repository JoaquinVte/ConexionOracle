package javaOracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author PC-ORACLE
 */


public class ConexionOracle{
    
    private Connection conexion;

     public Connection getConexion() {
        return conexion;
    }
     

   
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    public Connection Conectar()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String BaseDeDatos = "jdbc:oracle:thin:@192.168.1.42:1521:ORCL";
            conexion= DriverManager.getConnection(BaseDeDatos,"MUNDIAL","1234");
            if(conexion!=null)
                System.out.println("Conexión realizada con éxisto a MUNDIAL");
            else
                System.out.println("Conexión fallida");
        }
        catch(Exception e)
            {System.out.println("FALLOOOOO EXCEPCION!!!"); e.printStackTrace();}

        return conexion;
    }

    public void llenarDesplegable(String tabla, JComboBox desplegable, int posicionCampo, String colWhere, String valWhere)
    {
        try {
                Connection con = null;
                ResultSet rs = null;
                CallableStatement cs = null;
                ConexionOracle obconeccion=new ConexionOracle();
                con=obconeccion.Conectar();
                String sql;
                if(colWhere==null)
                    sql ="{?=call prueba.LLenar_Desplegables(?,?)}";
                else
                    sql ="{?=call prueba.LLenar_Desplegables(?,?,?,?)}";
                cs = con.prepareCall(sql);

            //String proc = SqlTools.buildProcedureCall("prueba", "busqPartidos", 5);
            //cs = con.prepareCall(proc);
                int pos = 0;
            
            // Cargamos los parametros de entrada IN
                cs.registerOutParameter(++pos, OracleTypes.CURSOR);
                cs.setString(++pos, tabla);  
                cs.setInt(++pos, posicionCampo);
                if(colWhere!=null)
                {
                    cs.setString(++pos, colWhere);  
                    cs.setString(++pos, valWhere);                      
                }

                // Ejecutamos
                cs.execute();
            // Cosechamos los parametros de salida OUT
            //resultado = cs.getInt(3);           // Nuestro number
                rs = (ResultSet) cs.getObject(1);   // Nuestro cursor, convertido en ResultSet
                desplegable.removeAllItems();
                desplegable.addItem(null);
                while(rs.next())
                    desplegable.addItem(rs.getString(posicionCampo));
//                desplEquipos.setModel(modeloLista);
                SqlTools.close(rs, cs, con);
        } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
    }    
    
}


