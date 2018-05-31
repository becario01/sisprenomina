/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import com.sun.awt.AWTUtilities;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Becarios
 */
public class reptxtran extends javax.swing.JFrame {

    public static ResultSet rs;
    private Connection userConn;
    private PreparedStatement st;
    Conexion con = new Conexion();
    Connection conn;
    PreparedStatement stmt;
    int x,y;

    public reptxtran() {

        initComponents();
        this.setResizable(false);
         this.setLocation(505,420);
        this.getContentPane().setBackground(new java.awt.Color(240,240,240));
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 30, 30);
        AWTUtilities.setWindowShape(this, forma);
        
    }
//clase para generar el reporte en txt dependiendo del intervalo de fechas
    public void intervalo(String fechainicio,String fechafin) throws SQLException {
      //abrir ventana para guardar el archivo 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos *.txt", "txt", "TXT"));//filtro para ver solo archivos .edu
        int seleccion = fileChooser.showSaveDialog(null);
        try {
            if (seleccion == JFileChooser.APPROVE_OPTION) {//comprueba si ha presionado el boton de aceptar
                File JFC = fileChooser.getSelectedFile();
                String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                PrintWriter printwriter = new PrintWriter(JFC);
                BufferedWriter bw = new BufferedWriter(printwriter);
                PreparedStatement nstmt = null;
                ResultSet interno = null;
               // obtener el id y nombre del empleado
                String sql = "SELECT DISTINCT  empleadoId  from incidencias";
                conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String idempleado = rs.getString("empleadoId");
                     bw.write("E\t" + idempleado);
                        bw.newLine();
                        //obtener incidencias 
                    String incidencias = " select DISTINCT nomi.nombre,inc.fecha,inc.horasExtra from incidencias inc  inner join NomIncidencia nomi   on inc.idNomIncidencia  = nomi.idNomIncidencia where  inc.fecha  BETWEEN  '"+fechainicio+"' and '"+fechafin+"' and  inc.empleadoId ='" + idempleado + "' ";
                    conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                    stmt = conn.prepareStatement(incidencias);
                    interno = stmt.executeQuery();
                    String nomcidencia = "";
                    while (interno.next()) {
                        nomcidencia = interno.getString("nombre");
                        String Fechainc = interno.getString("fecha");
                        int horasext = interno.getInt("horasExtra");
                        String[] datos = Fechainc.split("-");
                        String año = datos[0];
                        String mes = datos[1];
                        String dia = datos[2];
                        String fecha = dia + "/" + mes + "/" + año;
                        String incidencia = "D " + nomcidencia;
                        Calendar cal = Calendar.getInstance();
                        int añoact = cal.get(Calendar.YEAR);
                      //validar enumero de digitos  y si no cumple rellenar con espacios en blanco 
                        if (incidencia.length() < 40) {
                            for (int i = incidencia.length(); i < 40; i++) {
                                incidencia += " ";
                            }
                        }
                        bw.write(incidencia);
                        bw.write(horasext+ " " + fecha + "\t" + añoact);
                        bw.newLine();
                    }
                }
                 bw.close();//cierra el archivo
                    if (!(PATH.endsWith(".txt"))) {
                        File temp = new File(PATH + ".txt");
                        JFC.renameTo(temp);//renombramos el archivo
                    }

                    JOptionPane.showMessageDialog(null, "Guardado exitoso!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
                    this.hide();
            }
        } catch (Exception e) {//por alguna excepcion salta un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
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

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btntxtreporte = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 160, -1));
        getContentPane().add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 160, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("Fecha Inicio");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Fecha Fin");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Rango a reportar ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, 25));

        btntxtreporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/reportetxtL.png"))); // NOI18N
        btntxtreporte.setContentAreaFilled(false);
        btntxtreporte.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/reportetxtO.png"))); // NOI18N
        btntxtreporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntxtreporteActionPerformed(evt);
            }
        });
        getContentPane().add(btntxtreporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 73, -1));

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
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 32, 30));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 32, 30));

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
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btntxtreporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntxtreporteActionPerformed
  //enviar datos a clase para generrar el reporte txt 
        try {  if (jDateChooser2.getDate() == null && jDateChooser1.getDate() == null) {//devuelve verdadero si es ese mismo el botón que se ha pulsado
                JOptionPane.showMessageDialog(null, "Ambos campos estas vacios");
            } else if (jDateChooser1.getDate() == null) {
                JOptionPane.showMessageDialog(null, "El campo Fecha fin esta vacio");
            } else if (jDateChooser2.getDate() == null) {
                JOptionPane.showMessageDialog(null, "El campo Fecha inicio esta vacio");
            } else {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                //fecha inicio
                String formato = jDateChooser1.getDateFormatString();
                Date dates = jDateChooser1.getDate();
                String fechainicio = String.valueOf(sdf.format(dates));
                //fecha fin
                String formatofin = jDateChooser2.getDateFormatString();
                Date datefin = jDateChooser2.getDate();
                String fechafin = String.valueOf(sdf.format(datefin));
            System.out.println(fechainicio + fechafin);
            if (datefin.before(dates)) {
                    String resultado = "La Fecha Inicio no puede ser  Mayor ";
                    JOptionPane.showMessageDialog(rootPane, resultado);
                } else {
                 intervalo(fechainicio,fechafin);
                }
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(reptxtran.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btntxtreporteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
//regresar al ventana anterior 
        try {
            RH_UsuariosConIncidencias rsh = new RH_UsuariosConIncidencias();
           RH_UsuariosConIncidencias.TstVentNvoPres=false;
            this.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(reptxtran.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel7MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel7MouseDragged

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel7MousePressed

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
            java.util.logging.Logger.getLogger(reptxtran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(reptxtran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(reptxtran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(reptxtran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new reptxtran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btntxtreporte;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
