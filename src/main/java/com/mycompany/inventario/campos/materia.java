/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class materia extends conexion implements sentencias {

    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private int cantidad_min;
    private int idProveedor;
    private String nombreproveedor;

    public materia() {
    }

    public materia(int id, String nombre, double precio, int cantidad, int cantidad_min, String nombreproveedor, int idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.cantidad_min = cantidad_min;
        this.nombreproveedor = nombreproveedor;
        this.idProveedor = idProveedor;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad_min() {
        return cantidad_min;
    }

    public void setCantidad_min(int cantidadMin) {
        this.cantidad_min = cantidadMin;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }
    
    @Override
    public boolean insertar() {
        String sql="insert into materiaPrima(idMaterial,nombre,precio,cantidad,Proveedor_idProveedor,cantidad_min) values(?,?,?,?,?,?)";
                try( 
                    Connection con=getCon();
                    PreparedStatement stm=con.prepareStatement(sql)){
                    stm.setInt(1,this.id);
                    stm.setString(2,this.nombre);
                    stm.setDouble(3,this.precio);
                    stm.setInt(4,this.cantidad);
                    stm.setInt(5,this.idProveedor);
                    stm.setInt(6,this.cantidad_min);
                    stm.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(materia.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }    
    }

    @Override
    public ArrayList consulta() {
        
        ArrayList<materia> materia=new ArrayList<>();
        
        String sql;
        sql = "SELECT `m`.*, `p`.`nombre` AS nombrep\n" +
                "FROM `materiaprima` AS `m` \n" +
                "LEFT JOIN `proveedor` AS `p` ON `m`.`Proveedor_idProveedor` = `p`.`idProveedor`";
        
        try( 
                Connection con=getCon();
                Statement stm=con.createStatement();
                ResultSet rs=stm.executeQuery(sql)){
                while(rs.next())
            {
                int cod=rs.getInt("idMaterial");
                String nom=rs.getString("nombre");
                double pre=rs.getDouble("precio");
                int cant=rs.getInt("cantidad");
                int Idpro=rs.getInt("Proveedor_idProveedor");
                int can_min=rs.getInt("cantidad_min");
                String nomPro=rs.getString("nombrep");
                materia m=new materia(cod,nom,pre,cant,can_min,nomPro,Idpro);
                materia.add(m);                     
            }
            } catch (SQLException ex) {
                
             Logger.getLogger(materia.class.getName()).log(Level.SEVERE, null, ex);
             
         }     
          return materia;
         
    }

    @Override
    public boolean modificar() {
        
        String sql="UPDATE materiaprima SET nombre=?,precio=?,cantidad=?,Proveedor_idProveedor=?,cantidad_min=? WHERE idMaterial=?"; 
        
        try( 
               Connection con=getCon();
               PreparedStatement stm=con.prepareStatement(sql)){
               stm.setString(1,this.nombre);
               stm.setDouble(2,this.precio);
               stm.setInt(3,this.cantidad);
               stm.setInt(4,this.idProveedor);
               stm.setInt(5,this.cantidad_min);
               stm.setInt(6,this.id);
               stm.executeUpdate();
               return true;
               
           } 
        catch (SQLException ex) {
            
               Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
               return false;
               
           }    
        
    }

    @Override
    public boolean eliminar() {
        
        String sql = "delete from materiaprima where idMaterial = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setInt(1, this.id);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(materia.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }
    
}
