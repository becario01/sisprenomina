/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.Conexion;

import Data.UsuariosAcIn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Programacion 2
 */
public class controllerBD {
      
        private Connection userConn;
      
    //se activan o desactivan los empleados registrados 
    
    public void desactivar(String cod, String nom)  throws SQLException{
       String sql="UPDATE empleados SET estatus=0 WHERE empleadoId='"+cod+"'and nombre='"+nom+"'";
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            
            stmt = conn.prepareStatement(sql);
          
            rows = stmt.executeUpdate();
            
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }   
    }
    
    public void activar(String cod, String nom)  throws SQLException{
       String sql="UPDATE empleados SET estatus=1 WHERE empleadoId='"+cod+"'and nombre='"+nom+"'";
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
          
            stmt = conn.prepareStatement(sql);
          
            rows = stmt.executeUpdate();
            
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }   
    }
      
}
