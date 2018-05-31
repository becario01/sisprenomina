/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.Conexion;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Programacion 04
 */
public class PercepcionesReport {

    static Connection conn;
    static PreparedStatement stmt;
    public static ResultSet rs;
    private static Connection userConn;
    static Connection conn1;
    static PreparedStatement stmt1;
    public static ResultSet rs1;
    private static Connection userConn1;
    public static Connection conn5;
    public static PreparedStatement stmt5;
    public static ResultSet rs5;
    private static Connection userConn5;
    static Vector<String> listadoempe = new Vector<String>();
    public static boolean valores = false;
//se genera el segundo encabezado de los registros de percepciones 
    public static List<String> getHeaders() {
        List<String> tableHeader = new ArrayList<>();
        tableHeader.add("PERC / DEDU");
        tableHeader.add("COMENTARIO");
        tableHeader.add("DIA");
        tableHeader.add("FECHA");

        return tableHeader;

    }
// se genera el primer encabezado de los empleados 
    public static List<String> getHeadersnom() {
        List<String> tableHeader = new ArrayList<String>();
        tableHeader.add("ID");
        tableHeader.add("NOMBRE");
        tableHeader.add("DEPARTAMENTO");
        tableHeader.add("PUESTO");
     
        return tableHeader;
    }
//se en listan los datos de registros de percepciones 
    public static List<List<String>> getContent(String idemp, String fecha) {
        List<List<String>> tableContent = new ArrayList<List<String>>();
        List<String> row = null;
        String sql = "SELECT  nper.nombre AS nomper , per.comentario, per.dia, per.fecha\n"
                + "FROM percepciones per \n"
                + "INNER JOIN empleados em on per.empleadoId=em.empleadoId\n"
                + "INNER JOIN nomPercepciones nper on per.idNomPer=nper.idNomPer\n"
                + "where per.fecha='" + fecha + "' and per.empleadoId='" + idemp + "' ";

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            
            
                while (rs.next()) {
                    tableContent.add(row = new ArrayList<String>());
                    row.add(rs.getString("nomper"));
                    row.add(rs.getString("comentario"));
                    row.add(rs.getString("dia"));
                    row.add(rs.getString("fecha"));
                

            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (userConn == null) {
                Conexion.close(conn);
            }

        }

        return tableContent;

    }
//se en listan el id de los empleados con percepciones 
    public static void listarid(Vector<String> dias, String deptonom) {
        listadoempe.clear();
        for (int dia = 0; dia < dias.size(); dia++) {
            String sql;
            String fecharepo = dias.elementAt(dia);

            if (deptonom.contains("-SELECCIONE UNA OPCION-")) {

                sql = "SELECT per.empleadoId AS idemp, em.nombre AS nomemp, em.depto, em.puesto\n"
                        + "FROM percepciones per \n"
                        + "INNER JOIN empleados em on per.empleadoId=em.empleadoId\n"
                        + "INNER JOIN nomPercepciones nper on per.idNomPer=nper.idNomPer\n"
                        + "where per.fecha='" + fecharepo + "'";
            } else {
                sql = "SELECT per.empleadoId AS idemp, em.nombre AS nomemp, em.depto, em.puesto\n"
                        + "FROM percepciones per \n"
                        + "INNER JOIN empleados em on per.empleadoId=em.empleadoId\n"
                        + "INNER JOIN nomPercepciones nper on per.idNomPer=nper.idNomPer\n"
                        + "where per.fecha='" + fecharepo + "' and em.depto='" + deptonom + "'";

            }
            String datos[] = new String[2];

            try {
                conn5 = (PercepcionesReport.userConn5 != null) ? PercepcionesReport.userConn5 : Conexion.getConnection();
                stmt5 = conn5.prepareStatement(sql);
                rs5 = stmt5.executeQuery();
                while (rs5.next()) {
                    boolean boolean1 = false;
                    boolean boolean2 = false;
                    datos[0] = rs5.getString("idemp");
                    if (listadoempe.isEmpty()) {
                        listadoempe.add(datos[0]);
                    }
                    for (int i = 0; i < listadoempe.size(); i++) {
                        boolean1 = listadoempe.elementAt(i).equalsIgnoreCase(datos[0]);
                        if (boolean1) {
                            boolean2 = true;
                        }
                    }
                    if (boolean2) {
                    } else {
                        listadoempe.add(datos[0]);
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            } finally {
                Conexion.close(rs5);
                Conexion.close(stmt5);
                if (PercepcionesReport.userConn5 == null) {
                    Conexion.close(conn5);
                }
            }

        }
    }
//se listan los datos de los empleados con percepciones 
    public static List<List<String>> getContentnombres(String nomdep, Vector<String> dias) {
        
        List<List<String>> tableContent = new ArrayList<List<String>>();
        try {
        List<String> row = null;
        listarid(dias,nomdep);
       
        if(listadoempe.isEmpty()){
             JOptionPane.showMessageDialog(null, "Este rango de fechas no tiene registros", "", JOptionPane.WARNING_MESSAGE);
             valores=false;
        }else{
            valores=true;
        }
        for (int idemp = 0; idemp < listadoempe.size(); idemp++) {
            String empleId=listadoempe.elementAt(idemp);
            
        String sql = "SELECT DISTINCT per.empleadoId AS idemp, em.nombre AS nomemp, em.depto, em.puesto\n"
                + "FROM percepciones per \n"
                + "INNER JOIN empleados em on per.empleadoId=em.empleadoId\n"
                + "INNER JOIN nomPercepciones nper on per.idNomPer=nper.idNomPer\n"
                + "where per.empleadoId='"+empleId+"'";
      
            conn1 = Conexion.getConnection();
            stmt1 = conn1.prepareStatement(sql);
            rs1 = stmt1.executeQuery();
           
               
           
                while (rs1.next()) {
                    tableContent.add(row = new ArrayList<String>());
                    row.add(rs1.getString("idemp"));
                    row.add(rs1.getString("nomemp"));
                    row.add(rs1.getString("depto"));
                    row.add(rs1.getString("puesto"));
                }
             }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (userConn == null) {
                Conexion.close(conn);
            }
        }
        return tableContent;
    }
}
