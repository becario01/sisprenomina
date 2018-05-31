/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Conexion.Conexion;
import Controller.EJefes;
import Controller.EstiloPercepReport;
import Controller.PercepcionesReport;
import Controller.PrimaDominical;
import Controller.estilosreporte;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Programacion 2
 */
public class RH_UsuariosConIncidencias extends javax.swing.JFrame {
    
    Connection conn;
    PreparedStatement stmt;
    public static ResultSet rs;
    private Connection userConn;
    private TableRowSorter trsFiltro;
    int x, y;
    public static String codid;
    public static boolean TstVentNvoPres = false;    
    Vector<String> empleadosR = new Vector<String>();
    public static String Rdate1="";
    public static String Rdate2="";
    /**
     * Creates new form RH_UsuariosConIncidencias
     *
     * @throws java.sql.SQLException
     */
    public RH_UsuariosConIncidencias() throws SQLException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
      
        combodepartamento();
        cargarTitulos1();
        panelincidencias.setVisible(false);
        tbincidencias.setDefaultRenderer(Object.class, new EJefes());
        lblnombrerh.setHorizontalAlignment(lblnombrerh.CENTER);
        lblnombrerh.setVerticalAlignment(lblnombrerh.CENTER);
        lblcargo.setHorizontalAlignment(lblcargo.CENTER);
        lblcargo.setVerticalAlignment(lblcargo.CENTER);
    }
    //creacion del modelo de la tabla 
    DefaultTableModel tabla1 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int Fila, int Colum) {
            return false;
        }
    };
    //carga los titulos de la tabla 
    public void cargarTitulos1() throws SQLException {
        
        tabla1.addColumn("ID");
        tabla1.addColumn("NOMBRE");
        tabla1.addColumn("DEPARTAMENTO");
        tabla1.addColumn("PUESTO");
        this.tbincidencias.setModel(tabla1);
        TableColumnModel columnModel = tbincidencias.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(150);
        
    }
    
   
    //carga los departamentos existentes a el combo box 
    public void combodepartamento() {
        
        String sql = "select DISTINCT depto from empleados";
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            comboDepto.addItem("-SELECCIONE UNA OPCION-");
            while (rs.next()) {
                String nombre = rs.getString("depto");
                comboDepto.addItem(nombre);
                
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
    //carga los datos por rango 
    public void CargarDatosRango(Vector<String> dias) throws SQLException {
        empleadosR.clear();
        for (int dia = 0; dia < dias.size(); dia++) {
            String fecha = dias.elementAt(dia);
            
            String sql = "SELECT DISTINCT  emp.empleadoId, emp.nombre, emp.depto, emp.puesto\n"
                    + "                    from incidencias inc\n"
                    + "                    INNER JOIN empleados emp on inc.empleadoId= emp.empleadoId\n"
                    + "                    INNER JOIN semanas se on inc.idSemana= se.idSemana\n"
                    + "                    where  inc.fecha='" + fecha + "'";
            String datos[] = new String[10];
            try {
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
                    if (empleadosR.isEmpty()) {
                        empleadosR.add(datos[0]);
                        tabla1.addRow(datos);
                    }
                    for (int i = 0; i < empleadosR.size(); i++) {
                        boolean1 = empleadosR.elementAt(i).equalsIgnoreCase(datos[0]);
                        if (boolean1) {
                            boolean2 = true;
                        }
                    }
                    if (boolean2) {
                    } else {
                        empleadosR.add(datos[0]);
                        tabla1.addRow(datos);
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
    }
    //carga los datos por rango y por departamento 
    public void cargardatosFiltroDepto(Vector<String> dias, String depto) throws SQLException {
        empleadosR.clear();
        for (int i = 0; i < dias.size(); i++) {
            String fecha = dias.elementAt(i);
            String sql = "SELECT DISTINCT  emp.empleadoId, emp.nombre, emp.depto, emp.puesto\n"
                    + "from incidencias inc\n"
                    + "INNER JOIN empleados emp on inc.empleadoId= emp.empleadoId\n"
                    + "INNER JOIN semanas se on inc.idSemana= se.idSemana\n"
                    + "where  inc.fecha='" + fecha + "'  and emp.depto='" + depto + "'";
            String datos[] = new String[10];
            try {
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
                    if (empleadosR.isEmpty()) {
                        empleadosR.add(datos[0]);
                        tabla1.addRow(datos);
                    }
                    for (int a = 0; a < empleadosR.size(); a++) {
                        boolean1 = empleadosR.elementAt(a).equalsIgnoreCase(datos[0]);
                        if (boolean1) {
                            boolean2 = true;
                        }
                    }
                    if (boolean2) {
                    } else {
                        empleadosR.add(datos[0]);
                        tabla1.addRow(datos);
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
    }
    //lista las fechas de los rangos ingresados 
    public Vector<String> listarfechas() throws ParseException {
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Vector<Date> listaFechas = new Vector<>();
        Vector<String> dias = new Vector<String>();
        listaFechas.clear();
        dias.clear();
        Date fechaInicio = formato.parse(Rdate1);
        Date fechaFin = formato.parse(Rdate2);
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
    
    public void filtroBusqueda(JTextField txt) {
        trsFiltro.setRowFilter(RowFilter.regexFilter(txt.getText()));
    }
    
    public void limpiar(DefaultTableModel tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }
    }
    
    public int obteneridsem(String nombresemana) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select  * from  semanas   where semana= '" + nombresemana + "' ";
        conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        int idsemp = 0;
        if (!rs.next()) {
            JOptionPane.showMessageDialog(rootPane, "No existe la semana");
        } else {
            idsemp = rs.getInt("idSemana");
        }
        return idsemp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmAutorizar = new javax.swing.JPopupMenu();
        itemDetalles = new javax.swing.JMenuItem();
        itemPercepciones = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        comboDepto = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnMinimizar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        lblnombrerh = new javax.swing.JLabel();
        lblcargo = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panelincidencias = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnSobresueldo = new javax.swing.JButton();
        btnPrimaDominical = new javax.swing.JButton();
        btnPercepcionesYDeducciones = new javax.swing.JButton();
        btnReporteGeneral = new javax.swing.JButton();
        btntxtreporte = new javax.swing.JButton();
        btnCalcularFaltas = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbincidencias = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtdate2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtdate1 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        label = new javax.swing.JLabel();

        itemDetalles.setText("Detalles");
        itemDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDetallesActionPerformed(evt);
            }
        });
        pmAutorizar.add(itemDetalles);

        itemPercepciones.setText("Percepciones y Deducciones");
        itemPercepciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPercepcionesActionPerformed(evt);
            }
        });
        pmAutorizar.add(itemPercepciones);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fecha Fin");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 80, 30));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Reportar area");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 110, 30));

        comboDepto.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        comboDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDeptoActionPerformed(evt);
            }
        });
        getContentPane().add(comboDepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 229, 30));

        jPanel2.setBackground(new java.awt.Color(229, 230, 234));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/portafolio.png"))); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/user.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        btnMinimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/minimizar.png"))); // NOI18N
        btnMinimizar.setBorderPainted(false);
        btnMinimizar.setContentAreaFilled(false);
        btnMinimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, 32, 30));

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/error.png"))); // NOI18N
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 0, 32, 30));

        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/regresar.png"))); // NOI18N
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });
        jPanel2.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, 32, 30));

        lblnombrerh.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblnombrerh.setForeground(new java.awt.Color(51, 102, 255));
        lblnombrerh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel2.add(lblnombrerh, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 320, 20));

        lblcargo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblcargo.setForeground(new java.awt.Color(51, 102, 255));
        lblcargo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 102, 255)));
        jPanel2.add(lblcargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 330, 20));

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
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 52));

        panelincidencias.setBackground(new java.awt.Color(51, 102, 255));
        panelincidencias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSobresueldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/sobrL.png"))); // NOI18N
        btnSobresueldo.setBorderPainted(false);
        btnSobresueldo.setContentAreaFilled(false);
        btnSobresueldo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/sobO.png"))); // NOI18N
        jPanel1.add(btnSobresueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, 73, -1));

        btnPrimaDominical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/pridoL.png"))); // NOI18N
        btnPrimaDominical.setBorderPainted(false);
        btnPrimaDominical.setContentAreaFilled(false);
        btnPrimaDominical.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/pridoO.png"))); // NOI18N
        btnPrimaDominical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimaDominicalActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimaDominical, new org.netbeans.lib.awtextra.AbsoluteConstraints(309, 109, 73, -1));

        btnPercepcionesYDeducciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/xperxslL.png"))); // NOI18N
        btnPercepcionesYDeducciones.setBorderPainted(false);
        btnPercepcionesYDeducciones.setContentAreaFilled(false);
        btnPercepcionesYDeducciones.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/xperxslO.png"))); // NOI18N
        btnPercepcionesYDeducciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPercepcionesYDeduccionesActionPerformed(evt);
            }
        });
        jPanel1.add(btnPercepcionesYDeducciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 73, -1));

        btnReporteGeneral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/REPORTExlsL.png"))); // NOI18N
        btnReporteGeneral.setBorderPainted(false);
        btnReporteGeneral.setContentAreaFilled(false);
        btnReporteGeneral.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/reportexlsO.png"))); // NOI18N
        btnReporteGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteGeneralActionPerformed(evt);
            }
        });
        jPanel1.add(btnReporteGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(309, 0, 73, -1));

        btntxtreporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/reportetxtL.png"))); // NOI18N
        btntxtreporte.setContentAreaFilled(false);
        btntxtreporte.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/reportetxtO.png"))); // NOI18N
        btntxtreporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntxtreporteActionPerformed(evt);
            }
        });
        jPanel1.add(btntxtreporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 73, -1));

        btnCalcularFaltas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/calfaltasL.png"))); // NOI18N
        btnCalcularFaltas.setBorderPainted(false);
        btnCalcularFaltas.setContentAreaFilled(false);
        btnCalcularFaltas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/calfaltasOO.png"))); // NOI18N
        btnCalcularFaltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularFaltasActionPerformed(evt);
            }
        });
        jPanel1.add(btnCalcularFaltas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 73, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Exportar datos a TXT");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 26, -1, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Reporte General");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(386, 25, -1, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Percepciones y Deducciones");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(747, 21, -1, -1));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Calcular Faltas");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 137, -1, -1));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Calcular Prima Dominical");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(386, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Calcular Sobre Sueldo");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, -1, -1));

        panelincidencias.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        txtBuscar.setBackground(new java.awt.Color(51, 102, 255));
        txtBuscar.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        panelincidencias.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 254, 35));

        tbincidencias= new javax.swing.JTable(){
            public boolean  isCellEditable(int rowIndex,int conlIndex ){
                return false;
            }
        };
        tbincidencias.setAutoCreateRowSorter(true);
        tbincidencias.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbincidencias.setForeground(new java.awt.Color(51, 51, 51));
        tbincidencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbincidencias.setComponentPopupMenu(pmAutorizar);
        tbincidencias.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        tbincidencias.setFillsViewportHeight(true);
        tbincidencias.setGridColor(new java.awt.Color(255, 255, 255));
        tbincidencias.setInheritsPopupMenu(true);
        tbincidencias.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tbincidencias.setSelectionBackground(new java.awt.Color(108, 180, 221));
        tbincidencias.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tbincidencias);

        panelincidencias.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 296, 1060, 190));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/img/search1.png"))); // NOI18N
        panelincidencias.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, 30));

        getContentPane().add(panelincidencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 1060, -1));

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
        getContentPane().add(txtdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 170, 30));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Fecha Inicio");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 100, 30));

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
        txtdate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdate1KeyPressed(evt);
            }
        });
        getContentPane().add(txtdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 170, 30));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 170, -1));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 170, 0));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 170, 10));

        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setText("Pulsa ENTER para buscar ");
        getContentPane().add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
          trsFiltro = new TableRowSorter(tbincidencias.getModel());
          tbincidencias.setRowSorter(trsFiltro);
          

      }//GEN-LAST:event_txtBuscarKeyTyped

      private void comboDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDeptoActionPerformed
          //carga los datos a la table al seleccionar un departamento 
          try {
              limpiar(tabla1);
              String nomdep = comboDepto.getSelectedItem().toString();
              int numdep = comboDepto.getSelectedIndex();
              if (!Rdate1.equalsIgnoreCase("") && !Rdate2.equalsIgnoreCase("")) {
                  if (numdep == 0) {
                      CargarDatosRango(listarfechas());
                  } else {
                      label.setVisible(false);
                      panelincidencias.setVisible(true);
                      cargardatosFiltroDepto(listarfechas(), nomdep);
                  }
              } else {
                  comboDepto.setSelectedIndex(0);
              }
          } catch (SQLException e) {
              JOptionPane.showMessageDialog(null, "Error en " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
          } catch (ParseException ex) {
              Logger.getLogger(RH_UsuariosConIncidencias.class.getName()).log(Level.SEVERE, null, ex);
          }
      }//GEN-LAST:event_comboDeptoActionPerformed

      private void btnReporteGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteGeneralActionPerformed
          
          //boton para generar reporte general 
          String nomdep = comboDepto.getSelectedItem().toString();
          String emp = lblnombrerh.getText();
          String car = lblcargo.getText();
          try {
              RH_fechasReporte rango = new RH_fechasReporte(nomdep, emp, car, 1);
              rango.show();
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
          }
      }//GEN-LAST:event_btnReporteGeneralActionPerformed

    private void btnMinimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizarActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_btnMinimizarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        
        try {
            RH_Inicio sele = new RH_Inicio();
            sele.setVisible(true);
            RH_Inicio.lblcargo.setText(RH_UsuariosConIncidencias.lblcargo.getText());
            RH_Inicio.lblnombrerh.setText(RH_UsuariosConIncidencias.lblnombrerh.getText());
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnAtrasActionPerformed

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel11MousePressed

    private void jLabel11MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel11MouseDragged

    private void btntxtreporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntxtreporteActionPerformed
        //boton para generar reporte txt 
        if (TstVentNvoPres == false) {
            reptxtran rpt = new reptxtran();
            rpt.setVisible(true);
            
            TstVentNvoPres = true;
        } else {
            JOptionPane.showMessageDialog(null, "La ventana Nuevo Prestatario ya esta abierta!!!");
        }
        

    }//GEN-LAST:event_btntxtreporteActionPerformed

    private void btnPercepcionesYDeduccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPercepcionesYDeduccionesActionPerformed
        //boton para generar reporte de percepciones y deducciones 
        String nomdep = comboDepto.getSelectedItem().toString();
        String emp = lblnombrerh.getText();
        String car = lblcargo.getText();
        try {
            RH_fechasReporte rango = new RH_fechasReporte( nomdep, emp, car, 2);
            rango.show();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnPercepcionesYDeduccionesActionPerformed

    private void btnCalcularFaltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularFaltasActionPerformed
        //boton para calcular faltas 
        RH_Calculofaltas clf = new RH_Calculofaltas();
        clf.setVisible(true);
    }//GEN-LAST:event_btnCalcularFaltasActionPerformed

    private void btnPrimaDominicalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimaDominicalActionPerformed
        
        //boton para calcular la prima dominical 
        String nomdep = comboDepto.getSelectedItem().toString();
        String emp = lblnombrerh.getText();
        String car = lblcargo.getText();
        try {
            RH_fechasReporte rango = new RH_fechasReporte( nomdep, emp, car, 3);
            rango.show();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        

    }//GEN-LAST:event_btnPrimaDominicalActionPerformed

    private void itemPercepcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPercepcionesActionPerformed
       //abre la ventana de percepciones 
        System.out.println(Rdate1+"     -     "+Rdate2);
        try {
            int fila = tbincidencias.getSelectedRow();
            int numfila = tbincidencias.getSelectedRowCount();
            
            if (numfila == 1) {
                String nom = tbincidencias.getValueAt(fila, 1).toString();
                String idemp = tbincidencias.getValueAt(fila, 0).toString();
                RH_SelectPD per = new RH_SelectPD(idemp, 1); 
                per.show(true);
                RH_SelectPD.lblnombre.setText(nom);
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una fila", "", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR EN: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemPercepcionesActionPerformed

    private void itemDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemDetallesActionPerformed
        //abre la ventana de detalles de incidencias 
        try {
            System.out.println(Rdate1+"     -     "+Rdate2);
            String nomcargo = lblcargo.getText();
            String nomusuario = lblnombrerh.getText();
            int fila = tbincidencias.getSelectedRow();
            int numfila = tbincidencias.getSelectedRowCount();
            String nomsema = Rdate1+"     -     "+Rdate2;
            if (numfila == 1) {
                String idEmp = tbincidencias.getValueAt(fila, 0).toString();
                String nomEmp = tbincidencias.getValueAt(fila, 1).toString();
                int idempleado = Integer.parseInt(idEmp);
                RH_uci_detalles deta = new RH_uci_detalles(listarfechas(), idempleado, nomcargo, nomusuario);
                deta.show(true);
                
                RH_uci_detalles.txtsemana.setText(nomsema);
                RH_uci_detalles.txtid.setText(idEmp);
                RH_uci_detalles.txtnombre.setText(nomEmp);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila ", "", JOptionPane.WARNING_MESSAGE);
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(RH_UsuariosConIncidencias.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_itemDetallesActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtdate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdate1MouseClicked
        try {
            RH_Calendario2 cale = new RH_Calendario2(8);
            cale.show();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtdate1MouseClicked

    private void txtdate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdate2MouseClicked
        try {
            RH_Calendario2 cale = new RH_Calendario2(9);
            cale.show();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtdate2MouseClicked

    private void txtdate1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdate1KeyPressed
        if (!Rdate2.equalsIgnoreCase("")) {
            try {
                limpiar(tabla1);
                panelincidencias.setVisible(true);
                label.setVisible(false);
                CargarDatosRango(listarfechas());
                System.out.println(empleadosR);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(RH_UsuariosConIncidencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtdate1KeyPressed

    private void txtdate2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdate2KeyPressed
        if (!Rdate1.equalsIgnoreCase("")) {
            try {
                limpiar(tabla1);
                panelincidencias.setVisible(true);
                label.setVisible(false);
                CargarDatosRango(listarfechas());
                System.out.println(empleadosR);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(RH_UsuariosConIncidencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtdate2KeyPressed
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RH_UsuariosConIncidencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new RH_UsuariosConIncidencias().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RH_UsuariosConIncidencias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnCalcularFaltas;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnMinimizar;
    private javax.swing.JButton btnPercepcionesYDeducciones;
    private javax.swing.JButton btnPrimaDominical;
    private javax.swing.JButton btnReporteGeneral;
    private javax.swing.JButton btnSobresueldo;
    private javax.swing.JButton btntxtreporte;
    private javax.swing.JComboBox comboDepto;
    private javax.swing.JMenuItem itemDetalles;
    private javax.swing.JMenuItem itemPercepciones;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel label;
    public static javax.swing.JLabel lblcargo;
    public static javax.swing.JLabel lblnombrerh;
    private javax.swing.JPanel panelincidencias;
    private javax.swing.JPopupMenu pmAutorizar;
    private javax.swing.JTable tbincidencias;
    private javax.swing.JTextField txtBuscar;
    public static javax.swing.JTextField txtdate1;
    public static javax.swing.JTextField txtdate2;
    // End of variables declaration//GEN-END:variables

}
