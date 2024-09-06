/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.PedidoController;
import static com.mycompany.inventario.clases.alertas.ShowAlert;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class cliente extends conexion implements sentencias{
    
    private int id;
    private String nombre;
    private String correo;
    private String telefono;

    public cliente(int id, String nombre, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public cliente(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    public cliente() {
        
        
        
   }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public boolean insertar() {
        
        
        String sql = "insert into cliente values(null, ?, ?, ?)";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            
            stm.setString(1, this.nombre);
            stm.setString(2, this.correo);
            stm.setString(3, this.telefono);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }

    @Override
    public ArrayList consulta() {
        
        ArrayList<cliente> clientes = new ArrayList<>();
        String sql = "select c.idCliente, c.nombre, c.correo, c.telefono from cliente c where nombre != 'Sin nombre'";
        
        try(
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                int cod = rs.getInt("idCliente");
                String nom = rs.getString("nombre");
                String co = rs.getString("correo");
                String te = rs.getString("telefono");
                cliente c = new cliente(cod, nom, co, te);
                clientes.add(c);
                
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return clientes;
        
    }

    @Override
    public boolean modificar() {
        
        String sql = "update cliente set nombre = ?, correo = ?, telefono = ?"
                + " where idCliente = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setString(1, this.nombre);
            stm.setString(2, this.correo);
            stm.setString(3, this.telefono);
            stm.setInt(4, this.id);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }

    @Override
    public boolean eliminar() {
        
        String sql = "delete from cliente where idCliente = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setInt(1, this.id);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }        
    }
    
    public void buscarDatosCliente(String nombreCliente) {
        String correo;
        int telf;
        String consulta = "SELECT correo, telefono FROM Cliente WHERE nombre = ?";

        try (PreparedStatement stmt = getCon().prepareStatement(consulta)) {
            stmt.setString(1, nombreCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                correo = rs.getString("correo");
                telf = rs.getInt("telefono");
                this.setNombre(nombreCliente);
                this.setCorreo(correo); 
                this.setTelefono(String.valueOf(telf));
            } else {
                ShowAlert(Alert.AlertType.ERROR, "Error", "Cliente no registrado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
