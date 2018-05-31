/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Programacion 2
 */
public class autorizacionRH {
       private Connection userConn;
     //se autorizan las incidencias  
      public void autorizar(String cod, String fec)  throws SQLException{
       String sql="UPDATE incidencias SET actualizadoRH='AUTORIZADO' WHERE empleadoId='"+cod+"' AND fecha='"+fec+"'";
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
    //se niegan la o las incidencias 
    public void negar(String cod, String fec)  throws SQLException{
       String sql="UPDATE incidencias SET actualizadoRH='NEGADO' WHERE empleadoId='"+cod+"' AND fecha='"+fec+"'";
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
