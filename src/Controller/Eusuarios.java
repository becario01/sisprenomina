/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Becarios
 */
public class Eusuarios extends DefaultTableCellRenderer{
 
    private String nombre;
    private String usuario;
    private String psss;
    private int  tipousuario; 
    private int estatus;
    private String depto;
    private String fecha;

    public Eusuarios(String nombre, String usuario, String psss, int tipousuario, int estatus, String depto, String fecha) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.psss = psss;
        this.tipousuario = tipousuario;
        this.estatus = estatus;
        this.depto = depto;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPsss() {
        return psss;
    }

    public void setPsss(String psss) {
        this.psss = psss;
    }

    public int getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(int tipousuario) {
        this.tipousuario = tipousuario;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
