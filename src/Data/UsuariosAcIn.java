/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Programacion 2
 */
public class UsuariosAcIn {
      
      
    private String EmployeeId;
    private String activo;
    private String Name;
    private String Depto;
    private String Puesto;
    private int id;
    
    
    public String getEmployeeId(){
          return EmployeeId;
    }
    
    public void setEmployeeId(String EmployeeId){
          this.EmployeeId=EmployeeId;
    }
    
    public String getactivo(){
          return activo;
    }
    
    public void setactivo(String activo){
          this.activo=activo;
    }
    
    public String getName(){
          return Name;
    }
    
    public void setName(String Name){
          this.Name=Name;
    }
    
    public String getDepto(){
          return Depto;
    }
    
    public void setDepto(String Depto){
          this.Depto=Depto;
    }
    
    
    public String getPuesto(){
          return Puesto;
    }
    
    public void setPuesto(String Puesto){
          this.Puesto=Puesto;
    }
    
    public int getid(){
          return id;
    }
    
    public void setid(int id){
          this.id=id;
    }
}
