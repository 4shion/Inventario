/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Pswd extends conexion {
    
    public String cod;
    
    public Pswd(){
    }

    public Pswd(String cod) {
        this.cod = cod;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
    
    public boolean verificar(){
        
        String sql = "SELECT codigoAdmin FROM usuario WHERE codigoAdmin = ?";
    
        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, this.cod);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                
                return true;
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
        
    }
    
}
