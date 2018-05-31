/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Controller.EJefes;

import Controller.controllerBD;
import Data.UsuariosAcIn;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Programacion 2
 */
public class RH_ListadoPersonal extends javax.swing.JFrame {

    public static controllerBD controller;
    public static ResultSet rs;
    private Connection userConn;
    private TableRowSorter trsFiltro;
    int x, y;

    private PreparedStatement st;

    Connection conn;
    PreparedStatement stmt;

    /**
     * Creates new form RH_ListadoPersonal
     */
    public RH_ListadoPersonal() throws SQLException {
        
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        cargarTitulos1();
        cargarTitulos2();
        tbListadoAct.setDefaultRenderer(Object.class, new EJefes());
        tbListadoIna.setDefaultRenderer(Object.class, new EJefes());
        lblnombrerh.setHorizontalAlignment(lblnombrerh.CENTER);
        lblnombrerh.setVerticalAlignment(lblnombrerh.CENTER);
        lblcargo.setHorizontalAlignment(lblcargo.CENTER);
        lblcargo.setVerticalAlignment(lblcargo.CENTER);
    }
//creacion de modelos para las tablas 
    DefaultTableModel tabla1 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int Fila, int Colum) {
            return false;
        }
    };
    DefaultTableModel tabla2 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int Fila, int Colum) {
            return false;
        }
    };
//titulos para tabla 1
    public void cargarTitulos1() throws SQLException {
        tabla1.addColumn("ESTATUS");
        tabla1.addColumn("ID EMPLEADO");
        tabla1.addColumn("NOMBRE");
        tabla1.addColumn("DEPARTAMENTO");
        tabla1.addColumn("PUESTO");
        this.tbListadoAct.setModel(tabla1);

        TableColumnModel columnModel = tbListadoAct.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(55);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);

        cargardatos1();

    }
//datos para tabla 1 
    public void cargardatos1() throws SQLException {
        String sql = "select empleadoId, nombre, depto, puesto from empleados where  estatus=1 ";
        Object datos[] = new Object[5];

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                datos[0] = new JLabel(new ImageIcon(getClass().getResource("/View/img/actulizadoj.png")));
                datos[1] = rs.getString("empleadoId");
                datos[2] = rs.getString("nombre");
                datos[3] = rs.getString("depto");
                datos[4] = rs.getString("puesto");
                tabla1.addRow(datos);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

    }
//datos para tabla 2 
    public void cargardatos2() throws SQLException {
        String sql = "select empleadoId, nombre, depto, puesto from empleados where estatus=0 ";
        Object datos[] = new Object[5];

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                datos[0] = new JLabel(new ImageIcon(getClass().getResource("/View/img/noactualizadoj.png")));
                datos[1] = rs.getString("empleadoId");
                datos[2] = rs.getString("nombre");
                datos[3] = rs.getString("depto");
                datos[4] = rs.getString("puesto");
                tabla2.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

    }
//limpia la tabla 
    public void limpiar(DefaultTableModel tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }

    }
//filtros de busqueda 
    public void filtro(JTextField txt) {
        trsFiltro.setRowFilter(RowFilter.regexFilter(txt.getText()));
    }
//titulos para tabla 2
    public void cargarTitulos2() throws SQLException {
        tabla2.addColumn("ESTATUS");
        tabla2.addColumn("ID EMPLEADO");
        tabla2.addColumn("NOMBRE");
        tabla2.addColumn("DEPARTAMENTO");
        tabla2.addColumn("PUESTO");
        this.tbListadoIna.setModel(tabla2);
        TableColumnModel columnModel = tbListadoIna.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(55);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);
        cargardatos2();
    }

