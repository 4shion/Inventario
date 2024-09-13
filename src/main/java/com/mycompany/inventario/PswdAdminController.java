/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Pswd;
import com.mycompany.inventario.clases.alertas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
    private int intentosFallidos = 0;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TxtContraAdmin.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                System.out.println(TxtContraAdmin.getText().isEmpty());
                if (TxtContraAdmin.getText().isEmpty()) {
                    alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Error. Debes ingresar una contraseña");
                } else {
                    verificarContra();
                    TxtContraAdmin.clear(); // Limpiar el campo solo si se ha ingresado una contraseña
                }
            }   
        });
        
    }

    @FXML
    private void verificarContra() {
        
        ps.setCod(TxtContraAdmin.getText());
        
        if (ps.verificar()) {
            
            main.abrirformularios("perfilAdmin.fxml", "Perfil de Administrador");
            TxtContraAdmin.getScene().getWindow().hide();
            
        } else {
            
            intentosFallidos++;
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "Error. Contraseña incorrecta.");

            if (intentosFallidos >= 4) {
                // Utiliza Platform.runLater para asegurarte de que la alerta se muestre en el siguiente ciclo de la aplicación
                javafx.application.Platform.runLater(() -> {
                    // Crea una alerta para indicar que se cerrará el programa
                    Alert alertaFinal = new Alert(Alert.AlertType.ERROR, "Demasiados intentos fallidos. Cerrando el programa.");
                    Stage stage = (Stage) alertaFinal.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/com/mycompany/inventario/logo_e_corner.png"));
                    alertaFinal.setTitle("Aviso");
                    alertaFinal.setHeaderText(null);

                    // Define la acción para cerrar el programa después de cerrar la alerta
                    alertaFinal.setOnHidden(evt -> System.exit(0));

                    // Muestra la alerta y espera a que el usuario la cierre
                    alertaFinal.showAndWait();
                });
                
                
            }
            
        }
        
    }
    
}