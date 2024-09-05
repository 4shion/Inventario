/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class factura extends conexion {
    
    private double subTotal;
    private double Total;
    private int IdPedido;
    private int Idcliente;
    private int numFactura;
    
    public factura(){
        
    }

    public factura(double subTotal, double Total, int IdPedido, int Idcliente, int numFactura) {
        this.subTotal = subTotal;
        this.Total = Total;
        this.IdPedido = IdPedido;
        this.Idcliente = Idcliente;
        this.numFactura = numFactura;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public int getIdPedido() {
        return IdPedido;
    }

    public void setIdPedido(int IdPedido) {
        this.IdPedido = IdPedido;
    }

    public int getIdcliente() {
        return Idcliente;
    }

    public void setIdcliente(int Idcliente) {
        this.Idcliente = Idcliente;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }
    
   
    public boolean insertar() {
        
        
        String sql = "insert into factura values(null, ?, ?, ?, ?)";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            
            stm.setDouble(1, this.subTotal);
            stm.setDouble(2, this.Total);
            stm.setInt(3, this.IdPedido);
            stm.setInt(4, this.Idcliente);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }
    
}
