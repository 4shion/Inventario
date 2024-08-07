/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.clases;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class reportes extends conexion {
    
    public reportes(){    
    }
    
    public void generarReporte(String ubicacion,String titulo){
       
          try{
            String reportPath = getClass().getResource(ubicacion).getPath();
            
            Map<String, Object> parameters = new HashMap<>();
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());
            
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

          }catch (JRException ex){
              
              Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
              
          }
          
    }
    
}
