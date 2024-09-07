package com.mycompany.inventario.clases;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class reportes extends conexion {

    public reportes() {
    }
    
    public void generarReporte(String ubicacion, String titulo) {

        try {
            // Ruta al archivo .jasper
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();
            // Agrega parámetros según sea necesario

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarFactura(String ubicacion, String titulo, int Nrofactura) {
        
        try {
            // Ruta al archivo .jasper (compilado)
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("factNumero", Nrofactura);
            //parameters.put("Nrofactura", nroFactura);
            //parameters.put("iva10", iva);

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, "Error al generar el informe: ", ex);
        }
        
    }
    
    // Método para guardar los datos de la factura
    private void guardarDatosFactura(int nroFactura, String nombreCliente, String correo, String telefono, double subtotal, double total) {
        
        String sql = "INSERT INTO factura (nroFactura, nombreCliente, correo, telefono, subtotal, total) "
                + "VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nombreCliente=?, correo=?, telefono=?, subtotal=?, total=?";
        
        try (PreparedStatement stmt = getCon().prepareStatement(sql)) {
            
            stmt.setInt(1, nroFactura);
            stmt.setString(2, nombreCliente);
            stmt.setString(3, correo);
            stmt.setString(4, telefono);
            stmt.setDouble(5, subtotal);
            stmt.setDouble(6, total);
            
            stmt.setString(7, nombreCliente); //actualizacion
            stmt.setString(8, correo);        
            stmt.setString(9, telefono);      
            stmt.setDouble(10, subtotal);     
            stmt.setDouble(11, total);        
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}