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
public class factura extends conexion {
    
    private double subTotal;
    private double Total;
    private int IdPedido;
    private int Idcliente;
    private int numFactura;
    
    public factura() {
    }

    public factura(double subTotal, double Total, int Idcliente, int numFactura) {
        this.subTotal = subTotal;
        this.Total = Total;
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

    public int getIdPedido() {
        return IdPedido;
    }

    public void setIdPedido(int IdPedido) {
        this.IdPedido = IdPedido;
    }
    
        public boolean insertar() {
        
        String sql = "INSERT INTO factura (numFactura, subtotal, total, Pedido_idPedido, Cliente_Idcliente) VALUES (null, ?, ?, ?, ?)";

        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setDouble(1, this.subTotal);
            stm.setDouble(2, this.Total);
            stm.setInt(3, this.IdPedido);  
            stm.setInt(4, this.Idcliente);  
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Placeholder sin IdPedido
    public boolean insertarPlaceholder() {
        String sql = "INSERT INTO factura (numFactura, subtotal, total, Cliente_idCliente) VALUES (null, ?, ?, ?)";
        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Valores placeholders
            stm.setDouble(1, 0.0); // subTotal
            stm.setDouble(2, 0.0); // Total
            stm.setInt(3, 0); // IdCliente placeholder

            int affectedRows = stm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.numFactura = generatedKeys.getInt(1); // num factura generado
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(factura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean actualizar() {
        String sql = "UPDATE factura SET subTotal = ?, Total = ?, Idcliente = ? WHERE numFactura = ?";
        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setDouble(1, this.subTotal);
            stm.setDouble(2, this.Total);
            stm.setInt(3, this.Idcliente);
            stm.setInt(4, this.numFactura);

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(factura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}


