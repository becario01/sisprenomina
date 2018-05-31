/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import BD.Addusuarios;
import Conexion.Conexion;
import Controller.Eempleados;
import static Controller.exportReporte.conn;
import static Controller.exportReporte.stmt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Becarios
 */
public class RH_registrarusrs extends javax.swing.JFrame {

    DefaultComboBoxModel<Eempleados> modelocmbpersona;
    DefaultTableModel modeloasignados = new DefaultTableModel(null, getColumasasig());
    DefaultTableModel modelonoasiganados = new DefaultTableModel(null, getColumasnoasig());
    Addusuarios eemp;
    public static ResultSet rs;
    private Connection userConn;
    private PreparedStatement st;
    int x,y;

    public RH_registrarusrs() {
        eemp = new Addusuarios();
        modelocmbpersona = new DefaultComboBoxModel<Eempleados>();
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        Date fechactual = new Date();
        txtfechauser.setText(dt.format(fechactual));
        cargarModeloEmp();
        tblasignados.getColumnModel().getColumn(0).setMaxWidth(100);
        tblasignados.getColumnModel().getColumn(1).setMaxWidth(380);
        tblnoasignados.getColumnModel().getColumn(0).setMaxWidth(100);
        tblnoasignados.getColumnModel().getColumn(1).setMaxWidth(380);
        lblnomjefe.setHorizontalAlignment(lblnomjefe.CENTER);
     
        lbldepjefe.setHorizontalAlignment(lbldepjefe.CENTER);
    
        try {
            setFilas();
            setFilasnoasig();
        } catch (SQLException ex) {
            Logger.getLogger(RH_registrarusrs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] getColumasasig() {
        String columna[] = {"IDempleado", "Nombre"};
        return columna;
    }

    private String[] getColumasnoasig() {
        String columna[] = {"IDempleado", "Nombre"};
        return columna;
    }

    private void cargarModeloEmp() {
        ArrayList<Eempleados> listaEmp;
        listaEmp = eemp.obtenerEmpleados();
        modelocmbpersona.addElement(new Eempleados(0, "Selecciona opcion", 1, "", ""));
        for (Eempleados semana : listaEmp) {
            modelocmbpersona.addElement(semana);
        }
    }

    public void setFilas() throws SQLException {
        String sql = "select DISTINCT emp.empleadoId, emp.nombre from empleados emp INNER JOIN asignacion asg on  emp.empleadoId = asg.empleadoId\n"
                + "where emp.estatus = 1";
        conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        Object datos[] = new Object[3];
        while (rs.next()) {
            for (int i = 0; i < 3; i++) {
                datos[0] = rs.getString("empleadoId");
                datos[1] = rs.getString("nombre");

            }
            modeloasignados.addRow(datos);
        }
    }

    public void setFilasnoasig() throws SQLException {
        String sql = "SELECT  emp.empleadoId, emp.nombre FROM empleados emp \n"
                + "LEFT JOIN asignacion asg ON emp.empleadoId = asg.empleadoId \n"
                + "WHERE  asg.empleadoId  IS NULL  AND estatus = 1";
        conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        Object datos[] = new Object[3];
        while (rs.next()) {
            for (int i = 0; i < 3; i++) {
                datos[0] = rs.getString("empleadoId");
                datos[1] = rs.getString("nombre");

            }
            modelonoasiganados.addRow(datos);
        }
    }
    
   public String mayusculas(String texto){
       
       String Srt = new String (texto);
       String deptoupp = Srt.toUpperCase();
       return deptoupp;
       
   } 
 public void limpiar(DefaultTableModel tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }

      }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtusuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcomtusu = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtdepartamento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtcontraseña = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtfechauser = new javax.swing.JTextField();
        btnguardarusers = new javax.swing.JButton();
        cmbpersonas = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblnoasignados = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblasignados = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnminimizar = new javax.swing.JButton();
        btncerrar = new javax.swing.JButton();
        btnregresar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lblnomjefe = new javax.swing.JTextField();
        lbldepjefe = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(760, 600));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(229, 230, 234));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtusuario.setBackground(new java.awt.Color(229, 230, 234));
        txtusuario.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel1.add(txtusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 160, 20));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel1.setText("Tipo Usuario :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 125, 82, 20));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel3.setText("Contraseña :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, -1));

        jcomtusu.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jcomtusu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un opcion", "Jefe de area", "Recursos Humanos" }));
        jPanel1.add(jcomtusu, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 150, 200, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel4.setText("Usuario :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 55, 20));

        txtdepartamento.setBackground(new java.awt.Color(229, 230, 234));
        txtdepartamento.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel1.add(txtdepartamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 162, 25));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel5.setText("Departamento :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 125, -1, 20));

        txtcontraseña.setBackground(new java.awt.Color(229, 230, 234));
        txtcontraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel1.add(txtcontraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 160, 20));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel6.setText("Fecha");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 39, 23));

        txtfechauser.setEditable(false);
        txtfechauser.setBackground(new java.awt.Color(229, 230, 234));
        txtfechauser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 255)));
        txtfechauser.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        jPanel1.add(txtfechauser, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 162, -1));

        btnguardarusers.setBackground(new java.awt.Color(51, 102, 255));
        btnguardarusers.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btnguardarusers.setForeground(new java.awt.Color(255, 255, 255));
        btnguardarusers.setText("Guardar");
        btnguardarusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarusersActionPerformed(evt);
            }
        });
        jPanel1.add(btnguardarusers, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 132, 40));

        cmbpersonas.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        cmbpersonas.setModel(modelocmbpersona);
        jPanel1.add(cmbpersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 307, -1));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel8.setText("Nombre Persona : ");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 600, 250));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/adduser.png"))); // NOI18N
        jLabel2.setAlignmentX(0.2F);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nuevos Usuarios ");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 609, 15));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Asignados");
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 439, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("No asignados");
        jLabel10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 439, -1));

        tblnoasignados= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tblnoasignados.setAutoCreateRowSorter(true);
        tblnoasignados.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        tblnoasignados.setForeground(new java.awt.Color(51, 51, 51));
        tblnoasignados.setModel(modelonoasiganados);
        tblnoasignados.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        tblnoasignados.setFillsViewportHeight(true);
        tblnoasignados.setGridColor(new java.awt.Color(255, 255, 255));
        tblnoasignados.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tblnoasignados.setSelectionBackground(new java.awt.Color(108, 180, 221));
        tblnoasignados.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblnoasignados);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 390, 430, 170));

        tblasignados= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tblasignados.setAutoCreateRowSorter(true);
        tblasignados.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        tblasignados.setModel(modeloasignados);
        tblasignados.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        tblasignados.setFillsViewportHeight(true);
        tblasignados.setGridColor(new java.awt.Color(255, 255, 255));
        tblasignados.setInheritsPopupMenu(true);
        tblasignados.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tblasignados.setSelectionBackground(new java.awt.Color(108, 180, 221));
        tblasignados.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tblasignados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 430, 170));

        jButton1.setBackground(new java.awt.Color(113, 195, 134));
        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton1.setText("Asignar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 160, 60));

        jPanel2.setBackground(new java.awt.Color(229, 230, 234));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/portafolio.png"))); // NOI18N
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/user.png"))); // NOI18N
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        btnminimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/minimizar.png"))); // NOI18N
        btnminimizar.setBorderPainted(false);
        btnminimizar.setContentAreaFilled(false);
        btnminimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnminimizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnminimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 0, 32, 30));

        btncerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/error.png"))); // NOI18N
        btncerrar.setBorderPainted(false);
        btncerrar.setContentAreaFilled(false);
        btncerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrarActionPerformed(evt);
            }
        });
        jPanel2.add(btncerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, 32, 30));

        btnregresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        btnregresar.setBorderPainted(false);
        btnregresar.setContentAreaFilled(false);
        btnregresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresarActionPerformed(evt);
            }
        });
        jPanel2.add(btnregresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, 32, 30));

        jLabel13.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel13MouseDragged(evt);
            }
        });
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel13MousePressed(evt);
            }
        });
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 10));

        lblnomjefe.setEditable(false);
        lblnomjefe.setBackground(new java.awt.Color(229, 230, 234));
        lblnomjefe.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblnomjefe.setForeground(new java.awt.Color(51, 102, 255));
        lblnomjefe.setAutoscrolls(false);
        lblnomjefe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        lblnomjefe.setCaretColor(new java.awt.Color(51, 102, 255));
        jPanel2.add(lblnomjefe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 320, 20));

        lbldepjefe.setBackground(new java.awt.Color(229, 230, 234));
        lbldepjefe.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbldepjefe.setForeground(new java.awt.Color(51, 102, 255));
        lbldepjefe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        lbldepjefe.setCaretColor(new java.awt.Color(51, 102, 255));
        jPanel2.add(lbldepjefe, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 330, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarusersActionPerformed
        int emp = cmbpersonas.getSelectedIndex();
        int tuser = jcomtusu.getSelectedIndex();
        Eempleados empleadod = (Eempleados) cmbpersonas.getSelectedItem();
        if (emp == 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione una persona");
        } else if (txtusuario.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(rootPane, "El campo usuario no puede estar vacio");
        } else if (txtcontraseña.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(rootPane, "El campo contraseña no puede estar vacio");
        } else if (tuser == 0) {
            JOptionPane.showMessageDialog(rootPane, "Elija el tipo de  usuario");
        } else if (txtdepartamento.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(rootPane, "Ingrese el nombre del departamento!!!");
        } else {
            try {
                String nombre = empleadod.getNombre();
                String tipouser = (String) jcomtusu.getSelectedItem();
                int tipousers = 0;
                if (tipouser.equalsIgnoreCase("Jefe de area")) {
                    tipousers = 0;
                } else if (tipouser.equalsIgnoreCase("Recursos Humanos")) {
                    tipousers = 1;
                }
                String departamento = mayusculas(txtdepartamento.getText());
                eemp.insert(nombre, txtusuario.getText(), txtcontraseña.getText(), tipousers, 1, departamento, txtfechauser.getText());
                txtcontraseña.setText("");
                txtdepartamento.setText("");
                txtusuario.setText("");
                limpiar(modeloasignados);
                limpiar(modelonoasiganados);
                setFilas();
                setFilasnoasig();
            } catch (SQLException ex) {
                Logger.getLogger(RH_registrarusrs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnguardarusersActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     Asignacion asg = new Asignacion();
     Asignacion.lblcargojefe.setText(lbldepjefe.getText());
     Asignacion.lblnombrejefe.setText(lblnomjefe.getText());
             asg.setVisible(true);
             this.hide();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnminimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnminimizarActionPerformed
        this.setExtendedState(ICONIFIED);        // TODO add your handling code here:
    }//GEN-LAST:event_btnminimizarActionPerformed

    private void btncerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrarActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_btncerrarActionPerformed

    private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
       RH_Inicio ini = new RH_Inicio();
       ini.setVisible(true);
       RH_Inicio.lblcargo.setText(lbldepjefe.getText());
        RH_Inicio.lblnombrerh.setText(lblnomjefe.getText());
       this.hide();
    }//GEN-LAST:event_btnregresarActionPerformed

    private void jLabel13MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseDragged
        this.setLocation(this.getLocation().x+evt.getX()-x, this.getLocation().y+evt.getY()-y);
    }//GEN-LAST:event_jLabel13MouseDragged

    private void jLabel13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel13MousePressed

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
            java.util.logging.Logger.getLogger(RH_registrarusrs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RH_registrarusrs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RH_registrarusrs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RH_registrarusrs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RH_registrarusrs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btncerrar;
    private javax.swing.JButton btnguardarusers;
    public static javax.swing.JButton btnminimizar;
    public static javax.swing.JButton btnregresar;
    private javax.swing.JComboBox cmbpersonas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox jcomtusu;
    public static javax.swing.JTextField lbldepjefe;
    public static javax.swing.JTextField lblnomjefe;
    private javax.swing.JTable tblasignados;
    private javax.swing.JTable tblnoasignados;
    private javax.swing.JPasswordField txtcontraseña;
    private javax.swing.JTextField txtdepartamento;
    private javax.swing.JTextField txtfechauser;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables

  
}
