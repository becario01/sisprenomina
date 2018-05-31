/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Programacion 04
 */

import Conexion.Conexion;
import View.RH_SEMANA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class EstatusSemanas {

    private Connection userConn;
    public static ResultSet rs;
    Connection conn = null;
    Conexion con = new Conexion();
    PreparedStatement stmt = null;

    public void desactivar(String semana) throws SQLException {
        String sql = "UPDATE semanas SET estatus=0 WHERE  semana='" + semana + "'";
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

    public void activar(String semana) throws SQLException {
        String sql = "UPDATE semanas SET estatus=1 WHERE  semana='" + semana + "'";
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

    public void agregar(String dias[],String semana) {
        try {
            String estatus="1";
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            String sql = "INSERT INTO semanas(semana, fechaL, fechaMa, fechaMi, fechaJ, fechaV, fechaS, fechaD, estatus) values (?,?,?,?,?,?,?,?,?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, semana);
            stmt.setString(2, dias[0]);
            stmt.setString(3, dias[1]);
            stmt.setString(4, dias[2]);
            stmt.setString(5, dias[3]);
            stmt.setString(6, dias[4]);
            stmt.setString(7, dias[5]);
            stmt.setString(8, dias[6]);
            stmt.setString(9, estatus);
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Registro Exitoso!");

        } catch ( SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en:  " + e, "ERROR", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexion.close(stmt);

            if (this.userConn == null) {
                Conexion.close(conn);
            }

        }
    }

}