//       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmActivar = new javax.swing.JPopupMenu();
        miActivar = new javax.swing.JMenuItem();
        pmDesactivar = new javax.swing.JPopupMenu();
        miDesactivar = new javax.swing.JMenuItem();
        txtBuscar1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListadoIna = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnmini = new javax.swing.JButton();
        btncerrar = new javax.swing.JButton();
        btnregresar = new javax.swing.JButton();
        lblnombrerh = new javax.swing.JLabel();
        lblcargo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListadoAct = new javax.swing.JTable();

        miActivar.setText("Activar");
        miActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miActivarActionPerformed(evt);
            }
        });
        pmActivar.add(miActivar);

        miDesactivar.setText("Desactivar");
        miDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDesactivarActionPerformed(evt);
            }
        });
        pmDesactivar.add(miDesactivar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar1.setBackground(new java.awt.Color(51, 102, 255));
        txtBuscar1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtBuscar1.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscar1ActionPerformed(evt);
            }
        });
        txtBuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscar1KeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 465, 187, 35));

        tbListadoIna= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tbListadoIna.setAutoCreateRowSorter(true);
        tbListadoIna.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbListadoIna.setForeground(new java.awt.Color(51, 51, 51));
        tbListadoIna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbListadoIna.setComponentPopupMenu(pmActivar);
        tbListadoIna.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        tbListadoIna.setFillsViewportHeight(true);
        tbListadoIna.setGridColor(new java.awt.Color(255, 255, 255));
        tbListadoIna.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tbListadoIna.setSelectionBackground(new java.awt.Color(108, 180, 221));
        tbListadoIna.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tbListadoIna);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 561, 912, 180));

        jPanel1.setBackground(new java.awt.Color(229, 230, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/portafolio.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/user.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        btnmini.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/minimizar.png"))); // NOI18N
        btnmini.setBorderPainted(false);
        btnmini.setContentAreaFilled(false);
        btnmini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnminiActionPerformed(evt);
            }
        });
        jPanel1.add(btnmini, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 32, 30));

        btncerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/error.png"))); // NOI18N
        btncerrar.setBorderPainted(false);
        btncerrar.setContentAreaFilled(false);
        btncerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btncerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 32, 30));

        btnregresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        btnregresar.setBorderPainted(false);
        btnregresar.setContentAreaFilled(false);
        btnregresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnregresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 0, 32, 30));

        lblnombrerh.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblnombrerh.setForeground(new java.awt.Color(51, 102, 255));
        lblnombrerh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel1.add(lblnombrerh, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 320, 20));

        lblcargo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblcargo.setForeground(new java.awt.Color(51, 102, 255));
        lblcargo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel1.add(lblcargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 330, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 52));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/search1.png"))); // NOI18N
        jLabel6.setToolTipText("");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, 40, 40));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Listado personal inactivo");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 424, 227, 30));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Listado personal activo");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 83, 227, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/search1.png"))); // NOI18N
        jLabel5.setToolTipText("");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 40, 40));

        txtBuscar.setBackground(new java.awt.Color(51, 102, 255));
        txtBuscar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 140, 187, 35));

        tbListadoAct= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tbListadoAct.setAutoCreateRowSorter(true);
        tbListadoAct.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbListadoAct.setForeground(new java.awt.Color(51, 51, 51));
        tbListadoAct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbListadoAct.setComponentPopupMenu(pmDesactivar);
        tbListadoAct.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        tbListadoAct.setFillsViewportHeight(true);
        tbListadoAct.setGridColor(new java.awt.Color(255, 255, 255));
        tbListadoAct.setInheritsPopupMenu(true);
        tbListadoAct.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tbListadoAct.setSelectionBackground(new java.awt.Color(108, 180, 221));
        tbListadoAct.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tbListadoAct);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 912, 180));

        pack();
    }// </editor-fold>//GEN-END:initComponents

      private void miDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDesactivarActionPerformed
        //desactiva empleados y vuelve a cargar la tabla actualizada 
          int fila = tbListadoAct.getSelectedRow();
          if (fila >= 0) {
              String cod = tbListadoAct.getValueAt(fila, 1).toString();
              String nom = tbListadoAct.getValueAt(fila, 2).toString();

              controllerBD act = new controllerBD();

              try {
                  act.desactivar(cod, nom);

              } catch (SQLException ex) {
                  JOptionPane.showMessageDialog(null, "error" + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
              }

          } else {
              JOptionPane.showMessageDialog(null, "Selecione una fila ", "", JOptionPane.WARNING_MESSAGE);
          }
          limpiar(tabla1);
          limpiar(tabla2);

          try {
              cargardatos1();
              cargardatos2();
          } catch (SQLException ex) {
              Logger.getLogger(RH_ListadoPersonal.class.getName()).log(Level.SEVERE, null, ex);
          }

      }//GEN-LAST:event_miDesactivarActionPerformed

      private void miActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miActivarActionPerformed
