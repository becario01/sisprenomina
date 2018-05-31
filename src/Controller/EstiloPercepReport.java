/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Programacion 04
 */
public class EstiloPercepReport {

    static Connection conn;
    static PreparedStatement stmt;
    public static ResultSet rs;
    private static Connection userConn;
    // Excel work book
    private HSSFWorkbook workbook;

    // Fonts
    private HSSFFont headerFont;
    private HSSFFont headerFont1;
    private HSSFFont headerFont2;
    private HSSFFont contentFont;
    private HSSFFont contentFont2;

    // Styles
    private HSSFCellStyle headerStyle1;
    private HSSFCellStyle headerStyle;
    private HSSFCellStyle headerStyle2;
    private HSSFCellStyle contentStyle2;
    private HSSFCellStyle oddRowStyle;
    private HSSFCellStyle evenRowStyle;

    // Integer to store the index of the next row
    private int rowIndex;

    /**
     * Make a new excel workbook with sheet that contains a stylized table
     *
     * @return
     */
    public HSSFWorkbook generateExcel(String inicio, String fin, String empleado, String cargo, String nomdep, Vector<String> dias) {

        // Initialize rowIndex
        rowIndex = 0;

        // New Workbook
        workbook = new HSSFWorkbook();

        // Generate fonts
        headerFont = createFont(HSSFColor.WHITE.index, (short) 12, true);
        headerFont1 = createFont(HSSFColor.WHITE.index, (short) 17, true);
        headerFont2 = createFont(HSSFColor.WHITE.index, (short) 12, true);
        contentFont = createFont(HSSFColor.BLACK.index, (short) 11, false);
        contentFont2 = createFont(HSSFColor.BLACK.index, (short) 11, false);

        // Generate styles
        headerStyle1 = createStyle(headerFont1, HSSFCellStyle.ALIGN_CENTER, HSSFColor.BLUE.index, false, HSSFColor.BLACK.index);
        headerStyle2 = createStyle(headerFont2, HSSFCellStyle.ALIGN_CENTER, HSSFColor.BLUE.index, false, HSSFColor.BLACK.index);
        contentStyle2 = createStyle(contentFont2, HSSFCellStyle.ALIGN_LEFT, HSSFColor.WHITE.index, false, HSSFColor.BLACK.index);
        headerStyle = createStyle(headerFont, HSSFCellStyle.ALIGN_CENTER, HSSFColor.LIGHT_BLUE.index, false, HSSFColor.DARK_BLUE.index);
        oddRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT, HSSFColor.WHITE.index, false, HSSFColor.WHITE.index);
        evenRowStyle = createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT, HSSFColor.LIGHT_TURQUOISE.index, false, HSSFColor.GREY_25_PERCENT.index);

        // New sheet
        HSSFSheet sheet = workbook.createSheet("PERCEPCIONES Y DEDUCCIONES");
        HSSFRow headerRow1 = sheet.createRow(rowIndex++);
        
        HSSFCell headerCell1 = null;

        if (nomdep.contains("-SELECCIONE UNA OPCION-")) {
            headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellStyle(headerStyle1);
            headerCell1.setCellValue("PERCEPCIONES Y DEDUCCIONES        " + inicio + "/" + fin);
        } else {
            headerCell1 = headerRow1.createCell(0);
            headerCell1.setCellStyle(headerStyle1);
            headerCell1.setCellValue("PERCEPCIONES Y DEDUCCIONES        " + inicio + "/" + fin + "           " + nomdep);
        }

        CellRangeAddress re = new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(re);

    

        // Table content
        HSSFRow contentRow = null;
        HSSFCell contentCell = null;

        // Obtain table content values
        List<List<String>> contentRowValues = PercepcionesReport.getContentnombres(nomdep, dias);
        for (List<String> rowValues : contentRowValues) {
                // Table header
        HSSFRow headerRow = sheet.createRow(rowIndex++);
        List<String> headerValues = PercepcionesReport.getHeadersnom();

        HSSFCell headerCell = null;
        for (int i = 0; i < headerValues.size(); i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellStyle(headerStyle2);
            headerCell.setCellValue(headerValues.get(i));
        }
            // At each row creation, rowIndex must grow one unit
            contentRow = sheet.createRow(rowIndex++);
            for (int i = 0; i < rowValues.size(); i++) {
                contentCell = contentRow.createCell(i);
                contentCell.setCellValue(rowValues.get(i));
                // Style depends on if row is odd or even
                contentCell.setCellStyle(contentStyle2);
            }
              HSSFRow headerRow4 = sheet.createRow(rowIndex++);
        List<String> headerValues4 = PercepcionesReport.getHeaders();

        HSSFCell headerCell4 = null;
        for (int i = 0; i < headerValues4.size(); i++) {
            headerCell4 = headerRow4.createCell(i);
            headerCell4.setCellStyle(headerStyle);
            headerCell4.setCellValue(headerValues4.get(i));
        }
        // Autosize columns
        for (int i = 0; i < headerValues4.size(); sheet.autoSizeColumn(i++));
 
            String idemp=rowValues.get(0);
            for (int dia = 0; dia < dias.size(); dia++) {
                String fecha=dias.elementAt(dia);
                List<List<String>> contentRowValues2 = PercepcionesReport.getContent(idemp, fecha);
                for (List<String> rowValues2 : contentRowValues2) {
                    // At each row creation, rowIndex must grow one unit
                    contentRow = sheet.createRow(rowIndex++);
                    for (int i = 0; i < rowValues2.size(); i++) {
                        contentCell = contentRow.createCell(i);
                        contentCell.setCellValue(rowValues2.get(i));
                        // Style depends on if row is odd or even
                        contentCell.setCellStyle(rowIndex % 2 == 0 ? oddRowStyle : evenRowStyle);
                    }
                }
            }
// Autosize columns
        for (int i = 0; i < headerValues.size(); sheet.autoSizeColumn(i++));
        }
        HSSFRow headerRow00 = sheet.createRow(rowIndex++);
        String datos = empleado + "   " + cargo;
        String da[] = new String[5];
        da[1] = "Realizado por";
        da[2] = "Fecha y Hora";
        da[3] = datos;
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

    /**
     * Create a new font on base workbook
     *
     * @param fontColor Font color (see {@link HSSFColor})
     * @param fontHeight Font height in points
     * @param fontBold Font is boldweight (<code>true</code>) or not
     * (<code>false</code>)
     *
     * @return New cell style
     */
    private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {

        HSSFFont font = workbook.createFont();
        font.setBold(fontBold);
        font.setColor(fontColor);
        font.setFontName("Arial");
        font.setFontHeightInPoints(fontHeight);

        return font;
    }

    /**
     * Create a style on base workbook
     *
     * @param font Font used by the style
     * @param cellAlign Cell alignment for contained text (see
     * {@link HSSFCellStyle})
     * @param cellColor Cell background color (see {@link HSSFColor})
     * @param cellBorder Cell has border (<code>true</code>) or not
     * (<code>false</code>)
     * @param cellBorderColor Cell border color (see {@link HSSFColor})
     *
     * @return New cell style
     */
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
    public String fecha() {

        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
        String fecha = date.format(now) + "      " + hour.format(now);
        return fecha;
    }

}
