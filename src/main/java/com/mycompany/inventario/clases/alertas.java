/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.inventario.clases;

import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class alertas {
    
    public alertas(){
    }
    
    public static void ShowAlert(Alert.AlertType Alerta, String titulo, String contenido) {
        
        Alert alert = new Alert(Alerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.show();
        
    }
    
}
