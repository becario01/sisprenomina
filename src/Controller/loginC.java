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
public class loginC {
    
    
    
    private String user;
    private String password;

    public loginC(String usuer, String password) {
        this.user = usuer;
        this.password = password;
    }

    
    public String getUsuer() {
        return user;
    }

    public void setUsuer(String usuer) {
        this.user = usuer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "loginC{" + "usuer=" + user + ", password=" + password + '}';
    }
    
    
    
}
