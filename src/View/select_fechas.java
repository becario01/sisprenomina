/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import BD.Nomincidencia;
import Conexion.Conexion;
import Controller.EJefes;
import Controller.Rincidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Programacion 2
 */
public class select_fechas extends javax.swing.JFrame {

    public static ResultSet rs;
    private Connection userConn;
    private PreparedStatement st;
    Conexion con = new Conexion();
    Connection conn;
    PreparedStatement stmt;
    Nomincidencia rin;
    select_incidencia slin;
    DefaultComboBoxModel<Rincidencia> modeloselincidencia;
    int x, y;

    public select_fechas() {
        modeloselincidencia = new DefaultComboBoxModel<Rincidencia>();
        initComponents();
        rin = new Nomincidencia();
        slin = new select_incidencia();
        cargarModeloSem();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(233, 236, 241));
        cantidadhoras.hide();
        lblcant.hide();
    }

    private void cargarModeloSem() {
        ArrayList<Rincidencia> listaSemanas;
        listaSemanas = rin.obtenerIncnidecnias();
        modeloselincidencia.addElement(new Rincidencia(0, "Selecciona opcion", 1,0));
        for (Rincidencia semana : listaSemanas) {
            modeloselincidencia.addElement(semana);
        }
    }

//    public void inrtevalofechas(String Fechainicio, String Fechafin) throws SQLException, ParseException {
//        String dias = "";
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        PreparedStatement stmt1 = null;
//        ResultSet rs = null;
//        ResultSet rs1 = null;
//        String Finicio = Fechainicio;
//        String[] parts = Finicio.split("-");
//        int añoi = Integer.parseInt(parts[0]);
//        int mesi = Integer.parseInt(parts[1]);
//        int diai = Integer.parseInt(parts[2]);
//
//        String fecharango = "";
//        String[] part = Fechafin.split("-");
//        int añof = Integer.parseInt(part[0]);
//        int mesf = Integer.parseInt(part[1]);
//        int diaf = Integer.parseInt(part[2]);
//
//        Calendar c1 = Calendar.getInstance();
//        c1.set(añoi, mesi - 1, diai);
//        Calendar c2 = Calendar.getInstance();
//        c2.set(añof, mesf - 1, diaf);
//        String mensaje = "";
//        java.util.List<Date> listaEntreFechas = getListaEntreFechas(c1.getTime(), c2.getTime());
//
//        for (int i = 0; i < listaEntreFechas.size(); i++) {
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date fec = listaEntreFechas.get(i);
//            String fe = sdf.format(fec);
//            int numsemanas = numsenanas(fe);
//            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
//            Date fechaActual = new Date();
//            String fechaSistema = formateador.format(fechaActual);
//            int numsem = numsenanas(fechaSistema);
//            select_incidencia sin = new select_incidencia();
//            String indi = select_incidencia.obtenerDiaSemana(fe);
//            EJefes semana = (EJefes) JA_inicio.cmbSemana.getSelectedItem();
//            Rincidencia incidencia = (Rincidencia) cmbincidencia.getSelectedItem();
//            JA_newincidencia nein = new JA_newincidencia();
//            String codigoemp = idempleado.getText();
//            int codigoem = Integer.parseInt(codigoemp);
//            String cantidad = cantidadhoras.getText();
//            String horastrab = "10";
//            String comentario = tctcomentario.getText();
//            int idsemana = semana.getIdSemana();
//            int idincidencia = incidencia.getIdNomIncidencia();
//            if (sin.isNumeric(cantidad)) {
//                cantidad = cantidadhoras.getText();
//                System.out.println(cantidad);
//            } else if (cantidad.equalsIgnoreCase("")) {
//                cantidad = " ";
//                System.out.println(cantidad);
//            } else {
//                JOptionPane.showMessageDialog(rootPane, "Debe ingresar un numero");
//            }
//
//            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
//
//            if (numsemanas >= numsem) {
//                String sql = "select  * from incidencias where  empleadoId='" + codigoemp + "' and fecha='" + fe + "' and idSemana='" + numsemanas + "'  and dia='" + indi + "'";
//
//                stmt = conn.prepareStatement(sql);
//                rs = stmt.executeQuery();
//                if (!rs.next()) {
//                    insertarrangos(codigoem, indi, fe, cantidad, comentario, numsemanas, idincidencia, "10");
//                    mensaje = "Incidencia Registrada!!";
//                } else {
//                    mensaje = "Esta persona ya cuenta con incidencia en algun  dia!!";
//
//                }
//            } else {
//                mensaje = "No puedes insertar Fechas anteriores";
//
//            }
//        }
//
//        JOptionPane.showMessageDialog(rootPane, mensaje);
//    }

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

    public int insertarrangos(int empleadoId, String dia, String fecha, String horasextra, String comentario, int idSemana, int idNomIncidencias, String horasTrab) throws SQLException {
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

    public  int numsenanas(String fecha) throws ParseException, SQLException  {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        java.util.Date date = d.parse(fecha);
        calendar.setTime(date);
        int  numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int year= calendar.get(Calendar.YEAR);
        String numbstring  = String.valueOf(numberWeekOfYear);
        String strinyear = String.valueOf(year);
        String sSubCadena = strinyear.substring(2,4);
        String nomsemana = "SEMANA"+" "+numbstring +"_"+ sSubCadena;   
     
         String sql = "select  * from  semanas   where semana= '"+nomsemana+"' ";
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            String idsemp="";
             if (!rs.next()) {
                 JOptionPane.showMessageDialog(rootPane,"Las fechas  no existen en la Base de Datos, debe existir para registrar incidencias.","Inane warning",JOptionPane.WARNING_MESSAGE);
                }else{
                idsemp = rs.getString("idSemana");
                }
             int idsemanada= Integer.valueOf(idsemp);
             
            
        return idsemanada;
    }
  public int validarmes(String fechaini ,String fechafin) throws ParseException{
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fechaInicial=dateFormat.parse(fechaini);
     Date fechaFinal=dateFormat.parse(fechafin);
     int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
                return dias+1;
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tctcomentario = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbincidencia = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        lblsemana = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        idempleado = new javax.swing.JLabel();
        cantidadhoras = new javax.swing.JTextField();
        lblcant = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/save1.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 460, 72, 65));
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 170, -1));
        getContentPane().add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 170, -1));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Incidencia por Rango de fechas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, 20));

        tctcomentario.setColumns(20);
        tctcomentario.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        tctcomentario.setRows(5);
        jScrollPane1.setViewportView(tctcomentario);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 239, -1));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Comentario");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 90, 30));

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
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 32, 30));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 32, 30));

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 52));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Selecciona incidencia");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, -1));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Fecha Inicio");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 90, -1));

        cmbincidencia.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        cmbincidencia.setModel(modeloselincidencia);
        cmbincidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbincidenciaActionPerformed(evt);
            }
        });
        getContentPane().add(cmbincidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 170, 30));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Fecha Fin");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 80, -1));

        lblsemana.setBackground(new java.awt.Color(0, 0, 0));
        lblsemana.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        getContentPane().add(lblsemana, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 130, 30));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("EmpeleadoID:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, 30));

        idempleado.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        getContentPane().add(idempleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 90, 30));
        getContentPane().add(cantidadhoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 50, 30));

        lblcant.setText("Cantidad de horas");
        getContentPane().add(lblcant, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 130, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

//        try {
//            if (jDateChooser2.getDate() == null && jDateChooser1.getDate() == null) {//devuelve verdadero si es ese mismo el botón que se ha pulsado
//                JOptionPane.showMessageDialog(null, "Ambos campos estas vacios");
//            } else if (jDateChooser1.getDate() == null) {
//                JOptionPane.showMessageDialog(null, "El campo Fecha fin esta vacio");
//            } else if (jDateChooser2.getDate() == null) {
//                JOptionPane.showMessageDialog(null, "El campo Fecha inicio esta vacio");
//            } else if (cmbincidencia.getSelectedIndex() == 0) {
//                JOptionPane.showMessageDialog(null, "Seleciona Incidencia");
//            } else {
//                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
//                //fecha inicio
//                String formato = jDateChooser2.getDateFormatString();
//                Date dates = jDateChooser2.getDate();
//                String fechainicio = String.valueOf(sdf.format(dates));
//                //fecha fin
//                String formatofin = jDateChooser1.getDateFormatString();
//                Date datefin = jDateChooser1.getDate();
//                String fechafin = String.valueOf(sdf.format(datefin));
//                if (datefin.before(dates)) {
//                    String resultado = "La Fecha Inicio no puede ser  Mayor ";
//                    JOptionPane.showMessageDialog(rootPane, resultado);
//                } else {
//                    System.out.println(fechainicio+fechafin);
//                int dias =    validarmes(fechainicio,fechafin);
//                    if (dias <= 30) {
//                          System.out.println(dias);
//                    inrtevalofechas(fechainicio, fechafin);
//                    }else{
//                  System.out.println(dias);   
//                  JOptionPane.showMessageDialog(null, "Solo es posible asignar incidencias por 30 Dias ", "Dias!!", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Al menos elija una FECHA DE NACIMIENTO VALIDA ");
//
//        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel7MousePressed

    private void jLabel7MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel7MouseDragged

    private void cmbincidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbincidenciaActionPerformed
        Rincidencia incidencia = (Rincidencia) cmbincidencia.getSelectedItem();
        if (incidencia.getIncidencia().equalsIgnoreCase("Horas extras")) {
            cantidadhoras.show();
            lblcant.show();

        } else {
            cantidadhoras.hide();
            lblcant.hide();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cmbincidenciaActionPerformed

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
            java.util.logging.Logger.getLogger(select_fechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(select_fechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(select_fechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(select_fechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new select_fechas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cantidadhoras;
    private javax.swing.JComboBox cmbincidencia;
    public static javax.swing.JLabel idempleado;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblcant;
    public static javax.swing.JLabel lblsemana;
    private javax.swing.JTextArea tctcomentario;
    // End of variables declaration//GEN-END:variables
}
