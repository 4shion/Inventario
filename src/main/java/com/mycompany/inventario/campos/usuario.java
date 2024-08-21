/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.sentencias;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class usuario extends conexion implements sentencias {
    
    private String nombre;
    private String codigo;
    private String Correo;
    private String codAdmi;
    private boolean pmateriales;
    private boolean ppedido;
    private boolean pproveedor;
    private boolean pcliente;
    private boolean pfactura;
    private boolean pusuarios;

    public usuario(String nombre, String codigo, String Correo, String codAdmi) {
        
        this.nombre = nombre;
        this.codigo = codigo;
        this.Correo = Correo;
        this.codAdmi = codAdmi ;
        
    }
    
    
    
    public usuario(){    
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public boolean isPmateriales() {
        return pmateriales;
    }

    public void setPmateriales(boolean pmateriales) {
        this.pmateriales = pmateriales;
    }

    public boolean isPpedido() {
        return ppedido;
    }

    public void setPpedido(boolean ppedido) {
        this.ppedido = ppedido;
    }

    public boolean isPproveedor() {
        return pproveedor;
    }

    public void setPproveedor(boolean pproveedor) {
        this.pproveedor = pproveedor;
    }

    public boolean isPcliente() {
        return pcliente;
    }

    public void setPcliente(boolean pcliente) {
        this.pcliente = pcliente;
    }

    public boolean isPfactura() {
        return pfactura;
    }

    public void setPfactura(boolean pfactura) {
        this.pfactura = pfactura;
    }

    public boolean isPusuarios() {
        return pusuarios;
    }

    public void setPusuarios(boolean pusuarios) {
        this.pusuarios = pusuarios;
    }

    @Override
    public boolean insertar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList consulta() {
        
        ArrayList<usuario> usuarios = new ArrayList<>();
        String sql = "select * from usuario";
        
        try(

            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                String codAdmi = rs.getString("codigoAdmin");
                String nom = rs.getString("nombre");
                String correo = rs.getString("correo");
                String contra = rs.getString("codigo");
                usuario u = new usuario(nom, contra, correo, codAdmi);
                usuarios.add(u);
                
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return usuarios;
        
    }

    @Override
    public boolean modificar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}