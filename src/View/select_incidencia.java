/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import BD.Rjefes;
import BD.Nomincidencia;
import BD.RegistrarIncidencia;
import Conexion.Conexion;
import Controller.EJefes;
import Controller.Rincidencia;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class select_incidencia extends javax.swing.JFrame {

    Nomincidencia rin;
    DefaultComboBoxModel<Rincidencia> modeloselincidencia;
    Rjefes rjf;
    String nom;
    String codi;
    public static ResultSet rs;
    private Connection userConn;
    private PreparedStatement st;
    Conexion con = new Conexion();
    Connection conn;
    PreparedStatement stmt;
    int x, y;

    public select_incidencia() {
        modeloselincidencia = new DefaultComboBoxModel<Rincidencia>();
        rin = new Nomincidencia();
        rjf = new Rjefes();
        cargarModeloINC();
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(233, 236, 241));
        cantidadhoras.hide();
        lblcant.hide();
    }
//calse para mostrar datos del usuario que inicio sesion 
    public void mostrardatos(Object cod, Object nombre) {
        String nom = (String) nombre;
        Object codi = cod;
        lblnombre.setText(nom);
        lblid.setText((String) codi);

    }
     private void cargarModeloINC() {
        ArrayList<Rincidencia> listaSemanas;
        listaSemanas = rin.obtenerIncnidecnias();
        for (Rincidencia semana : listaSemanas) {
            modeloselincidencia.addElement(semana);
        }
    }


//clase para opener dia de la semana 
    public static String obtenerDiaSemana(String fechaP) throws ParseException {
        String[] dias = {"D", "L", "Ma", "Mi", "J", "V", "S"};
        String aux = "";
        fechaP = fechaP.replaceAll(" ", "");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        if (fechaP.equalsIgnoreCase("")) {

        } else {
            String dateInString = fechaP;
            String[] dates = dateInString.split("-");
            String año = dates[0];
            String mes = dates[1];
            String dia = dates[2];
            String fecha = dia + "-" + mes + "-" + año;
            fecha = fecha.replaceAll(" ", "");

            java.util.Date date = formatter.parse(fecha);
            int numeroDia = 0;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            numeroDia = cal.get(Calendar.DAY_OF_WEEK);

            aux = dias[numeroDia - 1];
        }
        return aux;
    }//metodo obtenerDia

//clase para validar si los datos en el campo son  numeros
    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
