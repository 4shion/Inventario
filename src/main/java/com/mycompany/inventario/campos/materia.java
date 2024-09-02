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
    private double cantidad;
    private double cantidad_min;
    private int idProveedor;
    private String nombreproveedor;
    private String unidadMedida;

    public materia() {
    }

    public materia(int id, String nombre, double precio, double cantidad, double cantidad_min, String nombreproveedor, int idProveedor, String unidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.cantidad_min = cantidad_min;
        this.nombreproveedor = nombreproveedor;
        this.idProveedor = idProveedor;
        this.unidadMedida = unidadMedida;
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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidad_min() {
        return cantidad_min;
    }

    public void setCantidad_min(double cantidadMin) {
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

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
    @Override
    public boolean insertar() {
        String sql="insert into materiaPrima(idMaterial,nombre,precio,cantidad,cantidad_min,Proveedor_idProveedor,UnidadMedida) values(?,?,?,?,?,?,?)";
                try( 
                    Connection con=getCon();
                    PreparedStatement stm=con.prepareStatement(sql)){
                    stm.setInt(1,this.id);
                    stm.setString(2,this.nombre);
                    stm.setDouble(3,this.precio);
                    stm.setDouble(4,this.cantidad);
                    stm.setDouble(5,this.cantidad_min);
                    stm.setInt(6,this.idProveedor);
                    stm.setString(7,this.unidadMedida);
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
                double cant=rs.getInt("cantidad");
                double can_min=rs.getInt("cantidad_min");
                int Idpro=rs.getInt("Proveedor_idProveedor");
                String nomPro=rs.getString("nombrep");
                String uni=rs.getString("UnidadMedida");
                materia m=new materia(cod,nom,pre,cant,can_min,nomPro,Idpro,uni);
                materia.add(m);                     
            }
            } catch (SQLException ex) {
                
             Logger.getLogger(materia.class.getName()).log(Level.SEVERE, null, ex);
             
         }     
          return materia;
         
    }

    @Override
    public boolean modificar() {
        
        String sql="UPDATE materiaprima SET nombre=?,precio=?,cantidad=?,cantidad_min=?,Proveedor_idProveedor=?,UnidadMedida=? WHERE idMaterial=?"; 
        
        try( 
               Connection con=getCon();
               PreparedStatement stm=con.prepareStatement(sql)){
               stm.setString(1,this.nombre);
               stm.setDouble(2,this.precio);
               stm.setDouble(3,this.cantidad);
               stm.setDouble(4,this.cantidad_min);
               stm.setInt(5,this.idProveedor);
               stm.setString(6,this.unidadMedida);
               stm.setInt(7,this.id);
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
