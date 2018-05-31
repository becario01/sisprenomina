/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexion.Conexion;
import static View.RH_ListadoPersonal.rs;
//import static View.RH_UsuariosConIncidencias.rs;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import jxl.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Programacion 2
 */
public class exportReporte {

    public static Connection conn;
    public static Connection conn1;
    public static Connection conn2;
    public static PreparedStatement stmt;
    public static PreparedStatement stmt2;
    public static ResultSet rs;
    public static ResultSet rs2;
    public static PreparedStatement stmt1;
    public static ResultSet rs1;
    private static Connection userConn;
    private static Connection userConn2;
    private static Connection userConn1;
    boolean dom = false;
    boolean lu = false;
    boolean ma = false;
    boolean mi = false;
    boolean ju = false;
    boolean vi = false;
    boolean sa = false;
    public static String fec[] = new String[7];
//se crean listado de encabezados de los empleados 
    public static List<String> getTableHeaders() {
        List<String> tableHeader = new ArrayList<String>();
        tableHeader.add("ID");
        tableHeader.add("NOMBRE");
        tableHeader.add("DEPARTAMENTO");
        tableHeader.add("PUESTO");
        tableHeader.add("");
        tableHeader.add("");
        tableHeader.add("");
        tableHeader.add("");
        return tableHeader;
    }
    //se crea listado de los encabezados de registos de incidencias 
    public static List<String> getTableregistros() {
        List<String> tableregistros = new ArrayList<String>();
        tableregistros.add("INCIDENCIA");
        tableregistros.add("COMENTARIO");
        tableregistros.add("DIA");
        tableregistros.add("FECHA");
        tableregistros.add("HEN   ");
        tableregistros.add("HS     ");
        tableregistros.add("HT     ");
        tableregistros.add("HEX");
        return tableregistros;
    }


}
