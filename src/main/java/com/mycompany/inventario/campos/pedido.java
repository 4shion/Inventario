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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class pedido extends conexion implements sentencias {
    
    private final IntegerProperty idPedido;
    private final StringProperty servicio;
    private final Date fechaActual = new Date(System.currentTimeMillis());
    private final IntegerProperty totalPedido;
    private final IntegerProperty idCliente;
    private final IntegerProperty idMateria;
    private final IntegerProperty Cant;
    private final StringProperty nombreC;
    private final StringProperty nombreM;
    private final IntegerProperty CantTotal;
    private final DoubleProperty precio;

    public pedido(int idPedido, String servicio, int totalPedido, int idCliente, int idMateria, int Cant, String nombreC, String nombreM, int CantTotal, double precio) {
        this.idPedido = new SimpleIntegerProperty(idPedido);
        this.servicio = new SimpleStringProperty(servicio);
        this.totalPedido = new SimpleIntegerProperty(totalPedido);
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.idMateria = new SimpleIntegerProperty(idMateria);
        this.Cant = new SimpleIntegerProperty(Cant);
        this.nombreC = new SimpleStringProperty(nombreC);
        this.nombreM = new SimpleStringProperty(nombreM);
        this.CantTotal = new SimpleIntegerProperty(CantTotal);
        this.precio = new SimpleDoubleProperty(precio);
    }

    public int getIdPedido() {
        return idPedido.get();
    }

    public void setIdPedido(int idPedido) {
        this.idPedido.set(idPedido);
    }

    public IntegerProperty idPedidoProperty() {
        return idPedido;
    }

    public String getServicio() {
        return servicio.get();
    }

    public void setServicio(String servicio) {
        this.servicio.set(servicio);
    }

    public StringProperty servicioProperty() {
        return servicio;
    }

    public int getTotalPedido() {
        return totalPedido.get();
    }

    public void setTotalPedido(int totalPedido) {
        this.totalPedido.set(totalPedido);
    }

    public IntegerProperty totalPedidoProperty() {
        return totalPedido;
    }

    public int getIdCliente() {
        return idCliente.get();
    }

    public void setIdCliente(int idCliente) {
        this.idCliente.set(idCliente);
    }

    public IntegerProperty idClienteProperty() {
        return idCliente;
    }

    public int getIdMateria() {
        return idMateria.get();
    }

    public void setIdMateria(int idMateria) {
        this.idMateria.set(idMateria);
    }

    public IntegerProperty idMateriaProperty() {
        return idMateria;
    }

    public int getCant() {
        return Cant.get();
    }

    public void setCant(int Cant) {
        this.Cant.set(Cant);
    }

    public IntegerProperty CantProperty() {
        return Cant;
    }

    public String getNombreC() {
        return nombreC.get();
    }

    public void setNombreC(String nombreC) {
        this.nombreC.set(nombreC);
    }

    public StringProperty nombreCProperty() {
        return nombreC;
    }

    public String getNombreM() {
        return nombreM.get();
    }

    public void setNombreM(String nombreM) {
        this.nombreM.set(nombreM);
    }

    public StringProperty nombreMProperty() {
        return nombreM;
    }

    public int getCantTotal() {
        return CantTotal.get();
    }

    public void setCantTotal(int CantTotal) {
        this.CantTotal.set(CantTotal);
    }

    public IntegerProperty CantTotalProperty() {
        return CantTotal;
    }

    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    // Métodos para la interfaz sentencias
    @Override
    public boolean insertar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<pedido> consulta() {
        ArrayList<pedido> pedidos = new ArrayList<>();
        String sql = "SELECT pedido.*, detallepedido.*, cliente.nombre AS nombreC, materiaPrima.nombre AS nombreM, detallepedido.cantidad, materiaPrima.precio "
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
                int cant = rs.getInt("cantidad");
                String nomC = rs.getString("nombreC");
                String nomM = rs.getString("nombreM");
                int cantTo = rs.getInt("cantTotal");
                double precio = rs.getDouble("precio");

                pedido p = new pedido(cod, ser, to, idp, idm, cant, nomC, nomM, cantTo, precio);
                pedidos.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pedidos;
    }

    @Override
    public boolean modificar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private final IntegerProperty stockRestante = new SimpleIntegerProperty();

    public int getStockRestante() {
        return stockRestante.get();
    }

    public void setStockRestante(int stockRestante) {
        this.stockRestante.set(stockRestante);
    }

    public IntegerProperty stockRestanteProperty() {
        return stockRestante;
    }
}
