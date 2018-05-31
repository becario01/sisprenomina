/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Programacion 04
 */
public class RH_PercepcionesDeducciones extends javax.swing.JFrame {

    Connection conn;
    PreparedStatement stmt;
    public static ResultSet rs;
    private Connection userConn;
    private TableRowSorter trsFiltro;
    int x, y;
    public static Connection conn5;
    public static PreparedStatement stmt5;
    public static ResultSet rs5;
    private static Connection userConn5;
    Vector<String> empleados = new Vector<String>();

    public RH_PercepcionesDeducciones() throws SQLException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        cargarTitulos1();
//        combosemana();
        combodepto();
        lblnombrerh.setHorizontalAlignment(lblnombrerh.CENTER);
        lblnombrerh.setVerticalAlignment(lblnombrerh.CENTER);
        lblcargo.setHorizontalAlignment(lblcargo.CENTER);
        lblcargo.setVerticalAlignment(lblcargo.CENTER);
        panel.setVisible(false);

    }

    DefaultTableModel tabla1 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int Fila, int Colum) {
            return false;
        }
    };

//    public void combosemana() {
//
//        String sql = "select semana from semanas where estatus=1";
//
//        try {
//            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
//            stmt = conn.prepareStatement(sql);
//            rs = stmt.executeQuery();
//            cmbsema.addItem("-SELECCIONE UNA OPCION-");
//            while (rs.next()) {
//                String nombre = rs.getString("semana");
//                cmbsema.addItem(nombre);
//
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            Conexion.close(rs);
//            Conexion.close(stmt);
//            if (this.userConn == null) {
//                Conexion.close(conn);
//            }
//        }
//    }
    public void combodepto() {

        String sql = "select DISTINCT depto from empleados  ";

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            cmbdepto.addItem("-SELECCIONE UNA OPCION-");
            while (rs.next()) {
                String nombre = rs.getString("depto");
                cmbdepto.addItem(nombre);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
    }

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
        } catch (Exception e) {
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

    public void filtroBusqueda(JTextField txt) {
        trsFiltro.setRowFilter(RowFilter.regexFilter(txt.getText()));
    }

    public void limpiar(DefaultTableModel tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }
    }

    public Vector<String> listarfechas() throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Vector<Date> listaFechas = new Vector<>();
        Vector<String> dias = new Vector<String>();
        listaFechas.clear();
        dias.clear();
        Date fechaInicio = formato.parse(txtdate1.getText());
        Date fechaFin = formato.parse(txtdate2.getText());
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopDetalle = new javax.swing.JPopupMenu();
        ItemDetalles = new javax.swing.JMenuItem();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblnombrerh = new javax.swing.JLabel();
        lblcargo = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbpercepciones = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        cmbdepto = new javax.swing.JComboBox();
        txtdate1 = new javax.swing.JTextField();
        txtdate2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        label = new javax.swing.JLabel();

        ItemDetalles.setText("Detalles");
        ItemDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemDetallesActionPerformed(evt);
            }
        });
        PopDetalle.add(ItemDetalles);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Reportar area");

        jPanel2.setBackground(new java.awt.Color(229, 230, 234));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/portafolio.png"))); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/user.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/minimizar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 32, 30));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/error.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 32, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 32, 30));

        lblnombrerh.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblnombrerh.setForeground(new java.awt.Color(51, 102, 255));
        lblnombrerh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel2.add(lblnombrerh, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 240, 20));

        lblcargo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblcargo.setForeground(new java.awt.Color(51, 102, 255));
        lblcargo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel2.add(lblcargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 230, 20));

        jLabel11.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel11MouseDragged(evt);
            }
        });
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
        });
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 50));

        panel.setBackground(new java.awt.Color(51, 102, 255));

        tbpercepciones= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tbpercepciones.setAutoCreateRowSorter(true);
        tbpercepciones.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbpercepciones.setForeground(new java.awt.Color(51, 51, 51));
        tbpercepciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbpercepciones.setComponentPopupMenu(PopDetalle);
        tbpercepciones.setFillsViewportHeight(true);
        tbpercepciones.setGridColor(new java.awt.Color(255, 255, 255));
        tbpercepciones.setInheritsPopupMenu(true);
        tbpercepciones.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tbpercepciones.setSelectionBackground(new java.awt.Color(108, 180, 221));
        tbpercepciones.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tbpercepciones);

        txtBuscar.setBackground(new java.awt.Color(51, 102, 255));
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setBorder(null);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/search1.png"))); // NOI18N
        jLabel4.setToolTipText("");

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setAlignmentX(0.7F);
        jSeparator2.setAlignmentY(0.8F);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(68, 68, 68)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(544, Short.MAX_VALUE)))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(435, Short.MAX_VALUE)))
        );

        cmbdepto.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbdepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbdeptoActionPerformed(evt);
            }
        });

        txtdate1.setBackground(new java.awt.Color(51, 102, 255));
        txtdate1.setFont(new java.awt.Font("Century Gothic", 2, 18)); // NOI18N
        txtdate1.setForeground(new java.awt.Color(255, 255, 255));
        txtdate1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtdate1.setBorder(null);
        txtdate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdate1MouseClicked(evt);
            }
        });
        txtdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdate1ActionPerformed(evt);
            }
        });
        txtdate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdate1KeyPressed(evt);
            }
        });

        txtdate2.setBackground(new java.awt.Color(51, 102, 255));
        txtdate2.setFont(new java.awt.Font("Century Gothic", 2, 18)); // NOI18N
        txtdate2.setForeground(new java.awt.Color(255, 255, 255));
        txtdate2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtdate2.setBorder(null);
        txtdate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdate2MouseClicked(evt);
            }
        });
        txtdate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdate2KeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Fecha Inicio");

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Fecha Fin");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setAlignmentX(0.7F);
        jSeparator1.setAlignmentY(0.8F);

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator3.setAlignmentX(0.7F);
        jSeparator3.setAlignmentY(0.8F);

        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setText("Pulsa ENTER para buscar ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbdepto, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 866, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtdate1, txtdate2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(cmbdepto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtdate1, txtdate2});

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
        try {
            RH_Inicio sele = new RH_Inicio();
            sele.setVisible(true);
            RH_Inicio.lblcargo.setText(dep);
            RH_Inicio.lblnombrerh.setText(nom);
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel11MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel11MouseDragged

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel11MousePressed

    private void cmbdeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbdeptoActionPerformed
        try {

            String depp = cmbdepto.getSelectedItem().toString();
            if (!txtdate1.getText().equalsIgnoreCase("") || !txtdate2.getText().equalsIgnoreCase("")) {
                limpiar(tabla1);
                if (depp.equalsIgnoreCase("-SELECCIONE UNA OPCION-")) {
                    panel.setVisible(true);
                    CargarDatosRango(listarfechas());
                } else {
                    cargardatosFiltroDepto(listarfechas(), depp);
                    panel.setVisible(true);
                }
            } else {
                cmbdepto.setSelectedIndex(0);
                JOptionPane.showMessageDialog(null, "Si desea hacer un filtro por departamento SELECCIONE ANTES UNA SEMANA", "", JOptionPane.WARNING_MESSAGE);
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(RH_PercepcionesDeducciones.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_cmbdeptoActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtBuscar.getText()).toUpperCase();
                txtBuscar.setText(cadena);
                repaint();
                filtroBusqueda(txtBuscar);
            }
        });
        trsFiltro = new TableRowSorter(tbpercepciones.getModel());
        tbpercepciones.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void ItemDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemDetallesActionPerformed
        try {
            int count = tbpercepciones.getSelectedRowCount();
            int fila = tbpercepciones.getSelectedRow();
            String nomcargo = lblcargo.getText();
            String nomusuario = lblnombrerh.getText();
            String inicio = txtdate1.getText();
            String fin = txtdate2.getText();
            if (count == 1) {
                String idemp = tbpercepciones.getValueAt(fila, 0).toString();
                String nomemp = tbpercepciones.getValueAt(fila, 1).toString();
                RH_detallePercep dper = new RH_detallePercep(listarfechas(), idemp, nomcargo, nomusuario);
                dper.show();
                RH_detallePercep.lblcargo.setText(nomcargo);
                RH_detallePercep.lblnombrerh.setText(nomusuario);
                RH_detallePercep.txtsemana.setText("FECHA:  " + inicio + "  -  " + fin);
                RH_detallePercep.txtid.setText(idemp);
                RH_detallePercep.txtnombre.setText(nomemp);
            } else if (count == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila ", "", JOptionPane.WARNING_MESSAGE);
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e, "", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(RH_PercepcionesDeducciones.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_ItemDetallesActionPerformed

    private void txtdate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdate1MouseClicked
        try {
            RH_Calendario2 cale = new RH_Calendario2(6);
            cale.show();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtdate1MouseClicked

    private void txtdate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdate2MouseClicked

        try {
            RH_Calendario2 cale = new RH_Calendario2(7);
            cale.show();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtdate2MouseClicked

    private void txtdate1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdate1KeyPressed
        if (!txtdate2.getText().equalsIgnoreCase("")) {
            try {
                limpiar(tabla1);
                panel.setVisible(true);
                label.setVisible(false);
                CargarDatosRango(listarfechas());
                System.out.println(empleados);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtdate1KeyPressed

    private void txtdate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdate2KeyPressed
        if (!txtdate1.getText().equalsIgnoreCase("")) {
            try {
                limpiar(tabla1);
                panel.setVisible(true);
                label.setVisible(false);
                CargarDatosRango(listarfechas());
                System.out.println(empleados);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtdate2KeyPressed

    private void txtdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdate1ActionPerformed

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
            java.util.logging.Logger.getLogger(RH_PercepcionesDeducciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RH_PercepcionesDeducciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RH_PercepcionesDeducciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RH_PercepcionesDeducciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RH_PercepcionesDeducciones().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RH_PercepcionesDeducciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemDetalles;
    private javax.swing.JPopupMenu PopDetalle;
    private javax.swing.JComboBox cmbdepto;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel label;
    public static javax.swing.JLabel lblcargo;
    public static javax.swing.JLabel lblnombrerh;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tbpercepciones;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtdate1;
    public static javax.swing.JTextField txtdate2;
    // End of variables declaration//GEN-END:variables

    private void cargardatosFiltroDepto(Vector<String> dias, String depp) {
        try {
            empleados.clear();
            for (int i = 0; i < dias.size(); i++) {
                String fecha = dias.elementAt(i);
                String sql = "select DISTINCT per.empleadoId, em.nombre,em.depto, em.puesto\n"
                        + "from percepciones per \n"
                        + "INNER JOIN empleados em on per.empleadoId=em.empleadoId\n"
                        + "INNER JOIN semanas se on per.Semana=se.semana\n"
                        + "where per.fecha='" + fecha + "' and em.depto='" + depp + "'";
                String datos[] = new String[13];
                conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    boolean boolean1 = false;
                    boolean boolean2 = false;
                    datos[0] = rs.getString("empleadoId");
                    datos[1] = rs.getString("nombre");
                    datos[2] = rs.getString("depto");
                    datos[3] = rs.getString("puesto");
                    if (empleados.isEmpty()) {
                        empleados.add(datos[0]);
                        tabla1.addRow(datos);
                    }
                    for (int a = 0; a < empleados.size(); a++) {
                        boolean1 = empleados.elementAt(a).equalsIgnoreCase(datos[0]);
                        if (boolean1) {
                            boolean2 = true;
                        }
                    }
                    if (boolean2) {
                    } else {
                        empleados.add(datos[0]);
                        tabla1.addRow(datos);
                    }
                }
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

    public void cargarTitulos1() throws SQLException {

        tabla1.addColumn("ID");
        tabla1.addColumn("NOMBRE");
        tabla1.addColumn("DEPARTAMENTO");
        tabla1.addColumn("PUESTO");
        this.tbpercepciones.setModel(tabla1);
        TableColumnModel columnModel = tbpercepciones.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(150);
    }

    public void CargarDatosRango(Vector<String> dias) {
        empleados.clear();
        for (int dia = 0; dia < dias.size(); dia++) {

            String fecharepo = dias.elementAt(dia);

            String sql = "select DISTINCT per.empleadoId, em.nombre,em.depto, em.puesto\n"
                    + "from percepciones per \n"
                    + "INNER JOIN empleados em on per.empleadoId=em.empleadoId\n"
                    + "INNER JOIN semanas se on per.Semana=se.semana\n"
                    + "where per.fecha='" + fecharepo + "'";
            String datos[] = new String[10];

            try {
                conn5 = (RH_PercepcionesDeducciones.userConn5 != null) ? RH_PercepcionesDeducciones.userConn5 : Conexion.getConnection();
                stmt5 = conn5.prepareStatement(sql);
                rs5 = stmt5.executeQuery();
                while (rs5.next()) {
                    boolean boolean1 = false;
                    boolean boolean2 = false;
                    datos[0] = rs5.getString("empleadoId");
                    datos[1] = rs5.getString("nombre");
                    datos[2] = rs5.getString("depto");
                    datos[3] = rs5.getString("puesto");
                    if (empleados.isEmpty()) {
                        empleados.add(datos[0]);
                        tabla1.addRow(datos);
                    }
                    for (int i = 0; i < empleados.size(); i++) {
                        boolean1 = empleados.elementAt(i).equalsIgnoreCase(datos[0]);
                        if (boolean1) {
                            boolean2 = true;
                        }
                    }
                    if (boolean2) {
                    } else {
                        empleados.add(datos[0]);
                        tabla1.addRow(datos);
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            } finally {
                Conexion.close(rs5);
                Conexion.close(stmt5);
                if (RH_PercepcionesDeducciones.userConn5 == null) {
                    Conexion.close(conn5);
                }
            }

        }
    }
}
