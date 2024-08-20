/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Administrador;
import com.mycompany.inventario.clases.alertas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginAdmiController implements Initializable {


    @FXML
    private TextField codAdmin;
    @FXML
    private TextField nombreAdmin;
    @FXML
    private TextField idAdmin;
    @FXML
    private TextField correoAdmin;
    
    Administrador admi = new Administrador();
    alertas alert = new alertas();
    
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    
    private MainController mainController;
    @FXML
    private PasswordField pswd;
    @FXML
    private PasswordField pswdVerificacion;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void switchToMain(ActionEvent event) {
        
        try {
            App.setRoot("main");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void Aceptar(ActionEvent event) {
        
        admi.setNombre(nombreAdmin.getText());
        admi.setCorreo(correoAdmin.getText());
        admi.setCodAdmi(codAdmin.getText());
        if(pswd.getText().equals(pswdVerificacion.getText())) {
            
            admi.setCod(pswdVerificacion.getText());
            
        }
        else{
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden");
            return;
            
        }
        
        if(admi.insertar()){

                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Administrador agregado correctamente");
                mainController.actualizarBotonSesion();
                
            }
            else{

                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido agregar correctamente al administrador");

            }
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // Cerrar la ventana
        stage.close();
        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // Cerrar la ventana
        stage.close();
        
    }
    
}
