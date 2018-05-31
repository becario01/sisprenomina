/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.rs
 */
package Controller;

import Conexion.Conexion;
import static Controller.exportReporte.conn;
import static Controller.exportReporte.rs;
import static Controller.exportReporte.rs;
import static Controller.exportReporte.rs1;
import static Controller.exportReporte.stmt;
import static Controller.exportReporte.stmt1;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class estilosreporte {

    public static Connection conn;
    public static PreparedStatement stmt;
    public static ResultSet rs;
    private static Connection userConn;
    public static Connection conn3;
    public static PreparedStatement stmt3;
    public static ResultSet rs3;
    private static Connection userConn3;
    public static Connection conn2;
    public static PreparedStatement stmt2;
    public static ResultSet rs2;
    private static Connection userConn2;
    public static Connection conn5;
    public static PreparedStatement stmt5;
    public static ResultSet rs5;
    private static Connection userConn5;
    private HSSFWorkbook workbook;
    public static boolean resultado = false;
    Vector<String> idempleado = new Vector<String>();
    Vector<String> listadoempe = new Vector<String>();
    String datosR[] = new String[11];
    // Fonts
    private HSSFFont headerFont;
    private HSSFFont headerFont1;
    private HSSFFont contentFont;

    private HSSFCellStyle headerStyle1;
    private HSSFCellStyle headerStyle2;
    private HSSFCellStyle headerStyle;
    private HSSFCellStyle oddRowStyle;
    private HSSFCellStyle evenRowStyle;

    // Integer to store the index of the next row
    private int rowIndex;

    public HSSFWorkbook generateExcel(int idSemana, String semana, String empleado, String cargo, String nomdep, Vector<String> dias, String inicio, String fin) throws SQLException {

        // Initialize rowIndex
        rowIndex = 0;

        // New Workbook
        workbook = new HSSFWorkbook();

        // Generate fonts
        headerFont = createFont(HSSFColor.WHITE.index, (short) 12, true);
        headerFont1 = createFont(HSSFColor.WHITE.index, (short) 19, true);
        contentFont = createFont(HSSFColor.BLACK.index, (short) 11, false);
// se crean los estilos 
        headerStyle1 = createStyle(headerFont1, HSSFCellStyle.ALIGN_CENTER, HSSFColor.BLUE.index, false, HSSFColor.BLACK.index);
        headerStyle2 = createStyle(headerFont, HSSFCellStyle.ALIGN_CENTER, HSSFColor.BLUE.index, false, HSSFColor.WHITE.index);
        headerStyle = createStyle(headerFont, HSSFCellStyle.ALIGN_CENTER, HSSFColor.LIGHT_BLUE.index, false, HSSFColor.DARK_BLUE.index);
        oddRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT, HSSFColor.WHITE.index, false, HSSFColor.GREY_25_PERCENT.index);
        evenRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT, HSSFColor.LIGHT_TURQUOISE.index, false, HSSFColor.WHITE.index);
//se inician el numero de columna 
        int filanombres = 3;
        int filadatos = 3;
        // New sheet
        HSSFSheet sheet = workbook.createSheet("REPORTE");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        row = sheet.createRow(0);

//se crea el encabezado inicial 
        HSSFRow headerRow0 = sheet.createRow(0);
        HSSFCell headerCell0 = null;
        HSSFCell headerCell22 = null;
        headerCell0 = headerRow0.createCell(0);
        headerCell0.setCellStyle(headerStyle1);
        headerCell0.setCellValue("REPORTE GENERAL          " + inicio + "   -   " + fin);
        CellRangeAddress re = new CellRangeAddress(0, 1, 0, 7);
        sheet.addMergedRegion(re);
        HSSFRow headerR2 = sheet.createRow(1);
        HSSFCell header2 = null;
        HSSFRow contentRow1 = null;
        HSSFRow contentRow2 = null;
        HSSFRow contentRow3 = null;
        HSSFCell contentCell1 = null;
        HSSFCell contentCell2 = null;
        HSSFCell contentCell3 = null;
        HSSFCell contentCell4 = null;
        HSSFCell contentCell5 = null;
        HSSFCell contentCell6 = null;
        HSSFCell contentCell7 = null;
        HSSFCell contentCell8 = null;
        HSSFCell contentCell9 = null;
        HSSFCell contentCell10 = null;
        HSSFCell contentCell11 = null;
        HSSFCell contentCell12 = null;
        HSSFCell contentCell13 = null;
