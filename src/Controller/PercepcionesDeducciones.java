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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

/**
 *
 * @author Programacion 04
 */
public class PercepcionesDeducciones {

    private Connection userConn;
    private Connection userConn1;
    private Connection userConn2;
    private Connection userConn3;
    private Connection userConn4;
    private Connection userConn5;
    private Connection userConn6;
    public static Vector<String> arrayidR = new Vector<>();
    public static Vector<String> arrayfeR = new Vector<>();
    public static Vector<String> arrayfechas = new Vector<>();
    String percep;
//se realiza la asignacion de las percepciones a los empleados 

    public void insertar(String empleadoid, String fecha, int idper, String coment, JRootPane rootPane) throws SQLException {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        percep = String.valueOf(idper);
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            String sql = "INSERT INTO percepciones( empleadoId, fecha, idNomPer, dia, comentario, Semana) values (?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, empleadoid);
            stmt.setString(2, fecha);
            stmt.setString(3, percep);
            stmt.setString(4, dia(fecha));
            stmt.setString(5, coment);
            stmt.setString(6, nomsenanas(fecha));
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Registro Exitoso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en:  " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(PercepcionesDeducciones.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
    }
    //se realiza el registro de las nuevas percepciones 
    public void newPercep(String nombre, String dias) throws SQLException {
        String estatus = "1";
        ResultSet rs3;
        Connection conn3 = null;
        PreparedStatement stmt3 = null;
        try {
            conn3 = (this.userConn3 != null) ? this.userConn3 : Conexion.getConnection();
            String sql = "INSERT INTO nomPercepciones(nombre, estatus, dias) values (?,?,?)";

            stmt3 = conn3.prepareStatement(sql);
            stmt3.setString(1, nombre);
            stmt3.setString(2, estatus);
            stmt3.setString(3, dias);

            stmt3.execute();
            JOptionPane.showMessageDialog(null, "Registro Exitoso!");

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en:  " + e, "ERROR", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexion.close(stmt3);

            if (this.userConn3 == null) {
                Conexion.close(conn3);
            }

        }

    }
//se desactivan las percepciones creadas 
    public void desactivar(String semana) throws SQLException {
        String sql = "UPDATE nomPercepciones SET estatus=0 WHERE  idNomPer='" + semana + "'";
        ResultSet rs4;
        Connection conn4 = null;
        PreparedStatement stmt4 = null;
        int rows = 0;
        try {
            conn4 = (this.userConn4 != null) ? this.userConn4 : Conexion.getConnection();

            stmt4 = conn4.prepareStatement(sql);

            rows = stmt4.executeUpdate();

        } finally {
            Conexion.close(stmt4);
            if (this.userConn4 == null) {
                Conexion.close(conn4);
            }
        }
    }
// se activan las percepciones creadas 
    public void activar(String semana) throws SQLException {
        String sql = "UPDATE nomPercepciones SET estatus=1 WHERE  idNomPer='" + semana + "'";
        ResultSet rs5;
        Connection conn5 = null;
        PreparedStatement stmt5 = null;
        int rows = 0;
        try {
            conn5 = (this.userConn5 != null) ? this.userConn5 : Conexion.getConnection();

            stmt5 = conn5.prepareStatement(sql);

            rows = stmt5.executeUpdate();

        } finally {
            Conexion.close(stmt5);
            if (this.userConn5 == null) {
                Conexion.close(conn5);
            }
        }
    }
//se obtiene el nombre de la semana dependiendo la fecha de creacion 
    public String nomsenanas(String fecha) throws ParseException, SQLException {

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        java.util.Date date = d.parse(fecha);
        calendar.setTime(date);
        int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        String numbstring = String.valueOf(numberWeekOfYear);
        String strinyear = String.valueOf(year);
        String sSubCadena = strinyear.substring(2, 4);
        String nomsemana = "SEMANA" + " " + numbstring + "_" + sSubCadena;

        return nomsemana;
    }
//se obtiene el dia de la percepcion dependiendo la fecha 
    public String dia(String fec) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String nomdia = "";
        try {
            Date fecha = formato.parse(fec);
            cal.setTime(fecha);
            int dia = cal.get(Calendar.DAY_OF_WEEK);
            switch (dia) {
                case 1:
                    nomdia = "DOMINGO";
                    break;
                case 2:
                    nomdia = "LUNES";
                    break;
                case 3:
                    nomdia = "MARTES";
                    break;
                case 4:
                    nomdia = "MIERCOLES";
                    break;
                case 5:
                    nomdia = "JUEVES";
                    break;
                case 6:
                    nomdia = "VIERNES";
                    break;
                case 7:
                    nomdia = "SABADO";
                    break;
                default:
                    break;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Errorn en: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return nomdia;
    }
}
