/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

/**
 *
 * @author User
 */
public class Login {
    
    private String usuario;
    private String contra;

    public Login(String usuario, String contra) {
        this.usuario = usuario;
        this.contra = contra;
    }
    
    public Login(){
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
    
    public boolean verificar(){
        
        return true;
        
    }
    
}
