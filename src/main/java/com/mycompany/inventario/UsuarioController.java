/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.cliente;
import com.mycompany.inventario.campos.usuario;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.encriptacion;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author User
 */
public class UsuarioController implements Initializable {


    @FXML
    private TableView<usuario> table;
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
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TableColumn<usuario, Integer> columId;
    @FXML
    private TableColumn<usuario, String> columNombre;
    @FXML
    private TableColumn<usuario, String> columCorreo;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private CheckBox checkMateriales;
    @FXML
    private CheckBox checkCliente;
    @FXML
    private CheckBox checkPedido;
    @FXML
    private CheckBox checkFacturacion;
    @FXML
    private CheckBox checkProveedor;
    @FXML
    private CheckBox checkUsuarios;
    @FXML
    private ImageView engranaje;
    
    boolean modificar = false;
    
    ObservableList<usuario> listaFiltrada;
    ObservableList<usuario> listaUsuario;
    
    usuario one = new usuario();
    alertas alert = new alertas();
    @FXML
    private Pane configuracion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtCodigo.setDisable(true);
        
        checkCliente.setDisable(true);
        checkFacturacion.setDisable(true);
        checkMateriales.setDisable(true);
        checkPedido.setDisable(true);
        checkProveedor.setDisable(true);
        checkUsuarios.setDisable(true);
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        mostrardatos();
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
    private void swicthToProveedor(ActionEvent event) {
        
        try {
            App.setRoot("proveedor");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToUsuarios(ActionEvent event) {
        
        try {
            App.setRoot("usuario");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToHistorial(ActionEvent event) {
        
        try {
            App.setRoot("historial");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToMateriales(ActionEvent event) {
        
        try {
            App.setRoot("materia");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToCliente(ActionEvent event) {
        
        try {
            App.setRoot("cliente");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToPedido(ActionEvent event) {
        
        try {
            App.setRoot("pedido");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void Nuevo(ActionEvent event) {
        
        txtNombre.setDisable(false);
        txtCorreo.setDisable(false);
        txtCodigo.setDisable(false);
        
        checkCliente.setDisable(false);
        checkFacturacion.setDisable(false);
        checkMateriales.setDisable(false);
        checkPedido.setDisable(false);
        checkProveedor.setDisable(false);
        checkUsuarios.setDisable(false);
        
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        
        btnNuevo.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
    }

    @FXML
    private void Modificar(ActionEvent event) {
        
       txtNombre.setDisable(false);
       txtCorreo.setDisable(false);
       txtCodigo.setDisable(false);
       
       checkCliente.setDisable(false);
       checkFacturacion.setDisable(false);
       checkMateriales.setDisable(false);
       checkPedido.setDisable(false);
       checkProveedor.setDisable(false);
       checkUsuarios.setDisable(false);
        
       btnEliminar.setDisable(true);
       btnNuevo.setDisable(true);
       btnModificar.setDisable(true);
       btnLimpiar.setDisable(true);
       
       btnGuardar.setDisable(false);
       btnCancelar.setDisable(false);
       
       modificar = true;
        
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        btnNuevo.setDisable(false);
        
        Alert alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
        alerta1.setTitle("Aviso");
        alerta1.setHeaderText(null);
        alerta1.setContentText("¿Desea eliminar el registro seleccionado?");
        Optional<ButtonType> opcion = alerta1.showAndWait();
        
        if(opcion.get() == ButtonType.OK){
            
            one.setId(Integer.parseInt(txtID.getText()));

            if(one.eliminar()){

                    alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Eliminado correctamente");

                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido eliminar correctamente");

                }
        } 
        else{
            
            Cancelar(event);
            
        }
        
        txtCorreo.clear();
        txtNombre.clear();
        txtCodigo.clear();
        
        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        mostrardatos();
        
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        one.setNombre(txtNombre.getText());
        one.setCodigo(txtCodigo.getText());
        one.setCorreo(txtCorreo.getText());
        
        one.setPcliente(checkCliente.isSelected());
        one.setPfactura(checkFacturacion.isSelected());
        one.setPmateriales(checkMateriales.isSelected());
        one.setPpedido(checkPedido.isSelected());
        one.setPproveedor(checkProveedor.isSelected());
        one.setPusuarios(checkUsuarios.isSelected());
        
        
        if(modificar){
            
            one.setId(Integer.parseInt(txtID.getText()));

            
            if(one.modificar()){
                
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Modificado correctamente");
                
            }
            else{
                
                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido modificado correctamente");

                
            }
            
        }
        else{
        
            if(one.insertar()){

                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Insertado correctamente");

            }
            else{

                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");

            }
            
        }
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtCodigo.setDisable(true);
        
        checkCliente.setDisable(true);
        checkFacturacion.setDisable(true);
        checkMateriales.setDisable(true);
        checkPedido.setDisable(true);
        checkProveedor.setDisable(true);
        checkUsuarios.setDisable(true);
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        btnNuevo.setDisable(false);
        btnLimpiar.setDisable(false);

        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        
        modificar = false;
        
        mostrardatos();
        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        
        btnNuevo.setDisable(false);        
        btnLimpiar.setDisable(false);        
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtCodigo.setDisable(true);
        
        checkCliente.setDisable(true);
        checkFacturacion.setDisable(true);
        checkMateriales.setDisable(true);
        checkPedido.setDisable(true);
        checkProveedor.setDisable(true);
        checkUsuarios.setDisable(true);
        
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        
        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        txtBusqueda.clear();
        
        modificar = false;
        
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        
        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        txtBusqueda.clear();
        
    }

    @FXML
    private void Busqueda(ActionEvent event) {
        
       listaFiltrada = FXCollections.observableArrayList();
       String buscar = txtBusqueda.getText();
        if(buscar.isEmpty()){
            
            table.setItems(listaUsuario);
            
        }
        else{
            
            listaFiltrada.clear();
            for (usuario listasUsuarios : listaUsuario) {
                
                if(listasUsuarios.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listasUsuarios);
                    
                }
                
            }
            
            table.setItems(listaUsuario);
            
        }
        
    }

    private void swicthToAdmi(ActionEvent event) {
        
        try {
            App.setRoot("perfil");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void mostrardatos(){
        
    listaUsuario = FXCollections.observableArrayList(one.consulta());
    columId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    columCorreo.setCellValueFactory(new PropertyValueFactory<>("Correo"));
    table.setItems(listaUsuario);    
        
    }

    @FXML
    private void Click(MouseEvent event) {
        
        usuario one = table.getSelectionModel().getSelectedItem();
        txtNombre.setText(one.getNombre());
        txtCorreo.setText(one.getCorreo());
        txtID.setText(String.valueOf(one.getId()));
        
        checkCliente.setSelected(one.isPcliente());
        checkFacturacion.setSelected(one.isPfactura());
        checkMateriales.setSelected(one.isPmateriales());
        checkPedido.setSelected(one.isPpedido());
        checkProveedor.setSelected(one.isPproveedor());
        checkUsuarios.setSelected(one.isPusuarios());
        System.out.println(one.isPcliente());
        
        btnModificar.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtCodigo.setDisable(true);
        
        btnNuevo.setDisable(true);
        
    }


    @FXML
    private void Config(ActionEvent event) {

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), configuracion);
        slideIn.setFromX(800); 
        slideIn.setToX(0);

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), configuracion);
        slideOut.setFromX(0);
        slideOut.setToX(800);
        
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(350), engranaje);

        if (configuracion.isVisible()) {

            slideOut.setOnFinished(event1 -> configuracion.setVisible(false));
            slideOut.play();

            rotateTransition.setByAngle(60); 
            rotateTransition.setCycleCount(1); 
            rotateTransition.setAutoReverse(false); 

            rotateTransition.playFromStart();

        } else {

            configuracion.setVisible(true);
            slideIn.play();
            rotateTransition.setByAngle(-60); 
            rotateTransition.setCycleCount(1); 
            rotateTransition.setAutoReverse(false); 

            rotateTransition.playFromStart();

        } 
    }


}