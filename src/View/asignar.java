/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Controller.Render;
import static Controller.exportReporte.conn;
import static Controller.exportReporte.stmt;
import static View.Asignacion.rs;
import static View.RH_registrarusrs.rs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Becarios
 */
public class asignar extends javax.swing.JFrame {

    public static ResultSet rs;
    private Connection userConn;
    private TableRowSorter trsFiltro;
    private PreparedStatement st;
    DefaultTableModel usrasignados = new DefaultTableModel(null, getColumas());
    DefaultTableModel modelonoasiganados = new DefaultTableModel(null, getColumas());

    Conexion con = new Conexion();
    Connection conn;
    PreparedStatement stmt;

    public asignar() {
        try {
            initComponents();
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
            setFilasnoasig();
            lblnombrerh.setHorizontalAlignment(lblnombrerh.CENTER);
            lblcargo.setHorizontalAlignment(lblcargo.CENTER);
        } catch (SQLException ex) {
            Logger.getLogger(asignar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] getColumas() {
        String columna[] = {"EmpleadoID", "Nombre"};
        return columna;
    }
//clase para mostrar los empleados que estan asignados
    public void SetFilas(String id) {

        try {
            String sql = "Select DISTINCT emp.empleadoId, emp.nombre from empleados emp INNER JOIN asignacion asg on  emp.empleadoId = asg.empleadoId\n"
                    + "where emp.estatus = 1 and asg.idUser='" + id + "'";
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            Object datos[] = new Object[4];
            while (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    datos[0] = rs.getString("empleadoId");
                    datos[1] = rs.getString("nombre");

                }
                usrasignados.addRow(datos);
            }
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }

    }//fin de clase asignados
    
///clase para mostrar listado de empleados no asignados 
    public void setFilasnoasig() throws SQLException {
        String sql = "SELECT  emp.empleadoId, emp.nombre FROM empleados emp \n"
                + "LEFT JOIN asignacion asg ON emp.empleadoId = asg.empleadoId \n"
                + "WHERE  asg.empleadoId  IS NULL  AND estatus = 1";
        conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        Object datos[] = new Object[3];
        while (rs.next()) {
            for (int i = 0; i < 3; i++) {
                datos[0] = rs.getString("empleadoId");
                datos[1] = rs.getString("nombre");

            }
            modelonoasiganados.addRow(datos);
        }
    }//fin clase no asignados
//clase para inserta los empleados a usuario jefe
    public int insert(String empleadoid, String iduser) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement("INSERT INTO asignacion(empleadoId, idUser) VALUES('" + empleadoid + "','" + iduser + "')");
            int index = 1;
            System.out.println("Ejecutando query:");
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);

        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }//fin de clase para insertar empleados
    
