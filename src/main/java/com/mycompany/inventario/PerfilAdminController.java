/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.PerfilAdmin;
import com.mycompany.inventario.clases.alertas;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PerfilAdminController implements Initializable {

    @FXML
    private TextField txtContra;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnModificar;
    @FXML
    private TextField TxtCod;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtCorreo;
    
    PerfilAdmin p = new PerfilAdmin();
    alertas alert = new alertas();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtNombre.setDisable(true);
        txtContra.setDisable(true);
        TxtCod.setDisable(true);
        txtCorreo.setDisable(true);
        
        btnGuardar.setDisable(true);
        
        mostrasdatos();
        
    }    

    @FXML
    private void Modificar(ActionEvent event) {
        
        txtNombre.setDisable(false);
        txtContra.setDisable(false);
        TxtCod.setDisable(false);
        txtCorreo.setDisable(false);
        
        btnModificar.setDisable(true);
        btnGuardar.setDisable(false);
        
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        p.setCodigo(txtContra.getText());
        p.setNombre(txtNombre.getText());
        p.setCorreo(txtCorreo.getText());
        p.setCodAdmi(TxtCod.getText());
        
        if(p.Modificar()){
                
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Modificado correctamente");
                
                Stage stage = (Stage) btnGuardar.getScene().getWindow();
                // Cerrar la ventana
                stage.close();
            }
            else{
                
                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido modificado correctamente");
                return;
                
            }
        
    }
    
    public void mostrasdatos(){
        
        ArrayList<PerfilAdmin> perfiles = p.Consulta();  
        PerfilAdmin perfil = perfiles.get(0);
        
        txtNombre.setText(perfil.getNombre());
        TxtCod.setText(perfil.getCodAdmi());
        txtCorreo.setText(perfil.getCorreo());
        
    }
    
}