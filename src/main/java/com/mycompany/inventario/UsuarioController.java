/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
/**
 * FXML Controller class
 *
 * @author User
 */
public class UsuarioController implements Initializable {


    @FXML
    private TableView<?> table;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnLimpiar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void switchToMain(ActionEvent event) {
    }

    @FXML
    private void switchToMateriales(ActionEvent event) {
    }

    @FXML
    private void switchToCliente(ActionEvent event) {
    }

    @FXML
    private void switchToPedido(ActionEvent event) {
    }

    @FXML
    private void swicthToProveedor(ActionEvent event) {
    }

    @FXML
    private void switchToUsuarios(ActionEvent event) {
    }

    @FXML
    private void switchToHistorial(ActionEvent event) {
    }

    @FXML
    private void Nuevo(ActionEvent event) {
    }

    @FXML
    private void Modificar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }

    @FXML
    private void Guardar(ActionEvent event) {
    }

    @FXML
    private void Cancelar(ActionEvent event) {
    }

    @FXML
    private void Limpiar(ActionEvent event) {
    }

}