//activa los empleados y actualiza la tabla 
          int fila = tbListadoIna.getSelectedRow();
          if (fila >= 0) {
              String cod = tbListadoIna.getValueAt(fila, 1).toString();
              String nom = tbListadoIna.getValueAt(fila, 2).toString();

              controllerBD act = new controllerBD();
              try {
                  act.activar(cod, nom);

              } catch (SQLException ex) {
                  JOptionPane.showMessageDialog(null, "error" + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
              }

          } else {
              JOptionPane.showMessageDialog(null, "Selecione una fila ", "", JOptionPane.WARNING_MESSAGE);
          }
          limpiar(tabla1);
          limpiar(tabla2);

          try {
              cargardatos2();
              cargardatos1();
          } catch (SQLException ex) {
              Logger.getLogger(RH_ListadoPersonal.class.getName()).log(Level.SEVERE, null, ex);
          }

      }//GEN-LAST:event_miActivarActionPerformed

      private void btnminiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnminiActionPerformed
         //minimiza la ventana 
          this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
      }//GEN-LAST:event_btnminiActionPerformed

      private void btncerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrarActionPerformed
        //cierra el sistema 
          System.exit(0);        // TODO add your handling code here:
      }//GEN-LAST:event_btncerrarActionPerformed

      private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
         //regresa a la anteriror ventana 
          String dep = lblcargo.getText();
          String nom = lblnombrerh.getText();

          RH_Inicio us = new RH_Inicio();
          us.show(true);
          RH_Inicio.lblcargo.setText(dep);
          RH_Inicio.lblnombrerh.setText(nom);
          this.show(false);
      }//GEN-LAST:event_btnregresarActionPerformed

      private void txtBuscar1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar1KeyTyped
        //busca el carracter ingresado en los campos de la tabla 2
          txtBuscar1.addKeyListener(new KeyAdapter() {
              public void keyReleased(final KeyEvent e) {
                  String cadena = (txtBuscar1.getText()).toUpperCase();
                  txtBuscar1.setText(cadena);
                  repaint();
                  filtro(txtBuscar1);
              }
          });
          trsFiltro = new TableRowSorter(tbListadoIna.getModel());
          tbListadoIna.setRowSorter(trsFiltro);
      }//GEN-LAST:event_txtBuscar1KeyTyped

      private void txtBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar1ActionPerformed
          // TODO add your handling code here:
      }//GEN-LAST:event_txtBuscar1ActionPerformed

      private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
 //busca el carracter ingresado en los campos de la tabla 1
          txtBuscar.addKeyListener(new KeyAdapter() {
              public void keyReleased(final KeyEvent e) {
                  String cadena = (txtBuscar.getText()).toUpperCase();
                  txtBuscar.setText(cadena);
                  repaint();
                  filtro(txtBuscar);
              }
          });
          trsFiltro = new TableRowSorter(tbListadoAct.getModel());
          tbListadoAct.setRowSorter(trsFiltro);
      }//GEN-LAST:event_txtBuscarKeyTyped

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
            java.util.logging.Logger.getLogger(RH_ListadoPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RH_ListadoPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RH_ListadoPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RH_ListadoPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RH_ListadoPersonal().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RH_ListadoPersonal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncerrar;
    private javax.swing.JButton btnmini;
    private javax.swing.JButton btnregresar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lblcargo;
    public static javax.swing.JLabel lblnombrerh;
    private javax.swing.JMenuItem miActivar;
    private javax.swing.JMenuItem miDesactivar;
    private javax.swing.JPopupMenu pmActivar;
    private javax.swing.JPopupMenu pmDesactivar;
    private javax.swing.JTable tbListadoAct;
    private javax.swing.JTable tbListadoIna;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscar1;
    // End of variables declaration//GEN-END:variables
}
