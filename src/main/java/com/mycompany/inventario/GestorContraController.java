/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.GestorContra;
import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.usuario;
import com.mycompany.inventario.clases.alertas;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class GestorContraController implements Initializable {

    @FXML
    private Button verificarContra;
    @FXML
    private ComboBox<String> cbmUsuario;
    @FXML
    private PasswordField txtContraA;
    @FXML
    private Button btnOlvidar;
    @FXML
    private PasswordField txtContraN;
    @FXML
    private PasswordField txtRepetir;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    
    GestorContra g = new GestorContra();
    usuario u = new usuario();
    alertas alert = new alertas();
    Login l = new Login();
    
    private boolean comprobacion;
    
    ObservableList<usuario> listaUsuario;
    @FXML
    private Text textNombre;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        txtContraN.setDisable(true);
        txtRepetir.setDisable(true);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        
        cargarUsuario();
        
        txtContraA.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                
                verificarContra();
                
                if(comprobacion){
                    
                    txtContraN.setDisable(false);
                    txtRepetir.setDisable(false);
                    btnGuardar.setDisable(false);
                    btnCancelar.setDisable(false);

                    txtContraA.setDisable(true);
                    btnOlvidar.setDisable(true);
                    cbmUsuario.setDisable(true);
                    
                    textNombre.setText(g.getSelecUsuario());
                    
                }
                else{
                    
                    alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Contraseña incorrecta");
                    
                }
                
            }
        });
        
    }    
    
    private void cargarUsuario() {
        
        listaUsuario = FXCollections.observableArrayList(u.consulta());
        for (usuario object : listaUsuario) {
            
            cbmUsuario.getItems().add(object.getNombre());
        
        }

    }
    
    private int buscarUsuario() {
        
        for (usuario object : listaUsuario) {
            
            if (object.getNombre().contains(cbmUsuario.getSelectionModel().getSelectedItem())) {
                
                return object.getId();                
                
            }
            
        }  
        
        return 0;
        
    }

    private void verificarContra() {
        g.setSelecUsuario(cbmUsuario.getSelectionModel().getSelectedItem());
        String contraActual = txtContraA.getText();

        boolean esCorrecta = g.verificarContraSeleccionada(contraActual);

        if (esCorrecta) {

            alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Contraseña correcta.");
            comprobacion = true;

        } else {
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "Contraseña incorrecta.");
            comprobacion = false;
            txtContraA.clear();

        }
    }
    
    @FXML
    private void Olvidar(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Recuperar Contraseña");
        dialog.setHeaderText("Introduzca el código de recuperación");
        dialog.setContentText("Código:");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/com/mycompany/inventario/logo_e_corner.png"));

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(codigoIngresado -> {
            if (g.verificarCodAdmi(codigoIngresado)) {
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Código Correcto", "Puede proceder a cambiar su contraseña.");
                txtContraN.setDisable(false);
                txtRepetir.setDisable(false);
                btnGuardar.setDisable(false);
                btnCancelar.setDisable(false);
                
                cbmUsuario.setDisable(true);
                cbmUsuario.getSelectionModel().clearSelection();
                cbmUsuario.setValue(null);
                txtContraA.setDisable(true);
                btnOlvidar.setDisable(true);
                textNombre.setText(l.getUsuarioActual());
                
            } else {
                alert.ShowAlert(Alert.AlertType.ERROR, "Código Incorrecto", "El código de recuperación ingresado no es válido.");
                cbmUsuario.setDisable(false);
                txtContraA.setDisable(false);
            }
        });
    }


    @FXML
    private void Guardar(ActionEvent event) {
        
        String contraNueva = txtContraN.getText();
        String Repetida = txtRepetir.getText();
        
        if(contraNueva.equals(Repetida)){
            
            g.setContraN(contraNueva);
            g.setSelecUsuario(textNombre.getText());
            
            if(g.Modificar()){
                
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Modificado correctamente");
                Stage stage = (Stage) btnGuardar.getScene().getWindow();
                // Cerrar la ventana
                stage.close();
                
                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido modificado correctamente");

                }
            
        }
        else{
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden");
            txtRepetir.clear();
            
        }
        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        
        txtContraN.setDisable(true);
        txtRepetir.setDisable(true);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        
        cbmUsuario.setDisable(false);
        txtContraA.setDisable(false);
        btnOlvidar.setDisable(false);
        
        txtContraN.clear();
        txtRepetir.clear();
        txtContraA.clear();
        textNombre.setText("");
        cbmUsuario.getSelectionModel().clearSelection();
        
    }
    
}
