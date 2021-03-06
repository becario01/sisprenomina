/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Controller.Render;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Becarios
 */
public class destallesJA extends javax.swing.JFrame {

    DefaultTableModel modelodetalles = new DefaultTableModel(null, getColumas());
    int x, y;
    public static ResultSet rs;
    private Connection userConn;
    private PreparedStatement st;
    Conexion con = new Conexion();
    Connection conn;
    PreparedStatement stmt;
    private TableRowSorter trsFiltro;

    public destallesJA() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(238, 240, 245));
        tbjdetallesjefes.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbjdetallesjefes.getColumnModel().getColumn(1).setPreferredWidth(350);
        tbjdetallesjefes.getColumnModel().getColumn(2).setPreferredWidth(200);

    }
//mostra nombre de columnas del modelo de la tabla 
    private String[] getColumas() {
        String columna[] = {"Dia", "Fecha", "Incidencia"};
        return columna;
    }
//ver datos en el modelo de la tabla  con el intervalo de fechas
    public void inrtevalofechas(String Fechainicio, String Fechafin) throws ParseException, SQLException {
        tbjdetallesjefes.setDefaultRenderer(Object.class, new Render());
        String sql = "SELECT  DATENAME(dw, inc.fecha)as diasemana, inc.*,nomin.nombre FROM incidencias inc  left JOIN NomIncidencia nomin on  inc.idNomIncidencia = nomin.idNomIncidencia WHERE inc.fecha  BETWEEN '" + Fechainicio + "' and '" + Fechafin + "'   and  inc.empleadoId ='" + txtidempleado.getText() + "' order by inc.fecha asc  ";
        conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        String incdi = "";
        String fecha = "";
        Object datos[] = new Object[3];
        while (rs.next()) {
            String nom = rs.getString("nombre");
            String fect = rs.getString("fecha");
            String diasemana = rs.getString("diasemana");
            datos[0] = diasemana;
            datos[1] = fect;
            datos[2] = nom;
            modelodetalles.addRow(datos);
            centrar_datos();
        }
    }
//clase para convertir la primera letra en mayuscula
    public static String mayus(String str) {
        if (str.isEmpty()) {
            return str;
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
    }
    //clase para centrar datos de la tabla 
    public void centrar_datos() {

        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer alindere = new DefaultTableCellRenderer();
        alindere.setHorizontalAlignment(SwingConstants.LEFT);
        tbjdetallesjefes.getColumnModel().getColumn(2).setCellRenderer(alindere);
        tbjdetallesjefes.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
    }

    //clase de filtro de busquueda
    public void filtroBusqueda(JTextField txt) {
        trsFiltro.setRowFilter(RowFilter.regexFilter(txt.getText()));
    }//fin filtro 
//liampiar modelo de la tabla 
     public void limpiar(DefaultTableModel tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtidempleado = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbjdetallesjefes = new rojerusan.RSTableMetro();
        jButton1 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtinicio = new javax.swing.JTextField();
        txtfin = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtidempleado.setEditable(false);
        txtidempleado.setBackground(new java.awt.Color(238, 240, 245));
        txtidempleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtidempleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 260, 20));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("ID empleado :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 100, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Nombre:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtnombre.setEditable(false);
        txtnombre.setBackground(new java.awt.Color(238, 240, 245));
        txtnombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 260, 20));

        jPanel1.setBackground(new java.awt.Color(138, 229, 239));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/minimizar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 32, 30));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, 32, 30));

        jLabel7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel7MouseDragged(evt);
            }
        });
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 52));

        tbjdetallesjefes= new rojerusan.RSTableMetro(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tbjdetallesjefes.setModel(modelodetalles);
        tbjdetallesjefes.setAltoHead(20);
        tbjdetallesjefes.setColorBackgoundHead(new java.awt.Color(242, 242, 242));
        tbjdetallesjefes.setColorBordeFilas(new java.awt.Color(242, 242, 242));
        tbjdetallesjefes.setColorBordeHead(new java.awt.Color(204, 204, 204));
        tbjdetallesjefes.setColorFilasBackgound1(new java.awt.Color(219, 222, 227));
        tbjdetallesjefes.setColorFilasForeground1(new java.awt.Color(153, 153, 153));
        tbjdetallesjefes.setColorFilasForeground2(new java.awt.Color(153, 153, 153));
        tbjdetallesjefes.setColorForegroundHead(new java.awt.Color(0, 0, 0));
        tbjdetallesjefes.setColorSelBackgound(new java.awt.Color(0, 149, 142));
        tbjdetallesjefes.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        tbjdetallesjefes.setFuenteFilas(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbjdetallesjefes.setFuenteFilasSelect(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tbjdetallesjefes.setFuenteHead(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tbjdetallesjefes.setGridColor(new java.awt.Color(204, 204, 204));
        tbjdetallesjefes.setSelectionBackground(new java.awt.Color(0, 149, 142));
        jScrollPane1.setViewportView(tbjdetallesjefes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 700, 170));

        jButton1.setBackground(new java.awt.Color(153, 204, 255));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, -1, -1));

        txtBuscar.setBackground(new java.awt.Color(238, 240, 245));
        txtBuscar.setToolTipText("");
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 200, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/search1.png"))); // NOI18N
        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 40, 40));

        txtinicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtinicioMouseClicked(evt);
            }
        });
        getContentPane().add(txtinicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 180, 25));

        txtfin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtfinMouseClicked(evt);
            }
        });
        getContentPane().add(txtfin, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 180, 25));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Fecha Inicio :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Fecha Fin :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel7MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseDragged
     //mover fventana 
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel7MouseDragged

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
       //variables para al ser presionada se pueda mover 
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel7MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//btn para buscar los datos 
        try {
    limpiar(modelodetalles);
    
        if (txtinicio == null && txtfin == null) {//devuelve verdadero si es ese mismo el botón que se ha pulsado
            JOptionPane.showMessageDialog(null, "Ambos campos estas vacios");
        } else if (txtfin == null) {
            JOptionPane.showMessageDialog(null, "El campo Fecha fin esta vacio");
        } else if (txtinicio == null) {
            JOptionPane.showMessageDialog(null, "El campo Fecha inicio esta vacio");
        } else {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                String finici = txtinicio.getText();
                String ffin = txtfin.getText();
                //enviar fecha incio y fin para ver los datos 
                inrtevalofechas(finici,ffin); 
        }
        } catch (ParseException ex) {
                Logger.getLogger(destallesJA.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(destallesJA.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
       //filtro de busqueda 
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = txtBuscar.getText();
                txtBuscar.setText(cadena);
                repaint();
                filtroBusqueda(txtBuscar);
            }
        });
        trsFiltro = new TableRowSorter(tbjdetallesjefes.getModel());
        tbjdetallesjefes.setRowSorter(trsFiltro);        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtinicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtinicioMouseClicked
 //mostra calendario para seleccionar la fecha al dar click
        try {
            JA_Calendario cale = new JA_Calendario(8);
            cale.show();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtinicioMouseClicked

    private void txtfinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtfinMouseClicked
 //mostra calendario para fecha  al dar click
        try {
            JA_Calendario cale = new JA_Calendario(9);
            cale.show();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtfinMouseClicked

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
            java.util.logging.Logger.getLogger(destallesJA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(destallesJA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(destallesJA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(destallesJA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new destallesJA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSTableMetro tbjdetallesjefes;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtfin;
    public static javax.swing.JTextField txtidempleado;
    public static javax.swing.JTextField txtinicio;
    public static javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}
