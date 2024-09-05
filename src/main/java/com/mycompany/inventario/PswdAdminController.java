/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Pswd;
import com.mycompany.inventario.clases.alertas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
/**
 * FXML Controller class
 *
 * @author Acer
 */
public class PswdAdminController implements Initializable {


    @FXML
    private PasswordField TxtContraAdmin;
    
    Pswd ps = new Pswd();
    alertas alert = new alertas();
    MainController main = new MainController();

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TxtContraAdmin.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                verificarContra();
            }
        });
        
    }

    @FXML
    private void verificarContra() {
        
        ps.setCod(TxtContraAdmin.getText());
        
        if(ps.verificar()){
            
            main.abrirformularios("perfilAdmin.fxml", "Perfil de Administrador");
            
            TxtContraAdmin.getScene().getWindow().hide();
            
        }
        else {
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "Error. Codigo incorrecto");
            TxtContraAdmin.clear();
            return;
            
        }
        
    }
    
}
