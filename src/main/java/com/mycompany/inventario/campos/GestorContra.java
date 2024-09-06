/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.encriptacion;
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
public class GestorContra extends conexion {
    
    private String SelecUsuario; 
    private String ContraA;
    private String nom; 
    private String correo;
    private String CodAdmi;
    private String ContraN;
    private int idUsuario;
    
    public GestorContra(){
    }

    public GestorContra(String ContraA, String nom, String correo, String CodAdmi, int idUsuario) {
        this.ContraA = ContraA;
        this.nom = nom;
        this.correo = correo;
        this.CodAdmi = CodAdmi;
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodAdmi() {
        return CodAdmi;
    }

    public void setCodAdmi(String CodAdmi) {
        this.CodAdmi = CodAdmi;
    }
    
    public String getSelecUsuario() {
        return SelecUsuario;
    }

    public void setSelecUsuario(String SelecUsuario) {
        this.SelecUsuario = SelecUsuario;
    }

    public String getContraA() {
        return ContraA;
    }

    public void setContraA(String ContraA) {
        this.ContraA = ContraA;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContraN() {
        return ContraN;
    }

    public void setContraN(String ContraN) {
        this.ContraN = ContraN;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public boolean Modificar(){
        return true;
    }
    
    public boolean verificarContraSeleccionada(String contraIngresada) {
        String sqlUsu = "SELECT codigo FROM Usuario WHERE nombre = ?";
        String sqlAdmi = "SELECT codigo FROM Usuario WHERE codigoAdmin is NOT NULL";
        
        try (Connection con = getCon();
             PreparedStatement pstmtUsu = con.prepareStatement(sqlUsu);
             PreparedStatement pstmtAdmi = con.prepareStatement(sqlAdmi)) {

            pstmtUsu.setString(1, this.SelecUsuario);

            ResultSet rs = pstmtUsu.executeQuery();
            if (rs.next()) {
                String contraBD = rs.getString("codigo");
                if (encriptacion.verify(contraIngresada, contraBD)) {
                    return true;
                }
            }
            
            ResultSet rsAdmi = pstmtAdmi.executeQuery();
            if (rsAdmi.next()) {
                String codAdmiBD = rsAdmi.getString("codigo");
                if (encriptacion.verify(contraIngresada, codAdmiBD)) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestorContra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean verificarCodAdmi(String contraIngresada){
        
        String sqlAdmi = "SELECT codigoAdmin FROM Usuario WHERE codigoAdmin is NOT NULL";
        
        try (Connection con = getCon();
            PreparedStatement pstmtAdmi = con.prepareStatement(sqlAdmi)) {
            
            ResultSet rsAdmi = pstmtAdmi.executeQuery();

            if (rsAdmi.next()) {
                String codAdmiBD = rsAdmi.getString("codigoAdmin");
                
                if (codAdmiBD.equals(contraIngresada)) {
                    return true;
                }
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestorContra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;        
    }
    
}
