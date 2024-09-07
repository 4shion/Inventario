package com.mycompany.inventario.campos;

import com.mycompany.inventario.PedidoController;
import com.mycompany.inventario.clases.conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.sql.PreparedStatement;

public class pedido extends conexion{
    
    private int idPedido;
    private String servicio;
    private Date fechaActual = new Date(System.currentTimeMillis());
    private double totalPedido;
    private int idCliente;
    private int idMateria;
    private double Cant;
    private String nombreC;
    private String nombreM;
    private double StockRestante;
    private double precio;
    private String unidad;
    private int[] listaMaterialesId;
    private double[] listaCant;
    private String[] listaMaterialesN;
    private String correo;
    private int telf;
    private int numFactura;
    
    public pedido(){
    }
    
    public pedido(int idPedido, String servicio, double totalPedido, int idCliente, int idMateria, double Cant, String nombreC, String nombreM, double StockRestante, double precio, String unidad) {
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

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
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

    public int[] getListaMaterialesId() {
        return listaMaterialesId;
    }

    public void setListaMaterialesId(int[] listaMaterialesId) {
        this.listaMaterialesId = listaMaterialesId;
    }

    public double[] getListaCant() {
        return listaCant;
    }

    public void setListaCant(double[] listaCant) {
        this.listaCant = listaCant;
    }

    public String[] getListaMaterialesN() {
        return listaMaterialesN;
    }

    public void setListaMaterialesN(String[] listaMaterialesN) {
        this.listaMaterialesN = listaMaterialesN;
    }

    public String getCorreo() {
        return correo;
    }

    public int getTelf() {
        return telf;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelf(int telf) {
        this.telf = telf;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
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
        
        obtenerIdClientePorNombre(nombreC);
        setListaMaterialesId(obtenerIdMaterial(listaMaterialesN));
        String sqlPedido = "INSERT INTO Pedido (idPedido, servicio, fecha, totalPedido, Cliente_idCliente) VALUES (null, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detallePedido (Pedido_idPedido, materiaPrima_idMaterial, cantidad) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE materiaPrima SET cantidad = cantidad - ? WHERE idMaterial = ?";


        // Conexión y transacciones
        try (Connection con = getCon()) {
            // Iniciar transacción
            con.setAutoCommit(false);

            // Insertar en la tabla Pedido
            try (PreparedStatement stmPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmDetalle = con.prepareStatement(sqlDetalle);
                 PreparedStatement stmUpdateStock = con.prepareStatement(sqlUpdateStock)) {

                // Preparar y ejecutar la inserción en Pedido
                stmPedido.setString(1, this.servicio);
                stmPedido.setDate(2, this.fechaActual);
                stmPedido.setDouble(3, this.totalPedido);
                stmPedido.setInt(4, this.idCliente);
                stmPedido.executeUpdate();

                // Obtener el ID generado para el nuevo Pedido
                ResultSet generatedKeys = stmPedido.getGeneratedKeys();
                if (generatedKeys.next()) {
                    
                    int idPedidoGenerado = generatedKeys.getInt(1);

                    // Insertar los detalles del pedido
                    for (int i = 0; i < listaMaterialesId.length; i++) {
                        stmDetalle.setInt(1, idPedidoGenerado);
                        stmDetalle.setInt(2, listaMaterialesId[i]);
                        stmDetalle.setDouble(3, listaCant[i]);
                        stmDetalle.executeUpdate();

                        // Actualizar el stock de cada material
                        stmUpdateStock.setDouble(1, listaCant[i]);
                        stmUpdateStock.setInt(2, listaMaterialesId[i]);
                        stmUpdateStock.executeUpdate();
                        
                    }
                    
                } 
                else {
                    throw new SQLException("No se pudo obtener el ID del pedido insertado.");
                }

                    // Confirmar transacción
                    con.commit();
                    return true;
            } catch (SQLException ex) {
                    // En caso de error, deshacer transacción
                con.rollback();
                Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } catch (SQLException ex) {
            
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
    }
    
    public void obtenerIdClientePorNombre(String nombreCliente) {
        
        String sql = "SELECT idCliente FROM cliente WHERE nombre = ?";
        
        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql)) {
            
            stm.setString(1, nombreCliente);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                this.setIdCliente(rs.getInt("idCliente"));
            } else {
                throw new SQLException("No se encontró un cliente con el nombre " + nombreCliente);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int[] obtenerIdMaterial(String[] listaM){
        
        String sql = "Select idMaterial from materiaPrima where nombre = ?";
        int[] listaMaterialesIds = new int[listaM.length]; // Inicializar el arreglo de IDs de materiales

        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql)){
            
            for (int i = 0; i < listaM.length; i++) {
                        stm.setString(1, listaM[i]);
                        ResultSet rs = stm.executeQuery();
                        
                        if (rs.next()){
                            listaMaterialesIds[i] = rs.getInt("IdMaterial");  
                        }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaMaterialesIds;
        
    }
    
    public double obtenerPrecioMaterial(String nombreMaterial) {
        double precio = 0.0;
        String query = "SELECT precio FROM materiaPrima WHERE nombre = ?";

        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nombreMaterial);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                precio = rs.getDouble("precio");
            }
        } catch (SQLException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al obtener precio del material", e);
        }

        return precio;
    }
    
    public double obtenerStockActual(String nombreMaterial) {
        
        double stockActual = 0.0;
        String query = "SELECT cantidad FROM materiaPrima WHERE nombre = ?";

        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nombreMaterial);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                stockActual = rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al obtener stock del material", e);
        }

        return stockActual;
    }
    
    public String obtenerUnidadMedida(String nombreMaterial) {
        
        String unidad = "";
        String query = "SELECT UnidadMedida FROM materiaPrima WHERE nombre = ?";

        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nombreMaterial);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                unidad = rs.getString("UnidadMedida");
            }
        } catch (SQLException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al obtener precio del material", e);
        }

        return unidad;
    }
    
    public void searchId(){
        String sql = "select max(idPedido) as idPedido from pedido";
        
        try(
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            if(rs.next()){
                
                int id = rs.getInt("idPedido");
                this.setIdPedido(id);
                
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
}