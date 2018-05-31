/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Conexion.Conexion;
import Controller.EJefes;
import Controller.EstatusSemanas;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import javax.swing.*;

/**
 *
 * @author Programacion 04
 */
public class RH_SEMANA extends javax.swing.JFrame {

    int x, y;
    public static ResultSet rs;
    private Connection userConn;
    private TableRowSorter trsFiltro;
    Connection conn;
    PreparedStatement stmt;
    Date dateultima;
    Date datepriemra;
    String dias[] = new String[7];
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    int clicCale = 0;

    public RH_SEMANA() throws SQLException, ParseException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        cargartitulos();
        nomsenanas(obtenerUltimaSemana());
        asignarfechas();
        tbsemanas.setDefaultRenderer(Object.class, new EJefes());

    }

    DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int Fila, int Colum) {
            return false;
        }
    };

    public void cargartitulos() throws SQLException {
        tabla.addColumn("SEMANA");
        tabla.addColumn("INICIO");
        tabla.addColumn("FIN");
        tabla.addColumn("ESTATUS");
        this.tbsemanas.setModel(tabla);

        TableColumnModel columnModel = tbsemanas.getColumnModel();
       
        columnModel.getColumn(3).setPreferredWidth(5);
        cargardatos();
    }

    public void cargardatos() throws SQLException {
        String sql = "select * from semanas order by idSemana DESC ";
        Object datos[] = new Object[5];

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("semana");
                datos[1] = rs.getString("fechaL");
                datos[2] = rs.getString("fechaD");
                if (rs.getString("estatus").equalsIgnoreCase("1")) {
                    datos[3] = new JLabel(new ImageIcon(getClass().getResource("/View/img/actulizadoj.png")));
                } else {
                    datos[3] = new JLabel(new ImageIcon(getClass().getResource("/View/img/noactualizadoj.png")));
                }
                tabla.addRow(datos);

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

    public void filtroBusqueda(JTextField txt) {
        trsFiltro.setRowFilter(RowFilter.regexFilter(txt.getText()));
    }

    public void limpiar(DefaultTableModel tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }
    }

    public String nomsenanas(String fecha) throws ParseException, SQLException {

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        java.util.Date date = d.parse(fecha);
        calendar.setTime(date);
        int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        String numbstring = String.valueOf(numberWeekOfYear);
        String strinyear = String.valueOf(year);
        String sSubCadena = strinyear.substring(2, 4);
        String nomsemana = "SEMANA" + " " + numbstring + "_" + sSubCadena;
        System.out.println(nomsemana);
        txtsemana.setText(nomsemana);
        return nomsemana;
    }

    public String obtenerUltimaSemana() throws ParseException, SQLException {
        String ultima = "1111-11-11";
        String sql = "select * from semanas order by fechaL asc";
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ultima = rs.getString("fechaD");

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

        Calendar cale = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date f1 = formato.parse(ultima);
        cale.setTime(f1);
        cale.add(Calendar.DAY_OF_YEAR, 1);
        dateultima = cale.getTime();
        ultima = formato.format(cale.getTime());

        return ultima;
    }

    public void asignarfechas() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fech1 = formato.format(dateultima);
        Calendar cale2 = Calendar.getInstance();
        cale2.setTime(dateultima);
        cale2.add(Calendar.DAY_OF_YEAR, 6);
        datepriemra = cale2.getTime();
        String fech2 = formato.format(datepriemra);
        jdate1.setText(fech1);
        jdate2.setText(fech2);
    }

    public void listarfechas() {
        Vector<Date> listaFechas = new Vector<>();
        listaFechas.clear();
        Date fechaInicio = dateultima;
        Date fechaFin = datepriemra;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);

        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        dias[0] = formato.format(listaFechas.elementAt(0));
        dias[1] = formato.format(listaFechas.elementAt(1));
        dias[2] = formato.format(listaFechas.elementAt(2));
        dias[3] = formato.format(listaFechas.elementAt(3));
        dias[4] = formato.format(listaFechas.elementAt(4));
        dias[5] = formato.format(listaFechas.elementAt(5));
        dias[6] = formato.format(listaFechas.elementAt(6));
        for (int i = 0; i < 7; i++) {
            System.out.println(dias[i]);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        accion = new javax.swing.JPopupMenu();
        ItemActivar = new javax.swing.JMenuItem();
        ItemDesactivar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblnombrerh = new javax.swing.JLabel();
        lblcargo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbsemanas = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtsemana = new javax.swing.JTextField();
        jdate1 = new javax.swing.JTextField();
        jdate2 = new javax.swing.JTextField();

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

        ItemActivar.setText("Activar");
        ItemActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemActivarActionPerformed(evt);
            }
        });
        accion.add(ItemActivar);

        ItemDesactivar.setText("Desactivar");
        ItemDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemDesactivarActionPerformed(evt);
            }
        });
        accion.add(ItemDesactivar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(229, 230, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/portafolio.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/user.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/minimizar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 32, 30));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/error.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 32, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 32, 30));

        lblnombrerh.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblnombrerh.setForeground(new java.awt.Color(51, 102, 255));
        lblnombrerh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel1.add(lblnombrerh, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 260, 20));

        lblcargo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblcargo.setForeground(new java.awt.Color(51, 102, 255));
        lblcargo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel1.add(lblcargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 170, 20));

        jLabel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel5MouseDragged(evt);
            }
        });
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 616, 52));

        tbsemanas.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbsemanas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbsemanas.setComponentPopupMenu(accion);
        jScrollPane2.setViewportView(tbsemanas);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 265, 616, 192));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/save1.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 138, 38, 38));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 3, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("DE");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 141, -1, -1));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 3, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("A");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 150, -1, -1));

        txtBuscar.setBackground(new java.awt.Color(51, 102, 255));
        txtBuscar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 209, 217, 35));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/search1.png"))); // NOI18N
        jLabel7.setToolTipText("");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 209, 40, 40));

        txtsemana.setEditable(false);
        txtsemana.setBackground(new java.awt.Color(51, 102, 255));
        txtsemana.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtsemana.setForeground(new java.awt.Color(255, 255, 255));
        txtsemana.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsemana.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtsemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsemanaActionPerformed(evt);
            }
        });
        getContentPane().add(txtsemana, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 82, 194, 28));

        jdate1.setEditable(false);
        jdate1.setBackground(new java.awt.Color(51, 102, 255));
        jdate1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jdate1.setForeground(new java.awt.Color(255, 255, 255));
        jdate1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jdate1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        jdate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jdate1MouseClicked(evt);
            }
        });
        jdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdate1ActionPerformed(evt);
            }
        });
        getContentPane().add(jdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 138, 28));

        jdate2.setEditable(false);
        jdate2.setBackground(new java.awt.Color(51, 102, 255));
        jdate2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jdate2.setForeground(new java.awt.Color(255, 255, 255));
        jdate2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jdate2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        jdate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jdate2MouseClicked(evt);
            }
        });
        getContentPane().add(jdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 147, 138, 28));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        this.setExtendedState(ICONIFIED);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String dep = lblcargo.getText();
        String nom = lblnombrerh.getText();
        RH_Inicio us = new RH_Inicio();
        us.show(true);
        this.show(false);
        RH_Inicio.lblcargo.setText(dep);
        RH_Inicio.lblnombrerh.setText(nom);
        tbsemanas.setDefaultRenderer(Object.class, new EJefes());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel5MouseDragged

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel5MousePressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        limpiar(tabla);
        try {
            listarfechas();
            String nombreSemana = txtsemana.getText();
            EstatusSemanas esta = new EstatusSemanas();
            esta.agregar(dias, nombreSemana);
            cargardatos();
            nomsenanas(obtenerUltimaSemana());
            asignarfechas();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(RH_SEMANA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtBuscar.getText()).toUpperCase();
                txtBuscar.setText(cadena);
                repaint();
                filtroBusqueda(txtBuscar);
            }
        });
        trsFiltro = new TableRowSorter(tbsemanas.getModel());
        tbsemanas.setRowSorter(trsFiltro);

    }//GEN-LAST:event_txtBuscarKeyTyped

    private void ItemActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemActivarActionPerformed
        try {
            EstatusSemanas sema = new EstatusSemanas();
            int numfila = tbsemanas.getSelectedRowCount();
            int fila = tbsemanas.getSelectedRow();
           
            if (numfila == 1) {
                 System.out.println(numfila);
                String semana = tbsemanas.getValueAt(fila, 0).toString();
                System.out.println(semana);
                sema.activar(semana);
                limpiar(tabla);
                cargardatos();
            } else if(numfila==0){
                 System.out.println(numfila);
                JOptionPane.showMessageDialog(null, "Seleccione una fila ", "", JOptionPane.WARNING_MESSAGE);

            }else if (numfila > 1){
                int codi[] = tbsemanas.getSelectedRows();
                 System.out.println(numfila);
                String semana[]= new String[numfila];
                for (int i = 0; i < numfila; i++) {
                    semana[i]=tbsemanas.getValueAt(codi[i], 0).toString();
                    String sem=semana[i];
                    sema.activar(sem);
                    
                }
                limpiar(tabla);
                cargardatos();
                
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_ItemActivarActionPerformed

    private void ItemDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemDesactivarActionPerformed
        try {
            int numfila = tbsemanas.getSelectedRowCount();
            int fila = tbsemanas.getSelectedRow();
            EstatusSemanas sema = new EstatusSemanas();
            if (numfila == 1) {
                String semana = tbsemanas.getValueAt(fila, 0).toString();
                sema.desactivar(semana);
                limpiar(tabla);
                cargardatos();
            } else if(numfila==0){
                JOptionPane.showMessageDialog(null, "", "", JOptionPane.WARNING_MESSAGE);
            }else if (numfila > 1){
                int codi[] = tbsemanas.getSelectedRows();
                 System.out.println(numfila);
                String semana[]= new String[numfila];
                for (int i = 0; i < numfila; i++) {
                    semana[i]=tbsemanas.getValueAt(codi[i], 0).toString();
                    String sem=semana[i];
                    sema.desactivar(sem);
                    
                }
                limpiar(tabla);
                cargardatos();
                
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en:  " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_ItemDesactivarActionPerformed

    private void jdate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jdate1MouseClicked
        try {
             clicCale = 1;
        RH_Calendario cale = new RH_Calendario(clicCale, dateultima);
        if (evt.getClickCount() == 2) {
            cale.setVisible(true);
        }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,"Error en: "+ e);
        }
       


    }//GEN-LAST:event_jdate1MouseClicked

    private void txtsemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsemanaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsemanaActionPerformed

    private void jdate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jdate2MouseClicked
        try {
             clicCale = 2;
        RH_Calendario cale = new RH_Calendario(clicCale, datepriemra);
        if (evt.getClickCount() == 2) {
            cale.setVisible(true);
        }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,"Error en:"+ e);
        }
       

    }//GEN-LAST:event_jdate2MouseClicked

    private void jdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jdate1ActionPerformed

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
            java.util.logging.Logger.getLogger(RH_SEMANA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RH_SEMANA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RH_SEMANA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RH_SEMANA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
//                    UIManager.setLookAndFeel( new SyntheticaBlackStarLookAndFeel());
                    new RH_SEMANA().setVisible(true);

                } catch (SQLException | ParseException ex) {
                    Logger.getLogger(RH_SEMANA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemActivar;
    private javax.swing.JMenuItem ItemDesactivar;
    private javax.swing.JPopupMenu accion;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTextField jdate1;
    public static javax.swing.JTextField jdate2;
    public static javax.swing.JLabel lblcargo;
    public static javax.swing.JLabel lblnombrerh;
    private javax.swing.JTable tbsemanas;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtsemana;
    // End of variables declaration//GEN-END:variables
}
