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

public class pedido extends conexion{
    
    private int idPedido;
    private String servicio;
    private Date fechaActual = new Date(System.currentTimeMillis());
    private int totalPedido;
    private int idCliente;
    private int idMateria;
    private double Cant;
    private String nombreC;
    private String nombreM;
    private double StockRestante;
    private double precio;
    private String unidad;

    public pedido(){
    }
    
    public pedido(int idPedido, String servicio, int totalPedido, int idCliente, int idMateria, double Cant, String nombreC, String nombreM, double StockRestante, double precio, String unidad) {
        this.idPedido = idPedido;
        this.servicio = servicio;
        this.totalPedido = totalPedido;
        this.idCliente = idCliente;
        this.idMateria = idMateria;
        this.Cant = Cant;
        this.nombreC = nombreC;
        this.nombreM = nombreM;
        this.StockRestante = StockRestante;
        this.precio = precio;
        this.unidad = unidad;
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

    public double getCant() {
        return Cant;
    }

    public void setCant(double Cant) {
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getStockRestante() {
        return StockRestante;
    }

    public void setStockRestante(double StockRestante) {
        this.StockRestante = StockRestante;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public ArrayList<pedido> consulta() {
        ArrayList<pedido> pedidos = new ArrayList<>();
        String sql = "SELECT pedido.*, detallepedido.*, cliente.nombre AS nombreC, materiaPrima.nombre AS nombreM, detallepedido.cantidad, materiaPrima.precio, materiaPrima.UnidadMedida AS uni "
                + "FROM pedido "
                + "LEFT JOIN detallepedido ON detallepedido.Pedido_idPedido = pedido.idPedido "
                + "LEFT JOIN cliente ON pedido.Cliente_idCliente = cliente.idCliente "
                + "LEFT JOIN materiaprima ON detallepedido.materiaPrima_idMaterial = materiaPrima.idMaterial";

        try (Connection con = getCon();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                int cod = rs.getInt("idPedido");
                String ser = rs.getString("servicio");
                int to = rs.getInt("totalpedido");
                int idp = rs.getInt("Cliente_idCliente");
                int idm = rs.getInt("materiaPrima_idMaterial");
                double cant = rs.getDouble("cantidad");
                String nomC = rs.getString("nombreC");
                String nomM = rs.getString("nombreM");
                int cantTo = rs.getInt("cantTotal");
                double precio = rs.getDouble("precio");
                String uni = rs.getString("UnidadMedida");

                pedido p = new pedido(cod, ser, to, idp, idm, cant, nomC, nomM, cantTo, precio, uni);
                pedidos.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pedidos;
    }
    
    // Método calculado para concatenar cantidad con la unidad
    public String getCantidadConUnidad() {
        return Cant + " " + unidad;
    }

    // Método calculado para concatenar stock restante con la unidad
    public String getStockRestanteConUnidad() {
        return StockRestante + " " + unidad;
    }
    
    public boolean insertar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}