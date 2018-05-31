package BD;

import Conexion.Conexion;
import Controller.Eempleados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Addusuarios {

    private Connection userConn;

    private final String SQL_INSERT = "INSERT INTO usuario(nombre,usuario,pass,TipoUsuario,estatus,depto,fecharegistro) VALUES(?,?,?,?,?,?,?)";
    private final String sql_select = "SELECT * FROM empleados WHERE estatus = 1";

    public Addusuarios() {
    }

    public Addusuarios(Connection conn) {
        this.userConn = conn;
    }

    public int insert(String nombre, String usuario, String pass, int tipo, int estatus, String depto, String fecha) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet rs = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt1 = conn.prepareStatement("SELECT * FROM usuario where nombre='"+nombre+"'");
              rs = stmt1.executeQuery();
            if (!rs.next()) {
                stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setString(index++, nombre);
            stmt.setString(index++, usuario);
            stmt.setString(index++, pass);
            stmt.setInt(index++, tipo);
            stmt.setInt(index++, estatus);
            stmt.setString(index++,depto);
            stmt.setString(index++,fecha);

            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
            JOptionPane.showMessageDialog(null, "Usuario  registrado");
            }else{
                JOptionPane.showMessageDialog(null,"La persona ya cuenta con usuario");
            }
            
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }

    public ArrayList<Eempleados> obtenerEmpleados() {
        ArrayList<Eempleados> listaempleados = new ArrayList<Eempleados>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql_select);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("empleadoId");
                String nombre = rs.getString("nombre");
                int estatus = rs.getInt("estatus");
                String puesto = rs.getString("puesto");
                String depto = rs.getString("depto");

                Eempleados emp = new Eempleados(id, nombre, estatus, puesto, depto);
                listaempleados.add(emp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return listaempleados;
    }

}
