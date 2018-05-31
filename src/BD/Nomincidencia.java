/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Controller.Rincidencia;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Becarios
 */
public class Nomincidencia {
    
    
    
    
    
    
   private Connection userConn;

    private final String SQL_INSERT = "INSERT INTO nomIncidencia(nombre, estatus,dias) VALUES(?,1,?)";
    private final String SQL_DESC = "UPDATE nomIncidencia SET estatus=0 WHERE idNomIncidencia=?";
    private final String SQL_ACT = "UPDATE nomIncidencia SET estatus=1 WHERE idNomIncidencia=?";
//    private final String SQL_DELETE = "DELETE FROM persona WHERE id_persona = ?";
     private final String SQL_SELECTIN = "SELECT idNomIncidencia,nombre,estatus,dias FROM nomIncidencia";


    
    
    public Nomincidencia() {
    }

   
    public Nomincidencia(Connection conn) {
        this.userConn = conn;
    }

    
        public int insert(String incidencia,int dias) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setString(index++, incidencia);
            stmt.setInt(index++, dias);
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
            JOptionPane.showMessageDialog(null,"Incidencia registrada");
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }
        
        public ArrayList<Rincidencia> obteneriIncidenciaPorCriterio(String criterio){
    
        ArrayList<Rincidencia> listarIncidencias = new ArrayList<Rincidencia>();
          Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
       try {
            
             
             conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            String sql = "SELECT * FROM nomIncidencia "
                    + "WHERE idNomIncidencia LIKE '"+criterio+"%'"
                    + "OR nombre LIKE '%"+criterio+"%'"
                    + "order by idNomIncidencia";
            stmt =  conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("idNomIncidencia");
                String nombre = rs.getString("nombre");
                int estatus = rs.getInt("estatus");
                int diass  = rs.getInt("dias");
                
                Rincidencia producto = new Rincidencia(id,nombre,estatus,diass);
                listarIncidencias.add(producto);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            
            Conexion.close(rs);
            Conexion.close(stmt);
           
        }
       return listarIncidencias;
    }
       
       
           public int Desactivar(String status)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_DESC);
            stmt = conn.prepareStatement(SQL_DESC);
            int index = 1;
        
            stmt.setString(index, status);
            rows = stmt.executeUpdate();
            
            System.out.println("Registros actualizados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
              public int Activar(String id)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_ACT);
            stmt = conn.prepareStatement(SQL_ACT);
            int index = 1;
        
            stmt.setString(index, id);
            rows = stmt.executeUpdate();
            
            System.out.println("Registros actualizados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
              
              
               public ArrayList<Rincidencia> obtenerIncnidecnias(){
        ArrayList<Rincidencia> listaIncidecnias = new ArrayList<Rincidencia>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECTIN);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("idNomIncidencia");
                String nombre = rs.getString("nombre");
                int estatus = rs.getInt("estatus");
             int dia = rs.getInt("dias");
            Rincidencia sincidencia = new Rincidencia(id,nombre,estatus,dia);
            listaIncidecnias.add(sincidencia);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(stmt);
        }
       return listaIncidecnias;
    }

               
               
                 
    
}