//obtener intervalo de fechas y guradar en ese intervalo
    public void intervalo(String Fechainicio, String Fechafin) throws SQLException {

        String Finicio = Fechainicio;
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
        c1.set(añoi, mesi - 1, diai);
        Calendar c2 = Calendar.getInstance();
        c2.set(añof, mesf - 1, diaf);
        String mensaje = "";
        java.util.List<java.util.Date> listaEntreFechas = getListaEntreFechas(c1.getTime(), c2.getTime());
        int datos = 0;
        Connection conn = null ;
        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        try{
            ArrayList<String> al = new ArrayList<String>();
        for (int i = 0; i < listaEntreFechas.size(); i++) {
        
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date fec = listaEntreFechas.get(i);
                RegistrarIncidencia inc = new RegistrarIncidencia();
                //datos
                String ide = lblid.getText();
                int idempr = Integer.valueOf(ide);
                select_fechas selc = new select_fechas();
                String fe = sdf.format(fec);
                String indi = select_incidencia.obtenerDiaSemana(fe);
                String horasest = cantidadhoras.getText();
                String comentarios = txtcomentario.getText();
                int idsemana = selc.numsenanas(fe);
                Rincidencia incidencia = (Rincidencia) cmbincidencia.getSelectedItem();
                int idinc = incidencia.getIdNomIncidencia();
                String horastrab = "10";
                String mens = "";
                String sql = "select  * from incidencias where  empleadoId='" + idempr + "' and fecha='" + fe + "'   and dia='" + indi + "'";
                conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                 
                if (!rs.next()) {
                   datos = inc.insert(idempr, indi, fe, horasest, comentarios, idsemana, idinc, horastrab);
                }else{
                  String fechar = rs.getString("fecha");
                 al.add(fechar);
                } 
        }
        if (datos > 0) {
            JOptionPane.showMessageDialog(null, "Registos exitosos");
        }
        if (rs.getRow() >= 1 ) {
           String output="";
            for (int i = 0; i < al.size(); i++) {
                output += "\n"+ al.get(i).toString();
            }
           JOptionPane.showMessageDialog(null, "Existen registros en estas fechas :" + output, "Warning",
        JOptionPane.WARNING_MESSAGE);
        }
        } catch (ParseException ex) {
                Logger.getLogger(select_incidencia.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cmbincidencia = new javax.swing.JComboBox();
        btnincidenciaL = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtcomentario = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cantidadhoras = new javax.swing.JTextField();
        lblcant = new javax.swing.JLabel();
        pnelinfo = new javax.swing.JPanel();
        lblid = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 102, 255));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(552, 444));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/error.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 32, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 32, 30));

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
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 52));

        cmbincidencia.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cmbincidencia.setModel(modeloselincidencia);
        cmbincidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbincidenciaActionPerformed(evt);
            }
        });
        getContentPane().add(cmbincidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 280, 30));

        btnincidenciaL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/save1.png"))); // NOI18N
        btnincidenciaL.setBorder(null);
        btnincidenciaL.setBorderPainted(false);
        btnincidenciaL.setContentAreaFilled(false);
        btnincidenciaL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnincidenciaLActionPerformed(evt);
            }
        });
        getContentPane().add(btnincidenciaL, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 40, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("REGISTRO DE INCIDENCIAS");
        jLabel13.setToolTipText("");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        txtcomentario.setColumns(20);
        txtcomentario.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        txtcomentario.setRows(5);
        jScrollPane3.setViewportView(txtcomentario);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 361, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("Comentario:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 101, 40));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Selecione incidencia:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 165, 30));
        getContentPane().add(cantidadhoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 50, 30));

        lblcant.setText("Cantidad de horas");
        getContentPane().add(lblcant, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 130, 20));

        pnelinfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnelinfo.add(lblid, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 210, 20));

        jLabel6.setText("ID :");
        pnelinfo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 40, 20));

        jLabel5.setText("Nombre :");
        pnelinfo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 60, 20));
        pnelinfo.add(lblnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 210, 20));

        getContentPane().add(pnelinfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 300, 110));
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 170, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Fecha :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 77, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.hide();
    }//GEN-LAST:event_jButton4ActionPerformed

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

    private void btnincidenciaLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnincidenciaLActionPerformed
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
            String formato = jDateChooser1.getDateFormatString();
            java.util.Date dates = jDateChooser1.getDate();
            Rincidencia incidencia = (Rincidencia) cmbincidencia.getSelectedItem();
            int dias = incidencia.getDias();
            java.util.Date aumentd = sumarRestarDiasFecha(dates, dias);
            String Fechainicio = sdf.format(dates);
            String fechafin = sdf.format(aumentd);
            intervalo(Fechainicio, fechafin);
        } catch (SQLException ex) {
            Logger.getLogger(select_incidencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnincidenciaLActionPerformed

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel3MousePressed

    private void jLabel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel3MouseDragged
    public java.util.Date sumarRestarDiasFecha(java.util.Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias - 1);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos	
    }

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
            java.util.logging.Logger.getLogger(select_incidencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(select_incidencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(select_incidencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(select_incidencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new select_incidencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnincidenciaL;
    private javax.swing.JTextField cantidadhoras;
    private javax.swing.JComboBox cmbincidencia;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblcant;
    private javax.swing.JLabel lblid;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JPanel pnelinfo;
    private javax.swing.JTextArea txtcomentario;
    // End of variables declaration//GEN-END:variables
}
