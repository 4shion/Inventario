/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.inventario.clases;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/com/mycompany/inventario/logo_e_corner.png"));
        alert.show();
        
    }
    
}