//clase para eliminar empleados asignados
    public int delete(String empleadoid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement("DELETE FROM asignacion WHERE empleadoId = '" + empleadoid + "' ");
            int index = 1;
            System.out.println("Ejecutando query:");
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);

        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }//fin para eliminar  empleados

    //limpiar tabla del modelo 
    public void limpiar(DefaultTableModel tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }

    }//fin  limpiar  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblasignados = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblnoasignados = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblnombrerh = new javax.swing.JLabel();
        lblcargo = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtnomasg = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        idusers = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(340, 350));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblasignados= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tblasignados.setModel(usrasignados);
        jScrollPane1.setViewportView(tblasignados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 340, 190));

        tblnoasignados= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tblnoasignados.setModel(modelonoasiganados);
        jScrollPane2.setViewportView(tblnoasignados);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 370, 190));

        jPanel2.setBackground(new java.awt.Color(229, 230, 234));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/portafolio.png"))); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/user.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 40));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/minimizar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 0, 32, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 32, 30));

        lblnombrerh.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblnombrerh.setForeground(new java.awt.Color(51, 102, 255));
        lblnombrerh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 255)));
        jPanel2.add(lblnombrerh, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 230, 20));

        lblcargo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblcargo.setForeground(new java.awt.Color(51, 102, 255));
        lblcargo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 255)));
        jPanel2.add(lblcargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 230, 20));
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre: ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        txtnomasg.setBackground(new java.awt.Color(51, 102, 255));
        txtnomasg.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtnomasg.setForeground(new java.awt.Color(255, 255, 255));
        txtnomasg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(229, 230, 224)));
        getContentPane().add(txtnomasg, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 270, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/right-arrow.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/right-arrow (1).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 90, 80));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID user:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 90, 20));

        idusers.setBackground(new java.awt.Color(51, 102, 255));
        idusers.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        idusers.setForeground(new java.awt.Color(255, 255, 255));
        idusers.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(229, 230, 234)));
        getContentPane().add(idusers, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 200, 30));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/left-arrow.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/left-arrow (1).png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, 90, 80));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int fila = tblasignados.getSelectedRow();
        if (fila >= 0) {
            int cuentaFilasSeleccionadas = tblasignados.getSelectedRowCount();
            if (cuentaFilasSeleccionadas == 1) {
                try {
                    int indiceFilaSeleccionada = tblasignados.getSelectedRow();
                    String usrid = tblasignados.getValueAt(indiceFilaSeleccionada, 0).toString();
                    String nombre = tblasignados.getValueAt(indiceFilaSeleccionada, 1).toString();
                    delete(usrid);
                    limpiar(modelonoasiganados);
                    limpiar(usrasignados);
                    setFilasnoasig();
                    SetFilas(idusers.getText());
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                } catch (SQLException ex) {
                    Logger.getLogger(asignar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                int[] indicesfilasSeleccionadas = tblasignados.getSelectedRows();
                try {
                    for (int indice : indicesfilasSeleccionadas) {
                        System.out.print(indice + " ");
                        String usrid = tblasignados.getValueAt(indice, 0).toString();
                        String nombre = tblasignados.getValueAt(indice, 1).toString();
                        delete(usrid);
                    }
                    limpiar(modelonoasiganados);
                    limpiar(usrasignados);
                    setFilasnoasig();
                    SetFilas(idusers.getText());
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                } catch (SQLException ex) {
                    Logger.getLogger(asignar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Seleccione uns fila !!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
///seleccion individual  de mepleados para  asignar
        int fila = tblnoasignados.getSelectedRow();
        if (fila >= 0) {
            int cuentaFilasSeleccionadas = tblnoasignados.getSelectedRowCount();
            if (cuentaFilasSeleccionadas == 1) {
                try {
                    int indiceFilaSeleccionada = tblnoasignados.getSelectedRow();
                    String usrid = tblnoasignados.getValueAt(indiceFilaSeleccionada, 0).toString();
                    String nombre = tblnoasignados.getValueAt(indiceFilaSeleccionada, 1).toString();
                    insert(usrid, idusers.getText());
                    limpiar(modelonoasiganados);
                    limpiar(usrasignados);
                    setFilasnoasig();
                    SetFilas(idusers.getText());
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                } catch (SQLException ex) {
                    Logger.getLogger(asignar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                ///grupo de usuarios seleccionados para asignar 
                int[] indicesfilasSeleccionadas = tblnoasignados.getSelectedRows();
                try {
                    for (int indice : indicesfilasSeleccionadas) {
                        System.out.print(indice + " ");
                        String usrid = tblnoasignados.getValueAt(indice, 0).toString();
                        String nombre = tblnoasignados.getValueAt(indice, 1).toString();
                        insert(usrid, idusers.getText());
                    }
                    limpiar(modelonoasiganados);
                    limpiar(usrasignados);
                    setFilasnoasig();
                    SetFilas(idusers.getText());
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                } catch (SQLException ex) {
                    Logger.getLogger(asignar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Seleccione uns fila !!");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
///ocultar ventana para regresar 
        this.hide();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(asignar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(asignar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(asignar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(asignar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new asignar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField idusers;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lblcargo;
    public static javax.swing.JLabel lblnombrerh;
    private javax.swing.JTable tblasignados;
    private javax.swing.JTable tblnoasignados;
    public static javax.swing.JTextField txtnomasg;
    // End of variables declaration//GEN-END:variables

}
