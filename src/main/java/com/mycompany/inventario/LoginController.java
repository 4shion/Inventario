/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.clases.alertas;
import java.net.URL;
import java.util.ResourceBundle;
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
 * @author Acer
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContra;
    @FXML
    private Button btnIngresar;

    Login login = new Login();
    
    alertas alert = new alertas();
    
    private MainController mainController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    
    
    public void setMainController(MainController mainController) {
        
        this.mainController = mainController;
        
    }

    @FXML
    private void Ingresar(ActionEvent event) {
        
        login.setUsuario(txtUsuario.getText());
        login.setContra(txtContra.getText());
        
        if(login.verificar()){
                
            alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Sesión iniciada correctamente");
            mainController.iniciarSesion(); 
            
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            // Cerrar la ventana
            stage.close();
        }
        else {
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "Error. Usuario y/o Contraseña incorrectos");
            txtContra.clear();
            
        }
        
    }
    
}