//se valida que el rango de fechas contenga registtros 
        listarid(dias, nomdep);
        if (listadoempe.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este rango de fechas no tiene registros", "", JOptionPane.WARNING_MESSAGE);
            resultado = false;
        } else {
            resultado = true;
        }
        String sql = "";
        for (int emple = 0; emple < listadoempe.size(); emple++) {
            String idemple = listadoempe.elementAt(emple);

            if (nomdep.contains("-SELECCIONE UNA OPCION-")) {

                sql = "SELECT DISTINCT  emp.empleadoId, emp.nombre, emp.depto, emp.puesto\n"
                        + "                    from incidencias inc\n"
                        + "                    INNER JOIN empleados emp on inc.empleadoId= emp.empleadoId\n"
                        + "                    INNER JOIN semanas se on inc.idSemana= se.idSemana\n"
                        + "                    where inc.empleadoId='" + idemple + "'";
            } else {
                sql = "SELECT DISTINCT  emp.empleadoId, emp.nombre, emp.depto, emp.puesto\n"
                        + "                    from incidencias inc\n"
                        + "                    INNER JOIN empleados emp on inc.empleadoId= emp.empleadoId\n"
                        + "                    INNER JOIN semanas se on inc.idSemana= se.idSemana\n"
                        + "                    where inc.empleadoId='" + idemple + "' and emp.depto='" + nomdep + "' ";
                headerCell0 = headerRow0.createCell(0);
                headerCell0.setCellStyle(headerStyle1);
                headerCell0.setCellValue("REPORTE GENERAL          " + inicio + "  -  " + fin + "                   " + nomdep);
            }

            HSSFRow headerRow;
            HSSFCell headerCell = null;
            HSSFRow headerRoe;
//            se crea un listados con los titulos de datos de los empleados 
            List<String> TITULOS = exportReporte.getTableHeaders();
            String datos[] = new String[11];

            try {
                conn = (estilosreporte.userConn != null) ? estilosreporte.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();

// se obtienen los datos generales del empleado.
                while (rs.next()) {

                    contentRow1 = sheet.createRow(filanombres);
                    contentRow2 = sheet.createRow(filanombres + 1);
                    datos[0] = rs.getString("empleadoId");
                    datos[1] = rs.getString("nombre");
                    datos[2] = rs.getString("depto");
                    datos[3] = rs.getString("puesto");
//                row = sheet.createRow(fila);
//se crean los encabezados para los datos de los empleados con incidencias 
                    headerRow = sheet.createRow(filanombres - 1);
                    for (int i = 0; i < TITULOS.size(); i++) {
                        headerCell = headerRow.createCell(i);
                        headerCell.setCellStyle(headerStyle2);
                        headerCell.setCellValue(TITULOS.get(i));
                    }
                    //se agregan los datos de empleados a el archivo .xls y se les agregan estilos 
                    contentCell1 = contentRow1.createCell(0);
                    contentCell1.setCellValue(datos[0]);
                    contentCell2 = contentRow1.createCell(1);
                    contentCell2.setCellValue(datos[1]);
                    contentCell3 = contentRow1.createCell(2);
                    contentCell3.setCellValue(datos[2]);
                    contentCell4 = contentRow1.createCell(3);
                    contentCell4.setCellValue(datos[3]);
                    idempleado.add(datos[0]);
                    contentCell1.setCellStyle(oddRowStyle);
                    contentCell2.setCellStyle(oddRowStyle);
                    contentCell3.setCellStyle(oddRowStyle);
                    contentCell4.setCellStyle(oddRowStyle);
                    filanombres++;
                    //se crea un listado con los titulos de los registros de incidencias
                    List<String> registros = exportReporte.getTableregistros();
                    for (int i = 0; i < registros.size(); i++) {
                        contentCell5 = contentRow2.createCell(i);
                        contentCell5.setCellStyle(headerStyle);
                        contentCell5.setCellValue(registros.get(i));
                    }
                    filanombres = filanombres + 1;
// se obtienen los registros de las incidencias en el rango de fechas ingresado 
                    for (int dia = 0; dia < dias.size(); dia++) {
                        String fecha = dias.elementAt(dia);
                        String sql2 = "SELECT inc.actualizadoJA, inc.actualizadoRH, emp.empleadoId, emp.nombre, inc.fecha,  nomin.nombre AS nombreinc, inc.comentario, inc.horasExtra, inc.dia\n"
                                + "from incidencias inc\n"
                                + "INNER JOIN empleados emp on inc.empleadoId= emp.empleadoId\n"
                                + "INNER JOIN NomIncidencia nomin on  nomin.idNomIncidencia = inc.idNomIncidencia\n"
                                + "INNER JOIN semanas se on inc.idSemana= se.idSemana\n"
                                + " where inc.fecha='" + fecha + "' and inc.empleadoId='" + idemple + "' and inc.actualizadoRH='AUTORIZADO'";

                        try {
                            conn2 = (estilosreporte.userConn2 != null) ? estilosreporte.userConn2 : Conexion.getConnection();
                            stmt2 = conn2.prepareStatement(sql2);
                            rs2 = stmt2.executeQuery();

                            while (rs2.next()) {
                                for (int i = 0; i < 9; i++) {
                                    datosR[i] = "";
                                }
                                horas(fecha, idemple);
                                datosR[0] = rs2.getString("nombreinc");
                                datosR[1] = rs2.getString("comentario");
                                datosR[2] = rs2.getString("dia");
                                datosR[3] = rs2.getString("fecha");
                                datosR[7] = rs2.getString("horasExtra");

                                headerRoe = sheet.createRow(filanombres);
//se agregan los rehistros a el archivo .xsl
                                contentCell6 = headerRoe.createCell(0);
                                contentCell6.setCellValue(datosR[0]);
                                contentCell7 = headerRoe.createCell(1);
                                contentCell7.setCellValue(datosR[1]);
                                contentCell8 = headerRoe.createCell(2);
                                contentCell8.setCellValue(datosR[2]);
                                contentCell9 = headerRoe.createCell(3);
                                contentCell9.setCellValue(datosR[3]);
                                contentCell10 = headerRoe.createCell(4);
                                contentCell10.setCellValue(datosR[4]);
                                contentCell11 = headerRoe.createCell(5);
                                contentCell11.setCellValue(datosR[5]);
                                contentCell12 = headerRoe.createCell(6);
                                contentCell12.setCellValue(datosR[6]);
                                contentCell13 = headerRoe.createCell(7);
                                contentCell13.setCellValue(datosR[7]);
                                filanombres = filanombres + 1;
                                contentCell6.setCellStyle(oddRowStyle);
                                contentCell7.setCellStyle(evenRowStyle);
                                contentCell8.setCellStyle(oddRowStyle);
                                contentCell9.setCellStyle(evenRowStyle);
                                contentCell10.setCellStyle(oddRowStyle);
                                contentCell11.setCellStyle(evenRowStyle);
                                contentCell12.setCellStyle(oddRowStyle);
                                contentCell13.setCellStyle(evenRowStyle);
                            }
                        } catch (HeadlessException | SQLException e) {
                            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            Conexion.close(rs2);
                            Conexion.close(stmt2);
                            if (estilosreporte.userConn2 == null) {
                                Conexion.close(conn2);
                            }
                        }
                    }
                    filanombres = filanombres + 2;
                    //se hacer auto ajustables los titulos a el texto 
                    for (int i = 0; i < TITULOS.size(); sheet.autoSizeColumn(i++));

                }
                rowIndex = filanombres;

            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            } finally {
                Conexion.close(rs);
                Conexion.close(stmt);
                if (estilosreporte.userConn == null) {
                    Conexion.close(conn);
                }
            }

        }
