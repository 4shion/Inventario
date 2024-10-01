/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.campos.cliente;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class proveedor extends conexion implements sentencias {
    
    private int id;
    private String nombre;
    private String correo;
    private String telefono;

    public proveedor(int id, String nombre, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public proveedor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public boolean insertar() {
        
        String sql = "insert into proveedor values(null, ?, ?, ?)";
        
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
        
        ArrayList<proveedor> proveedores = new ArrayList<>();
        String sql = "select * from proveedor";
        
        try(

            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                int cod = rs.getInt("idProveedor");
                String nom = rs.getString("nombre");
                String co = rs.getString("correo");
                String te = rs.getString("telefono");
                proveedor c = new proveedor(cod, nom, co, te);
                proveedores.add(c);
                
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return proveedores;
        
    }

    @Override
    public boolean modificar() {
        
        String sql = "update proveedor set nombre = ?, correo = ?, telefono = ?" +
                "where idProveedor = ?";
        
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
        
        String sqlVerificarMateriales = "SELECT COUNT(*) FROM materiaPrima WHERE Proveedor_idProveedor = ?";
        String sqlEliminarMateriales = "DELETE FROM materiaPrima WHERE Proveedor_idProveedor = ?";
        String sqlEliminarProveedor = "DELETE FROM Proveedor WHERE idProveedor = ?";
            
        try (Connection con = getCon();
            PreparedStatement stmVerificar = con.prepareStatement(sqlVerificarMateriales);
            PreparedStatement stmEliminarMateriales = con.prepareStatement(sqlEliminarMateriales);
            PreparedStatement stmEliminarProveedor = con.prepareStatement(sqlEliminarProveedor)) {

           // Verificar si el proveedor tiene materiales asociados
           stmVerificar.setInt(1, this.id);
           ResultSet rs = stmVerificar.executeQuery();
           if (rs.next() && rs.getInt(1) > 0) {
               // Si hay materiales asociados, mostrar una alerta
               Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
               alertaConfirmacion.setHeaderText(null);
               Stage stage = (Stage) alertaConfirmacion.getDialogPane().getScene().getWindow();
               stage.getIcons().add(new Image("/com/mycompany/inventario/logo_e_corner.png"));
               alertaConfirmacion.setContentText("El proveedor tiene materiales asociados. Si eliminas el proveedor, también se eliminarán los materiales que provee. ¿Deseas continuar?");

               ButtonType btnSi = new ButtonType("Sí");
               ButtonType btnNo = new ButtonType("No");
               alertaConfirmacion.getButtonTypes().setAll(btnSi, btnNo);

               Optional<ButtonType> result = alertaConfirmacion.showAndWait();
               if (result.get() != btnSi) {
                   // Si el usuario selecciona "No", cancelar la operación
                   return false;
               }

               // Eliminar los materiales asociados
               stmEliminarMateriales.setInt(1, this.id);
               stmEliminarMateriales.executeUpdate();
           }

           // Eliminar el proveedor
           stmEliminarProveedor.setInt(1, this.id);
           stmEliminarProveedor.executeUpdate();

           return true;

       } catch (SQLException ex) {
           Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
           return false;
       }        
        
    }
    
    public void buscarDatosProveedor(String nombreProveedor) {
        String correo;
        int telf;
        String consulta = "SELECT correo, telefono FROM proveedor WHERE nombre = ?";

        try (PreparedStatement stmt = getCon().prepareStatement(consulta)) {
            stmt.setString(1, nombreProveedor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                correo = rs.getString("correo");
                telf = rs.getInt("telefono");
                this.setNombre(nombreProveedor);
                this.setCorreo(correo); 
                this.setTelefono(String.valueOf(telf));
            }
        } catch (SQLException ex) {
            Logger.getLogger(proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
