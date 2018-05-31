/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Becarios
 */
public class Eempleados {

    private int empleadoid;
    private String nombre;
    private int estatus;
    private String puesto;
    private String depto;

    public Eempleados( int empleadoid, String nombre, int estatus, String puesto, String depto) {
       
        this.empleadoid = empleadoid;
        this.nombre = nombre;
        this.estatus = estatus;
        this.puesto = puesto;
        this.depto = depto;
    }


    public int getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(int empleadoid) {
        this.empleadoid = empleadoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    @Override
    public String toString() {
        return  nombre;
    }
    
}
