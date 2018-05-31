/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Controller.EstiloPercepReport;
import Controller.PercepcionesReport;
import Controller.PrimaDominical;
import Controller.estilosreporte;
import com.sun.awt.AWTUtilities;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *
 * @author Programacion 04
 */
public class RH_fechasReporte extends javax.swing.JFrame {

    Connection conn;
    PreparedStatement stmt;
    public static ResultSet rs;
    private Connection userConn;
    int x, y;
    static String nomdepto;
    static String nomsema;
    static String user;
    static String cargouser;
    static int frame;

    public RH_fechasReporte(String depto, String usurio, String cargo, int jframe) {
        initComponents();
        nomdepto = depto;
     
        user = usurio;
        cargouser = cargo;
        frame = jframe;
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(233, 236, 241));
                                                                                                                                                                                                                                             
        if (jframe == 3) {
            btnguardar.setText("Calcular");
        }
    }
//valida el frame 
    public void seleccionframe() throws ParseException {
        switch (frame) {
            case 1:
                try {
                    OutputStream out;
                    try (HSSFWorkbook workbook = new estilosreporte().generateExcel(semana(nomsema), nomsema, user, cargouser, nomdepto, listarfechas(), txtDate1.getText(), txtDate2.getText())) {
                        if (estilosreporte.resultado) {
                            JFileChooser guardar = new JFileChooser();
                            guardar.setApproveButtonText("Guardar");
                            if (guardar.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                                out = new FileOutputStream(guardar.getSelectedFile() + ".xls");
                                workbook.write(out);
                                out.flush();
                                out.close();
                                JOptionPane.showMessageDialog(null, "Reporte guardado!", "Reporte guardado!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/View/img/ok3.png")));
                            }
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error:  " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error:   " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException ex) {
                    Logger.getLogger(RH_fechasReporte.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case 2:
                try {
                    JFileChooser guardar = new JFileChooser();
                    HSSFWorkbook workbook = new EstiloPercepReport().generateExcel(txtDate1.getText(), txtDate2.getText(), user, cargouser, nomdepto, listarfechas());
                    if (PercepcionesReport.valores) {
                        guardar.setApproveButtonText("Guardar");
                        if (guardar.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                            try (OutputStream out = new FileOutputStream(guardar.getSelectedFile() + ".xls")) {
                                workbook.write(out);
                                workbook.close();
                                out.flush();
                                JOptionPane.showMessageDialog(null, "Reporte guardado!", "Reporte guardado!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/View/img/ok3.png")));
                            }
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException ex) {
                    Logger.getLogger(RH_fechasReporte.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case 3:
                {
                    try {
                        PrimaDominical pri = new PrimaDominical(listarfechas());
                        pri.insertar();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error en:  " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        JOptionPane.showMessageDialog(null, "Prima Dominical actualizada");
                    }       break;
                }
            default:
              JOptionPane.showMessageDialog(null, "seleccione un frame valido");
                break;
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDate2 = new javax.swing.JTextField();
        txtDate1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnguardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione una fecha de inicio y de fin ");

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

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Fecha Inicio");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Fecha Fin");

        txtDate2.setBackground(new java.awt.Color(233, 236, 241));
        txtDate2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDate2.setBorder(null);
        txtDate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDate2MouseClicked(evt);
            }
        });

        txtDate1.setBackground(new java.awt.Color(233, 236, 241));
        txtDate1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDate1.setBorder(null);
        txtDate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDate1MouseClicked(evt);
            }
        });
        txtDate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDate1ActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(138, 229, 239));
        jSeparator1.setForeground(new java.awt.Color(138, 229, 239));

        jSeparator2.setBackground(new java.awt.Color(138, 229, 239));
        jSeparator2.setForeground(new java.awt.Color(138, 229, 239));

        btnguardar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnguardar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnguardar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel7MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel7MouseDragged

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel7MousePressed

    private void txtDate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDate2MouseClicked
      //abre el calendario 
        try {
            RH_Calendario2 cale = new RH_Calendario2(4);
            cale.show(true);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtDate2MouseClicked

    private void txtDate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDate1MouseClicked
        //abre un calendario  
        try {
            RH_Calendario2 cale = new RH_Calendario2(3);
            cale.show(true);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtDate1MouseClicked

    private void txtDate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDate1ActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
//valida que los campos no esten vacion 
        if (txtDate1.getText().equalsIgnoreCase("") && txtDate2.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Ambos campos estan vacios");
        } else if (txtDate1.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "El campo Fecha inicio esta vacio");
        } else if (txtDate2.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "El campo Fecha Fin esta vacio");
        } else {
            try {
                seleccionframe();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnguardarActionPerformed
    //obtiene el nombre de la semana dependiendo la fecha 
    public int semana(String nomsem) {
        String sql = "select * from semanas where semana='" + nomsem + "' ";
        int codigo = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                codigo = Integer.valueOf(rs.getString("idSemana"));
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return codigo;
    }

    public Vector<String> listarfechas() throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Vector<Date> listaFechas = new Vector<>();
        Vector<String> dias = new Vector<String>();
        listaFechas.clear();
        dias.clear();
        Date fechaInicio = formato.parse(txtDate1.getText());
        Date fechaFin = formato.parse(txtDate2.getText());
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);

        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }
        dias.clear();
        for (int i = 0; i < listaFechas.size(); i++) {
            dias.add(formato.format(listaFechas.elementAt(i)));

        }
        return dias;
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
            java.util.logging.Logger.getLogger(RH_fechasReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RH_fechasReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RH_fechasReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RH_fechasReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RH_fechasReporte( nomdepto, user, cargouser, frame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    public static javax.swing.JTextField txtDate1;
    public static javax.swing.JTextField txtDate2;
    // End of variables declaration//GEN-END:variables
}
