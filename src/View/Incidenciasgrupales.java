/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import BD.Nomincidencia;
import BD.RegistrarIncidencia;
import Conexion.Conexion;
import Controller.EJefes;
import Controller.Rincidencia;
import static View.Incidenciasgrupales.jtbdatosgrupos;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Becarios
 */
public class Incidenciasgrupales extends javax.swing.JFrame {

    Nomincidencia rin;
    DefaultComboBoxModel<Rincidencia> modeloselincidencia;
    public static ResultSet rs;
    private Connection userConn;
    private PreparedStatement st;
    Conexion con = new Conexion();
    Connection conn;
    PreparedStatement stmt;
    int x, y;
    select_incidencia sle;
    public String tipof;
    /**
     * Creates new form Incidenciasgrupales
     */
    public Incidenciasgrupales() {
        modeloselincidencia = new DefaultComboBoxModel<Rincidencia>();
        rin = new Nomincidencia();
        sle = new select_incidencia();
        cargarModeloINC();
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(233, 236, 241));

    }
 private void cargarModeloINC() {
        ArrayList<Rincidencia> listaSemanas;
        listaSemanas = rin.obtenerIncnidecnias();
        for (Rincidencia semana : listaSemanas) {
            modeloselincidencia.addElement(semana);
        }
    }
   
    public int registrargrupos(int empleadoId, String dia, String fecha, String horasextra, String comentario, int idSemana, int idNomIncidencias, String horasTrab) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            String SQL_INSERT = "INSERT INTO incidencias (empleadoId ,dia,fecha ,horasExtra ,comentario ,idSemana ,idNomIncidencia,horasTrab,actualizadoJA) VALUES ('" + empleadoId + "','" + dia + "' ,'" + fecha + "','" + horasextra + "','" + comentario + "','" + idSemana + "','" + idNomIncidencias + "','" + horasTrab + "','AUTORIZADO')";
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
//calse para el rango de fechas 
    public String guardar(String Fechainicio, String Fechafin) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        select_incidencia sin = new select_incidencia();
        select_fechas selc = new select_fechas();
        String Finicio = Fechainicio;
        //separar fecha inicion y fin y convertir a int 
        String[] parts = Finicio.split("-");
        int añoi = Integer.parseInt(parts[0]);
        int mesi = Integer.parseInt(parts[1]);
        int diai = Integer.parseInt(parts[2]);

        String fecharango = "";
        String[] part = Fechafin.split("-");
        int añof = Integer.parseInt(part[0]);
        int mesf = Integer.parseInt(part[1]);
        int diaf = Integer.parseInt(part[2]);

        Calendar c1 = Calendar.getInstance();
        //enviar la variables de año,mes,dia
        c1.set(añoi, mesi - 1, diai);
        Calendar c2 = Calendar.getInstance();
        c2.set(añof, mesf - 1, diaf);
        String fecha = "";
        //lamamos la clase de rango de fechas
        java.util.List<java.util.Date> listaEntreFechas = getListaEntreFechas(c1.getTime(), c2.getTime());
            int result = 0;
            //lista de fechas
        for (Date date : listaEntreFechas) {
            fecha = sdf.format(date);
            int tabla = jtbdatosgrupos.getRowCount();
        //obtener todos lo usuarios de la tabla 
            for (int i = 0; i < tabla; i++) {
                try {
                    String codigos = jtbdatosgrupos.getValueAt(i, 0).toString();
                    int codigo = Integer.parseInt(codigos);
                    Rincidencia incidencia = (Rincidencia) cmbincidencia.getSelectedItem();
                    int idinc = incidencia.getIdNomIncidencia();
                    String comentario = txtcomentario.getText();
                    int idsemana = selc.numsenanas(fecha);
                    String indi = select_incidencia.obtenerDiaSemana(fecha);
                    String horastrab = "10";
                    String cantidad =" ";
                    //clase para registrar incidencias
                    result = registrargrupos(codigo, indi, fecha, cantidad, comentario, idsemana, idinc, horastrab);
                } catch (ParseException ex) {
                    Logger.getLogger(Incidenciasgrupales.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
         if (result > 0 ) {
            JOptionPane.showMessageDialog(null,"Registos exitosos");
        }
        return fecha;
    }
//clase para obtener el rango de fechas
    public java.util.List<java.util.Date> getListaEntreFechas(java.util.Date fechaInicio, java.util.Date fechaFin) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);
        java.util.List<java.util.Date> listaFechas = new java.util.ArrayList<java.util.Date>();
        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return listaFechas;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jtbdatosgrupos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cmbincidencia = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtcomentario = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(512, 100));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtbdatosgrupos= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        jtbdatosgrupos.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jtbdatosgrupos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmpleadoId", "Nombre"
            }
        ));
        jtbdatosgrupos.setEnabled(false);
        jtbdatosgrupos.setOpaque(false);
        jScrollPane2.setViewportView(jtbdatosgrupos);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 300, 134));

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
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 32, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 32, 30));

        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel3MouseDragged(evt);
            }
        });
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 52));

        cmbincidencia.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbincidencia.setModel(modeloselincidencia);
        cmbincidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbincidenciaActionPerformed(evt);
            }
        });
        getContentPane().add(cmbincidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 260, 30));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("Personas Seleccionadas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 190, 20));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Fecha :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 90, 20));

        txtcomentario.setColumns(20);
        txtcomentario.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtcomentario.setRows(5);
        jScrollPane1.setViewportView(txtcomentario);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 260, 110));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Comentario :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, 90, 20));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/save1.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 72, 65));
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 270, 30));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Incidencia :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 90, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DefaultTableModel model2 = (DefaultTableModel) jtbdatosgrupos.getModel();
        for (int i = 0; i < jtbdatosgrupos.getRowCount(); i++) {
            model2.removeRow(i);
            i -= 1;
        }
        this.hide();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel3MousePressed

    private void jLabel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel3MouseDragged

    private void cmbincidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbincidenciaActionPerformed
        Rincidencia incidencia = (Rincidencia) cmbincidencia.getSelectedItem();
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbincidenciaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String dia;
        select_incidencia sin = new select_incidencia();
        select_fechas selc = new select_fechas();
        if (cmbincidencia.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Seleciona Incidencia por favor!");

        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                String formato = jDateChooser1.getDateFormatString();
                java.util.Date dates = jDateChooser1.getDate();
                Rincidencia incidencia = (Rincidencia) cmbincidencia.getSelectedItem();
                int dias = incidencia.getDias();
                java.util.Date aumentd = sle.sumarRestarDiasFecha(dates, dias);
                String Fechainicio = sdf.format(dates);
                String fechafin = sdf.format(aumentd);
                guardar(Fechainicio, fechafin);
            } catch (SQLException ex) {
                Logger.getLogger(Incidenciasgrupales.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Incidenciasgrupales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Incidenciasgrupales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Incidenciasgrupales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Incidenciasgrupales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Incidenciasgrupales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbincidencia;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jtbdatosgrupos;
    private javax.swing.JTextArea txtcomentario;
    // End of variables declaration//GEN-END:variables
}
