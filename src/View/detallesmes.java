/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Controller.EJefes;
import Controller.Render;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Becarios
 */
public class detallesmes extends javax.swing.JFrame {
    private TableRowSorter trsFiltro;  
    public static ResultSet rs;
    private Connection userConn;
    private PreparedStatement st;
    Conexion con = new Conexion();
    Connection conn;
    PreparedStatement stmt;
    DefaultTableModel modelodetallesmes = new DefaultTableModel(null, getColumas());
    int x , y ;
    select_incidencia slin;
    public detallesmes() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(238,240,245));
         slin = new select_incidencia();
    }


    
    private String[] getColumas() {
        String columna[] = {"Dia", "Fecha", "Incidencia"};
        return columna;
    }

    public void diasiniciales(int año, int mes, String iduser) throws SQLException {
       jtbdetallesmes.setDefaultRenderer(Object.class, new EJefes());
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet rs = null;
        int mescomple = mes - 1;
        jtbdetallesmes.setDefaultRenderer(Object.class, new Render());
        Calendar calendario = Calendar.getInstance();
        calendario.set(año, mescomple, 1);
        int ultimoDiaMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
        int primerdia = calendario.getActualMinimum(Calendar.DAY_OF_MONTH);
        //comparacions de dosfechas
        Calendar c1 = Calendar.getInstance();
        c1.set(año, mescomple, primerdia);
        Calendar c2 = Calendar.getInstance();
        c2.set(año, mescomple, ultimoDiaMes);

        //lista de fechas 
        java.util.List<Date> listaEntreFechas = this.getListaEntreFechas(c1.getTime(), c2.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE yyyy-MM-dd");
        //mostrar fechas y dias
        Object datos[] = new Object[3];

        for (Date date : listaEntreFechas) {
            String dat = mayus(sdf.format(date));
            String[] parts = dat.split(" ");
            String part1 = parts[0];
            String part2 = parts[1];
            String  vdatos = consulta(part2,iduser);
            datos[0] = part1;
            datos[1] = part2;
            datos[2] = vdatos;
            modelodetallesmes.addRow(datos);
        }
    }

    public java.util.List<Date> getListaEntreFechas(Date fechaInicio, Date fechaFin) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);
        java.util.List<Date> listaFechas = new java.util.ArrayList<Date>();
        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return listaFechas;
    }

    public static String mayus(String str) {
        if (str.isEmpty()) {
            return str;
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
    }

    
     public String consulta(String fechas ,String iduser) throws SQLException{
                String sql = "SELECT inc.*,nomin.nombre FROM incidencias inc  left JOIN NomIncidencia nomin on  inc.idNomIncidencia = nomin.idNomIncidencia WHERE inc.fecha ='" + fechas + "'  and  inc.empleadoId ='" + iduser + "' ";
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();  
          String incdi = "";
         while (rs.next()) {
                String nom = rs.getString("nombre");
               
                if (nom.equals("")) {
                    incdi = " ";
                } else {
                    incdi = nom;
                }

            }
        return incdi ;
     
     
     
     }
           public void filtroBusqueda(JTextField txt) {
            trsFiltro.setRowFilter(RowFilter.regexFilter(txt.getText()));
      }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        pninsert = new javax.swing.JMenuItem();
        lblmes = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtidempleado = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        pnelcontbl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbdetallesmes = new rojerusan.RSTableMetro();

        pninsert.setText("Insertar Incidencia");
        pninsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pninsertActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pninsert);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblmes.setBackground(new java.awt.Color(255, 255, 255));
        lblmes.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblmes.setText("Mes");
        getContentPane().add(lblmes, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 80, 30));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("ID empleado");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Nombre");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        txtidempleado.setEditable(false);
        txtidempleado.setBackground(new java.awt.Color(238, 240, 245));
        txtidempleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtidempleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 55, 309, 20));

        txtnombre.setEditable(false);
        txtnombre.setBackground(new java.awt.Color(238, 240, 245));
        txtnombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 95, 309, 20));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Mes :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 80, 30));

        txtBuscar.setBackground(new java.awt.Color(238, 240, 245));
        txtBuscar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 250, 20));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/search1.png"))); // NOI18N
        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 40, 40));

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
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 52));

        pnelcontbl.setBackground(new java.awt.Color(112, 112, 112));
        pnelcontbl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtbdetallesmes.setModel(modelodetallesmes);
        jtbdetallesmes.setAlignmentX(0.8F);
        jtbdetallesmes.setAltoHead(20);
        jtbdetallesmes.setColorBackgoundHead(new java.awt.Color(25, 200, 166));
        jtbdetallesmes.setColorBordeFilas(new java.awt.Color(204, 204, 204));
        jtbdetallesmes.setColorBordeHead(new java.awt.Color(204, 204, 204));
        jtbdetallesmes.setColorFilasForeground1(new java.awt.Color(153, 153, 153));
        jtbdetallesmes.setColorFilasForeground2(new java.awt.Color(153, 153, 153));
        jtbdetallesmes.setColorForegroundHead(new java.awt.Color(0, 0, 0));
        jtbdetallesmes.setColorSelBackgound(new java.awt.Color(0, 255, 255));
        jtbdetallesmes.setComponentPopupMenu(jPopupMenu1);
        jtbdetallesmes.setFuenteFilas(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtbdetallesmes.setFuenteFilasSelect(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtbdetallesmes.setFuenteHead(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jtbdetallesmes.setSelectionBackground(new java.awt.Color(0, 149, 142));
        jtbdetallesmes.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(jtbdetallesmes);

        pnelcontbl.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 220));

        getContentPane().add(pnelcontbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 650, 220));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = txtBuscar.getText();
                txtBuscar.setText(cadena);
                repaint();
                filtroBusqueda(txtBuscar);
            }
        });
        trsFiltro = new TableRowSorter(jtbdetallesmes.getModel());
        jtbdetallesmes.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel7MousePressed

    private void jLabel7MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel7MouseDragged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pninsertActionPerformed
         int cuentaFilasSeleccionadas = jtbdetallesmes.getSelectedRowCount();   
        if (cuentaFilasSeleccionadas == 1) {
            int fila = jtbdetallesmes.getSelectedRow();
            String empid = txtidempleado.getText();
            String nombre = txtnombre.getText();
            slin.mostrardatos(empid, nombre);
            slin.setVisible(true);
            this.setVisible(false);
        } else {
        }
        
    }//GEN-LAST:event_pninsertActionPerformed

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
            java.util.logging.Logger.getLogger(detallesmes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detallesmes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detallesmes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detallesmes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new detallesmes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSTableMetro jtbdetallesmes;
    public static javax.swing.JLabel lblmes;
    private javax.swing.JPanel pnelcontbl;
    private javax.swing.JMenuItem pninsert;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtidempleado;
    public static javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables

}