//se obtienen los datos de quien el archivo asi coo fecha y hora 
        HSSFRow headerRow00 = sheet.createRow(rowIndex++);
        String dato = empleado + "   " + cargo;
        String da[] = new String[5];
        da[1] = "Realizado por";
        da[2] = "Fecha y Hora";
        da[3] = dato;
        da[4] = fecha();

        HSSFCell headerCell00 = null;
        for (int i = 1; i < 3; i++) {
            headerCell00 = headerRow00.createCell(i);
            headerCell00.setCellStyle(headerStyle);
            headerCell00.setCellValue(da[i]);
        }
        HSSFRow headerRow000 = sheet.createRow(rowIndex++);
        HSSFCell headerCell000 = null;
        for (int i = 1; i < 3; i++) {

            headerCell000 = headerRow000.createCell(i);
            headerCell000.setCellStyle(oddRowStyle);
            headerCell000.setCellValue(da[i + 2]);
        }

        return workbook;
    }

    private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {

        HSSFFont font = workbook.createFont();
        font.setBold(fontBold);
        font.setColor(fontColor);
        font.setFontName("Arial");
        font.setFontHeightInPoints(fontHeight);

        return font;
    }

    private HSSFCellStyle createStyle(HSSFFont font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(cellAlign);
        style.setFillForegroundColor(cellColor);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        if (cellBorder) {
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            style.setTopBorderColor(cellBorderColor);
            style.setLeftBorderColor(cellBorderColor);
            style.setRightBorderColor(cellBorderColor);
            style.setBottomBorderColor(cellBorderColor);
        }

        return style;

    }

    protected void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress) {
        RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
    }

    public String fecha() {

        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");

        String fecha = date.format(now) + "      " + hour.format(now);
        return fecha;
    }

    public void listarid(Vector<String> dias, String nomdep) {
        for (int dia = 0; dia < dias.size(); dia++) {
            String sql;
            String fecharepo = dias.elementAt(dia);

            if (nomdep.contains("-SELECCIONE UNA OPCION-")) {

                sql = "SELECT DISTINCT  emp.empleadoId, emp.nombre, emp.depto, emp.puesto\n"
                        + "                    from incidencias inc\n"
                        + "                    INNER JOIN empleados emp on inc.empleadoId= emp.empleadoId\n"
                        + "                    INNER JOIN semanas se on inc.idSemana= se.idSemana\n"
                        + "                    where inc.fecha='" + fecharepo + "'";
            } else {
                sql = "SELECT DISTINCT  emp.empleadoId, emp.nombre, emp.depto, emp.puesto\n"
                        + "                    from incidencias inc\n"
                        + "                    INNER JOIN empleados emp on inc.empleadoId= emp.empleadoId\n"
                        + "                    INNER JOIN semanas se on inc.idSemana= se.idSemana\n"
                        + "                    where inc.fecha='" + fecharepo + "' and emp.depto='" + nomdep + "' ";

            }
            String datos[] = new String[11];

            try {
                conn5 = (estilosreporte.userConn5 != null) ? estilosreporte.userConn5 : Conexion.getConnection();
                stmt5 = conn5.prepareStatement(sql);
                rs5 = stmt5.executeQuery();
                while (rs5.next()) {
                    boolean boolean1 = false;
                    boolean boolean2 = false;
                    datos[0] = rs5.getString("empleadoId");
                    if (listadoempe.isEmpty()) {
                        listadoempe.add(datos[0]);
                    }
                    for (int i = 0; i < listadoempe.size(); i++) {
                        boolean1 = listadoempe.elementAt(i).equalsIgnoreCase(datos[0]);
                        if (boolean1) {
                            boolean2 = true;
                        }
                    }
                    if (boolean2) {
                    } else {
                        listadoempe.add(datos[0]);
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            } finally {
                Conexion.close(rs5);
                Conexion.close(stmt5);
                if (estilosreporte.userConn5 == null) {
                    Conexion.close(conn5);
                }
            }

        }
    }

    public void horas(String fecharepo, String idemple) {

        String sql;
        sql = "SELECT * from registros where empleadoId='" + idemple + "' and fecha='" + fecharepo + "'";

        try {
            conn3 = (estilosreporte.userConn3 != null) ? estilosreporte.userConn3 : Conexion.getConnection();
            stmt3 = conn3.prepareStatement(sql);
            rs3 = stmt3.executeQuery();
            while (rs3.next()) {
                datosR[4] = rs3.getString("entrada");
                datosR[5] = rs3.getString("salida");
                datosR[6] = rs3.getString("horas");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos\n" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexion.close(rs3);
            Conexion.close(stmt3);
            if (estilosreporte.userConn3 == null) {
                Conexion.close(conn3);
            }
        }

    }

}
