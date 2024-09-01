    package com.mycompany.inventario.clases;

    import java.io.InputStream;
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

        public void generarReporte(String ubicacion, String titulo, Map<String, Object> parametros) {
            try {
                InputStream reportStream = getClass().getResourceAsStream(ubicacion);
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parametros, getCon());

                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setTitle(titulo);
                viewer.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
