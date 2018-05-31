/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import Conexion.Conexion;
import Controller.EIncidencia;
import Controller.EJefes;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Becarios
//// */
public class Rjefes {
    

   private Connection userConn;
   
   private final String sql_select = "SELECT * FROM semanas WHERE estatus ='1' ORDER BY  idSemana ASC";
  


  public Rjefes() {
    }

   
    public Rjefes(Connection conn) {
        this.userConn = conn;
    }

    
    public ArrayList<EJefes> obtenerSemanas(){
        ArrayList<EJefes> listaSemanas = new ArrayList<EJefes>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql_select);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("idSemana");
                String nomsemana = rs.getString("Semana");
                String  fechal = rs.getString("fechaL");
                String  fecham = rs.getString("fechaMa");
                String fechami = rs.getString("fechaMi");
                String fechaj = rs.getString("fechaJ");
                String fechav = rs.getString("fechaV");
                String fechas = rs.getString("fechaS");
                String fechad = rs.getString("fechaD");
                String estatus = rs.getString("estatus");

 
              EJefes semana = new EJefes(id,nomsemana,fechal,fecham,fechami,fechaj,fechav,fechas,fechad,estatus);
             listaSemanas.add(semana);
              
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            Conexion.close(rs);
            Conexion.close(stmt);
        }
       return listaSemanas;
    }

    
       


// public int insert(int empleadoId,String fecha,int horasextra,String comentario,int idSemana,int idNomIncidencias,String horasTrab) throws SQLException {
//      Connection conn = null;
//     PreparedStatement stmt = null;
//        int rows = 0;
//        try {
//            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
//            stmt = conn.prepareStatement(SQL_INSERT);
//            int index = 1;
//            stmt.setInt(index, empleadoId);
//            stmt.setString(index++, fecha);
//            stmt.setInt(index++, horasextra);
//            stmt.setString(index++, comentario);
//            stmt.setInt(index++, idSemana);
//            stmt.setInt(index++, idNomIncidencias);
//            stmt.setString(index++, horasTrab);
//          
//
////            stmt.setString(index, persona.getApellido());
//            System.out.println("Ejecutando query:" + SQL_INSERT);
//            rows = stmt.executeUpdate();
//            System.out.println("Registros afectados:" + rows);
//            JOptionPane.showMessageDialog(null,"Incidencia registrada");
//        } finally {
//            Conexion.close(stmt);
//            if (this.userConn == null) {
//                Conexion.close(conn);
//            }
//        }
//
//        return rows;
//       
//
//       
//    }

    
    
}
