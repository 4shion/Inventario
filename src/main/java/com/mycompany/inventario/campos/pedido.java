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
import java.sql.Date;

/**
 *
 * @author User
 */
public class pedido extends conexion implements sentencias {
    
    private int idPedido;
    private String servicio;
    Date fechaActual = new Date(System.currentTimeMillis());
    private int totalPedido;
    private int idCliente;
    private int idMateria;
    private int Cant;
    private String nombreC;
    private String nombreM;
    private int CantTotal;

    public pedido(int idPedido, String servicio, int totalPedido, int idCliente, int idMateria, int Cant, String nombreC, String nombreM, int CantTotal) {
        this.idPedido = idPedido;
        this.servicio = servicio;
        this.totalPedido = totalPedido;
        this.idCliente = idCliente;
        this.idMateria = idMateria;
        this.Cant = Cant;
        this.nombreC = nombreC;
        this.nombreM = nombreM;
        this.CantTotal = CantTotal;
    }
    
    public pedido(){
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public int getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(int totalPedido) {
        this.totalPedido = totalPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getCant() {
        return Cant;
    }

    public void setCant(int Cant) {
        this.Cant = Cant;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getNombreM() {
        return nombreM;
    }

    public void setNombreM(String nombreM) {
        this.nombreM = nombreM;
    }

    public int getCantTotal() {
        return CantTotal;
    }

    public void setCantTotal(int CantTotal) {
        this.CantTotal = CantTotal;
    }

    @Override
    public boolean insertar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList consulta() {
        
        ArrayList<pedido> pedido=new ArrayList<>();
        
        String sql;
        sql = "SELECT `pedido`.*, `detallepedido`.*, `nombreC`.`nombre`, `nombreM`.`nombre`, `cantTotal`.`cantidad` "
                + "FROM `pedido` LEFT JOIN `detallepedido` ON `detallepedido`.`Pedido_idPedido` = `pedido`.`idPedido` "
                + "LEFT JOIN `cliente` ON `pedido`.`Cliente_idCliente` = `nombreC`.`idCliente` "
                + "LEFT JOIN `materiaprima` ON `detallepedido`.`materiaPrima_idMaterial` = `nombreM`.`idMaterial`";
        
        try( 
                Connection con=getCon();
                Statement stm=con.createStatement();
                ResultSet rs=stm.executeQuery(sql)){
                while(rs.next())
            {
                int cod=rs.getInt("idPedido");
                String ser=rs.getString("servicio");
                int to=rs.getInt("totalpedido");
                int idp=rs.getInt("Cliente_idCliente");
                int idm=rs.getInt("materiaPrima_idMaterial");
                int cant=rs.getInt("cantidad");
                String nomC=rs.getString("nombreC");
                String nomM=rs.getString("nombreM");
                int cantTo = rs.getInt("cantTotal");
                pedido p=new pedido(cod, ser, to, idp, idm, cant, nomC, nomM, cantTo);
                pedido.add(p);
            }
        } 
            catch (SQLException ex) {
                
             Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
             
         }     
          return pedido;
          
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
