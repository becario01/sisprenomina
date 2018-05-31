/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Programacion 04
 */
public class RH_Calendario2 extends javax.swing.JFrame {

    boolean cerrar = false;
    static int numcalen = 0;
    SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");

    public RH_Calendario2(int numcal) throws ParseException {
        initComponents();
        numcalen = numcal;
        //se obtiene el numero del calendario 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(233, 236, 241));
        agregarOyente();
        asignarfecha();
    }

    public void cerra() {
        this.show(false);
    }

    public void asignarfecha() {
        //se asigna la fecha actual dependiendo el numero de el calendario 
        switch (numcalen) {
            case 1:
                RH_Calculofaltas.txtInicio.setText(formatoDeFecha.format(calendario2.getDate()));
                break;
            case 2:
                RH_Calculofaltas.txtFin.setText(formatoDeFecha.format(calendario2.getDate()));
                break;
            case 3:
                RH_fechasReporte.txtDate1.setText(formatoDeFecha.format(calendario2.getDate()));
                break;
            case 4:
                RH_fechasReporte.txtDate2.setText(formatoDeFecha.format(calendario2.getDate()));
                break;
            case 5:
                RH_SelectPD.txtFecha.setText(formatoDeFecha.format(calendario2.getDate()));
                break;
            case 6:
                RH_PercepcionesDeducciones.txtdate1.setText(formatoDeFecha.format(calendario2.getDate()));
                break;
            case 7:
                RH_PercepcionesDeducciones.txtdate2.setText(formatoDeFecha.format(calendario2.getDate()));
                break;
            case 8:
                RH_UsuariosConIncidencias.txtdate1.setText(formatoDeFecha.format(calendario2.getDate()));
                RH_UsuariosConIncidencias.Rdate1 = formatoDeFecha.format(calendario2.getDate());
                break;
            case 9:
                RH_UsuariosConIncidencias.txtdate2.setText(formatoDeFecha.format(calendario2.getDate()));
                RH_UsuariosConIncidencias.Rdate2 = formatoDeFecha.format(calendario2.getDate());
                break;
            case 10:
                RH_UsuariosSinIncidencias.txtdate1.setText(formatoDeFecha.format(calendario2.getDate()));
                RH_UsuariosSinIncidencias.Rdate1 = formatoDeFecha.format(calendario2.getDate());
                break;
            case 11:
                RH_UsuariosSinIncidencias.txtdate2.setText(formatoDeFecha.format(calendario2.getDate()));
                RH_UsuariosSinIncidencias.Rdate2 = formatoDeFecha.format(calendario2.getDate());
                break;

            default:
                break;
        }
    }

    private void agregarOyente() throws ParseException {
//se asigna la fecha seleccionada dependiendo el numero de el calendario 
        calendario2.getDayChooser().addPropertyChangeListener(
                new java.beans.PropertyChangeListener() {

                    @Override
                    public void propertyChange(java.beans.PropertyChangeEvent evt) {

                        cerrar = true;

                        switch (numcalen) {
                            case 1:
                                RH_Calculofaltas.txtInicio.setText(formatoDeFecha.format(calendario2.getDate()));
                                break;
                            case 2:
                                RH_Calculofaltas.txtFin.setText(formatoDeFecha.format(calendario2.getDate()));
                                break;
                            case 3:
                                RH_fechasReporte.txtDate1.setText(formatoDeFecha.format(calendario2.getDate()));
                                break;
                            case 4:
                                RH_fechasReporte.txtDate2.setText(formatoDeFecha.format(calendario2.getDate()));
                                break;
                            case 5:
                                RH_SelectPD.txtFecha.setText(formatoDeFecha.format(calendario2.getDate()));
                                break;
                            case 6:
                                RH_PercepcionesDeducciones.txtdate1.setText(formatoDeFecha.format(calendario2.getDate()));
                                break;
                            case 7:
                                RH_PercepcionesDeducciones.txtdate2.setText(formatoDeFecha.format(calendario2.getDate()));
                                break;
                            case 8:
                                RH_UsuariosConIncidencias.txtdate1.setText(formatoDeFecha.format(calendario2.getDate()));
                                RH_UsuariosConIncidencias.Rdate1 = formatoDeFecha.format(calendario2.getDate());
                                break;
                            case 9:
                                RH_UsuariosConIncidencias.txtdate2.setText(formatoDeFecha.format(calendario2.getDate()));
                                RH_UsuariosConIncidencias.Rdate2 = formatoDeFecha.format(calendario2.getDate());
                                break;
                            case 10:
                                RH_UsuariosSinIncidencias.txtdate1.setText(formatoDeFecha.format(calendario2.getDate()));
                                RH_UsuariosSinIncidencias.Rdate1 = formatoDeFecha.format(calendario2.getDate());
                                break;
                            case 11:
                                RH_UsuariosSinIncidencias.txtdate2.setText(formatoDeFecha.format(calendario2.getDate()));
                                RH_UsuariosSinIncidencias.Rdate2 = formatoDeFecha.format(calendario2.getDate());
                                break;
                            default:
                                break;
                        }

                        cerra();
                    }
                });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calendario2 = new com.toedter.calendar.JCalendar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        calendario2.setBackground(new java.awt.Color(0, 102, 255));
        calendario2.setDecorationBackgroundColor(new java.awt.Color(176, 249, 252));
        calendario2.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        calendario2.setSundayForeground(new java.awt.Color(255, 0, 0));
        calendario2.setWeekdayForeground(new java.awt.Color(0, 0, 255));
        calendario2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendario2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(calendario2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(calendario2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void calendario2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendario2MouseClicked
        cerra();
    }//GEN-LAST:event_calendario2MouseClicked

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
            java.util.logging.Logger.getLogger(RH_Calendario2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RH_Calendario2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RH_Calendario2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RH_Calendario2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RH_Calendario2(numcalen).setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(RH_Calendario2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar calendario2;
    // End of variables declaration//GEN-END:variables
}
