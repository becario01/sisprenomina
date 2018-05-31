/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import Conexion.Conexion;
import Controller.EIncidencia;
import View.RH_Calculofaltas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Becarios
 */
public class RegistrarIncidencia {
    
    private Connection userConn;
 

         public RegistrarIncidencia() {
    }

   
    public RegistrarIncidencia(Connection conn) {
        this.userConn = conn;
    }

    
    
    public int insert(int empleadoId,String dia,String fecha,String horasextra,String comentario,int idSemana,int idNomIncidencias,String horasTrab) throws SQLException {
      Connection conn = null;
     PreparedStatement stmt = null;
        int rows = 0;
        try {
            String SQL_INSERT = "INSERT INTO incidencias (empleadoId ,dia,fecha ,horasExtra ,comentario ,idSemana ,idNomIncidencia,horasTrab,actualizadoJA) VALUES ('"+empleadoId+"','"+dia+"' ,'"+fecha+"','"+horasextra+"','"+comentario+"','"+idSemana+"','"+idNomIncidencias+"','"+horasTrab+"','AUTORIZADO')";
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
//            stmt.setString(index, persona.getApellido());
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
           
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
      

        return rows;
       

       
    }
    
    
  
    
    
    
      public ArrayList<EIncidencia> obteneriRincidenciaPorCriterio(String criterio){
    
        ArrayList<EIncidencia> listarRIncidencias = new ArrayList<EIncidencia>();
          Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
       try {
            
             
             conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            String sql = "SELECT emp.empleadoId ,\n" +
"emp.nombre, \n" +
"STUFF(( SELECT ','+nomin.nombre \n" +
"FROM  incidencias inc\n" +
"INNER JOIN NomIncidencia nomin\n" +
"ON nomin.idNomIncidencia = inc.idNomIncidencia\n" +
" WHERE\n" +
"                 emp.empleadoId = inc.empleadoId\n" +
"  and  	inc.idSemana = '1'\n" +
"               order by fecha \n" +
"               FOR XML PATH('')\n" +
"             ) , 1, 1,'')as datos,\n" +
"\n" +
"               STUFF(( SELECT ','+inc.dia\n" +
"               FROM  incidencias inc\n" +
"        INNER JOIN NomIncidencia nomin\n" +
" ON nomin.idNomIncidencia = inc.idNomIncidencia\n" +
" WHERE\n" +
"                 emp.empleadoId = inc.empleadoId\n" +
"               order by fecha \n" +
"               FOR XML PATH('')\n" +
"             ) , 1, 1,'')as dias\n" +
"\n" +
"  FROM empleados emp\n" +
"\n" +
"where emp.empleadoId LIKE '"+criterio+"%' OR emp.nombre LIKE '%"+criterio+"%'";
            stmt =  conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                
               String FLunesInc="";
          String FMartesInc="";
           String FMiercolesInc="";
            String FJuevesInc="";
            String FViernesInc="";
             String FSabadoInc="";
              String FDomingoInc="";
            
          
                      String DL="",DMA="", DMI="", DJ="", DV="", DS="", DD="";
                     String LunesInc = "";
                     String MartesInc ="";
                     String MiercolesInc = "";
                     String   JuevesInc = "";
                     String   ViernesInc = "";
                     String   SabadoInc ="";
                     String   DomingoInc ="";
                      String empid="";
                       String nom ="";
                     
              if(rs.getString("datos") == null || rs.getString("datos")== ""){
                         empid = rs.getString("empleadoId");
                         nom = rs.getString("nombre");
                       
                     }else{
                          empid = rs.getString("empleadoId");
                          nom = rs.getString("nombre");
                    String Dias = rs.getString("datos");
                   String Fechas = rs.getString("dias");
                    if (Dias.contains(",")) {
                      
                     String[] days = Dias.split(",");
                     String[] dates = Fechas.split(",");
                       
                        
                            if (0 == days.length && 0 == dates.length) {
                                 Dias += ", , , , , , , ,"; 
                                 Fechas += ", , , , , , , ,"; 
                            }
                            if (1 == days.length && 1 == dates.length) {
                                Dias += " , , , , , , ,";
                                Fechas += " , , , , , , ,";
                            }
                            if (2 == days.length && 2 == dates.length) {
                                Dias += " , , , , , ,";
                                Fechas += " , , , , , ,";
                                
                            }
                             if (3 == days.length && 3 == dates.length) {
                                Dias += " , , , , ,";
                                Fechas += " , , , , ,";
                                
                            }
                              if (4 == days.length && 4 == dates.length) {
                                Dias += " , , , ,";
                                Fechas += " , , , ,";
                                
                            }
                            if (5 == days.length && 5 == dates.length) {
                                Dias += " , , ,";
                                Fechas += " , , ,";
                                
                            }
                            if (6 == days.length && 6 == dates.length) {
                                Dias += " , ,";
                                Fechas += " , ,";
                                
                            }
                        
                       days = Dias.split(",");
                       dates = Fechas.split(",");     
                       FLunesInc = dates[0];
                       FMartesInc = dates[1];
                       FMiercolesInc = dates[2];
                       FJuevesInc = dates[3];
                       FViernesInc = dates[4];
                       FSabadoInc = dates[5];
                       FDomingoInc = dates[6];
                       FLunesInc = FLunesInc.replaceAll(" ", "");
                       FMartesInc = FMartesInc.replaceAll(" ", "");
                       FMiercolesInc = FMiercolesInc.replaceAll(" ", "");
                       FJuevesInc = FJuevesInc.replaceAll(" ", "");
                       FViernesInc = FViernesInc.replaceAll(" ", "");
                       FSabadoInc = FSabadoInc.replaceAll(" ", "");
                       FDomingoInc = FDomingoInc.replaceAll(" ", "");
                  

                   
//                            ¿
          //lunes
                        if (FLunesInc.equalsIgnoreCase("L")) {
                         LunesInc = days[0];
                     
                        }else if (FLunesInc.equalsIgnoreCase("Ma")) {
                            MartesInc  = days[0];
                            
                        }else if (FLunesInc.equalsIgnoreCase("Mi")) {
                            MiercolesInc = days[0];
                        }else if (FLunesInc.equalsIgnoreCase("J")) {
                            JuevesInc = days[0];
                            
                        }else if (FLunesInc.equalsIgnoreCase("V")) {
                            ViernesInc = days[0];
                        }
                        else if (FLunesInc.equalsIgnoreCase("S")) {
                            SabadoInc = days[0];
                        }
                        else if (FLunesInc.equalsIgnoreCase("D")) {
                            DomingoInc = days[0];
                        }
                 //martes        
                          if (FMartesInc.equalsIgnoreCase("L")) {
                         LunesInc = days[1];
                     
                        }else if (FMartesInc.equalsIgnoreCase("Ma")) {
                            MartesInc  = days[1];
                            
                        }else if (FMartesInc.equalsIgnoreCase("Mi")) {
                            MiercolesInc = days[1];
                        }else if (FLunesInc.equalsIgnoreCase("J")) {
                            JuevesInc = days[1];
                            
                        }else if (FMartesInc.equalsIgnoreCase("V")) {
                            ViernesInc = days[1];
                        }
                        else if (FMartesInc.equalsIgnoreCase("S")) {
                            SabadoInc = days[1];
                        }
                        else if (FMartesInc.equalsIgnoreCase("D")) {
                            DomingoInc = days[1];
                        }
                          
                          //miercoles 
                            if (FMiercolesInc.equalsIgnoreCase("L")) {
                         LunesInc = days[2];
                     
                        }else if (FMiercolesInc.equalsIgnoreCase("Ma")) {
                            MartesInc  = days[2];
                            
                        }else if (FMiercolesInc.equalsIgnoreCase("Mi")) {
                            MiercolesInc = days[2];
                        }else if (FLunesInc.equalsIgnoreCase("J")) {
                            JuevesInc = days[2];
                            
                        }else if (FMiercolesInc.equalsIgnoreCase("V")) {
                            ViernesInc = days[2];
                        }
                        else if (FMiercolesInc.equalsIgnoreCase("S")) {
                            SabadoInc = days[2];
                        }
                        else if (FMiercolesInc.equalsIgnoreCase("D")) {
                            DomingoInc = days[2];
                        }
                            
                            
                            //jueves
                              if (FJuevesInc.equalsIgnoreCase("L")) {
                         LunesInc = days[3];
                     
                        }else if (FJuevesInc.equalsIgnoreCase("Ma")) {
                            MartesInc  = days[3];
                            
                        }else if (FJuevesInc.equalsIgnoreCase("Mi")) {
                            MiercolesInc = days[3];
                        }else if (FJuevesInc.equalsIgnoreCase("J")) {
                            JuevesInc = days[3];
                            
                        }else if (FJuevesInc.equalsIgnoreCase("V")) {
                            ViernesInc = days[3];
                        }
                        else if (FJuevesInc.equalsIgnoreCase("S")) {
                            SabadoInc = days[3];
                        }
                        else if (FJuevesInc.equalsIgnoreCase("D")) {
                            DomingoInc = days[3];
                        }
                              
                              
                              //viernes 
                                if (FViernesInc.equalsIgnoreCase("L")) {
                         LunesInc = days[4];
                     
                        }else if (FViernesInc.equalsIgnoreCase("Ma")) {
                            MartesInc  = days[4];
                            
                        }else if (FViernesInc.equalsIgnoreCase("Mi")) {
                            MiercolesInc = days[4];
                        }else if (FViernesInc.equalsIgnoreCase("J")) {
                            JuevesInc = days[4];
                            
                        }else if (FViernesInc.equalsIgnoreCase("V")) {
                            ViernesInc = days[4];
                        }
                        else if (FViernesInc.equalsIgnoreCase("S")) {
                            SabadoInc = days[4];
                        }
                        else if (FViernesInc.equalsIgnoreCase("D")) {
                            DomingoInc = days[4];
                        }
                                
                                
                                
                                //sabado
                       if (FSabadoInc.equalsIgnoreCase("L")) {
                         LunesInc = days[5];
                        }else if (FSabadoInc.equalsIgnoreCase("Ma")) {
                            MartesInc  = days[5];
                            
                        }else if (FSabadoInc.equalsIgnoreCase("Mi")) {
                            MiercolesInc = days[5];
                        }else if (FSabadoInc.equalsIgnoreCase("J")) {
                            JuevesInc = days[5];
                            
                        }else if (FSabadoInc.equalsIgnoreCase("V")) {
                            ViernesInc = days[5];
                        }
                        else if (FSabadoInc.equalsIgnoreCase("S")) {
                            SabadoInc = days[5];
                        }
                        else if (FSabadoInc.equalsIgnoreCase("D")) {
                            DomingoInc = days[5];
                        }
                                  
                                  
                                  //domingo
                        if (FDomingoInc.equalsIgnoreCase("L")) {
                         LunesInc = days[6];
                     
                        }else if (FDomingoInc.equalsIgnoreCase("Ma")) {
                            MartesInc  = days[6];
                            
                        }else if (FDomingoInc.equalsIgnoreCase("Mi")) {
                            MiercolesInc = days[6];
                        }else if (FDomingoInc.equalsIgnoreCase("J")) {
                            JuevesInc = days[6];
                            
                        }else if (FDomingoInc.equalsIgnoreCase("V")) {
                            ViernesInc = days[6];
                        }
                        else if (FDomingoInc.equalsIgnoreCase("S")) {
                            SabadoInc = days[6];
                        }
                        else if (FDomingoInc.equalsIgnoreCase("D")) {
                            DomingoInc = days[6];
                        }
                          
                          
                          
                          
                          
                      
                    
                 }
       
              }
           
              String FL =LunesInc;
              String FM=MartesInc;
              String FMI =MiercolesInc;
              String FJ =JuevesInc;
              String FV=ViernesInc;
              String FS =SabadoInc;
              String FD =DomingoInc;       
               
                
                EIncidencia incidencias = new EIncidencia(empid,nom,FL,FM,FMI,FJ,FV,FS,FD);
                listarRIncidencias.add(incidencias);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            
            Conexion.close(rs);
            Conexion.close(stmt);
           
        }
       return listarRIncidencias;
    }
    
  
      public void calculoFaltas(String fechaini,String fechafin,String inid){
          
           Connection conn = null;
                 PreparedStatement stmt = null;
                 ResultSet rs = null;
            try {
                String sql = "SELECT DISTINCT registros.*,DATENAME(dw, registros.fecha) as inidias,semanas.idSemana FROM registros\n"
                        + "LEFT JOIN incidencias ON registros.empleadoId <> incidencias.idIncidencia\n"
                        + "AND registros.fecha = incidencias.fecha\n"
                        + "LEFT JOIN semanas ON semanas.fechaL = registros.fecha\n"
                        + "OR semanas.fechaMa = registros.fecha\n"
                        + "OR semanas.fechaMi = registros.fecha\n"
                        + "OR semanas.fechaJ = registros.fecha\n"
                        + "OR semanas.fechaV = registros.fecha\n"
                        + "OR semanas.fechaS = registros.fecha\n"
                        + "OR semanas.fechaD = registros.fecha\n"
                        + "WHERE\n"
                        + "registros.fecha BETWEEN '" + fechaini + "'\n"
                        + "AND '" + fechafin + "'\n";
                conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
              
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int empleadoId = rs.getInt(2);
                    String entrada = rs.getString(3);
                    String salida = rs.getString(4);
                    String fecha = rs.getString(6);
                    String nomdfecha = rs.getString(7);
                    int idSemana = rs.getInt(8);
                    if (nomdfecha.equalsIgnoreCase(inid)) {
                     RH_Calculofaltas calf = new RH_Calculofaltas() ;
                  String  fechas =   RH_Calculofaltas.obtenerDiaSemana(fecha);
                        System.out.println(id + "\t "+empleadoId +"\t "+entrada+"\t"+salida+"\t"+fecha+"\t"+fechas+"\t"+idSemana);
                        
                        
                         if (entrada.equals("00:00:00") ||  salida.equals("00:00:00")) {
                     String comentario = "Falta  AT";                             
                     int nomincidencia =1;
                     this.insert(empleadoId,fechas,fecha," ", comentario, idSemana,nomincidencia,"5");
                
                    }else{
                        System.out.println("cuentan con entrada");
                    }
                         
                   }
              
                } 
       
             } catch (Exception e) {
                System.out.println(""+e);
             }finally{
                 Conexion.close(rs);
                  Conexion.close(stmt);
             }
      
       
}
}