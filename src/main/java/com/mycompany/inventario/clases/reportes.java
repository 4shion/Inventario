package com.mycompany.inventario.clases;

import java.sql.Connection;
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
    
    public JasperPrint generarReporte(String ubicacion, String titulo) {
        JasperPrint jasperPrint = null;

        try {
            // Ruta al archivo .jasper
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();

            // Agrega parámetros según sea necesario

            // Llenar el informe
            jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jasperPrint; // Devolver el objeto JasperPrint
    }

    public JasperPrint generarFactura(String ubicacion, String titulo, int Nrofactura) {
        
        JasperPrint jasperPrint = null; // Declarar fuera del bloque try para devolverlo al final

        // Intentar obtener la ruta del archivo de reporte
        String reportPath = getClass().getResource(ubicacion).getPath();

        try (Connection connection = getCon()) { // Abrir la conexión y cerrarla automáticamente después
            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("factNumero", Nrofactura);

            // Llenar el informe con los parámetros y la conexión
            jasperPrint = JasperFillManager.fillReport(reportPath, parameters, connection);

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException | SQLException ex) { // Capturar posibles excepciones
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, "Error al generar el informe: ", ex);
        }

        return jasperPrint; // Devolver el objeto JasperPrint
    }

    
}