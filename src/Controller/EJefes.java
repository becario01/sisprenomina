/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Component;
import java.sql.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Becarios
 */
public class EJefes extends DefaultTableCellRenderer {
 
    private int idSemana;
    private String Semana;
    private String fechaL;
    private String fechaM;
    private String fechaMi;
    private String fechaJ;
    private String fechaV;
    private String fechaS;
    private String fechaD;
    private String estatus;

    public EJefes() {
    }

    public EJefes(int idSemana, String Semana, String fechaL, String fechaM, String fechaMi, String fechaJ, String fechaV, String fechaS, String fechaD, String estatus) {
        this.idSemana = idSemana;
        this.Semana = Semana;
        this.fechaL = fechaL;
        this.fechaM = fechaM;
        this.fechaMi = fechaMi;
        this.fechaJ = fechaJ;
        this.fechaV = fechaV;
        this.fechaS = fechaS;
        this.fechaD = fechaD;
        this.estatus = estatus;
    }

  
    public int getIdSemana() {
        return idSemana;
    }

    public void setIdSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    public String getSemana() {
        return Semana;
    }

    public void setSemana(String Semana) {
        this.Semana = Semana;
    }

    public String getFechaL() {
        return fechaL;
    }

    public void setFechaL(String fechaL) {
        this.fechaL = fechaL;
    }

    public String getFechaM() {
        return fechaM;
    }

    public void setFechaM(String fechaM) {
        this.fechaM = fechaM;
    }

    public String getFechaMi() {
        return fechaMi;
    }

    public void setFechaMi(String fechaMi) {
        this.fechaMi = fechaMi;
    }

    public String getFechaJ() {
        return fechaJ;
    }

    public void setFechaJ(String fechaJ) {
        this.fechaJ = fechaJ;
    }

    public String getFechaV() {
        return fechaV;
    }

    public void setFechaV(String fechaV) {
        this.fechaV = fechaV;
    }

    public String getFechaS() {
        return fechaS;
    }

    public void setFechaS(String fechaS) {
        this.fechaS = fechaS;
    }

    public String getFechaD() {
        return fechaD;
    }

    public void setFechaD(String fechaD) {
        this.fechaD = fechaD;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

   

    @Override
    public String toString() {
        return  Semana;
    }
    
        
          @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JLabel) {
            JLabel lbl = (JLabel)value;
            lbl.setOpaque(true);
            lbl.setBackground(UIManager.getColor("Label.background"));
            return lbl;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    

    
    
    
    
   
    
}
