/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.proveedor;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.reportes;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProveedorController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<proveedor> table;
    @FXML
    private TableColumn<proveedor, Integer> ColumID;
    @FXML
    private TableColumn<proveedor, String> ColumNombre;
    @FXML
    private TableColumn<proveedor, String> ColumCorreo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtId;
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
    private Pane configuracion;
    @FXML
    private Button configButton;
    
    proveedor one = new proveedor();
    
    private boolean modificar = false;
    
    ObservableList<proveedor> lista;
    ObservableList<proveedor> listaFiltrada;
    
    alertas alert = new alertas();
    @FXML
    private TableColumn<proveedor, String> ColumTelefono;
    @FXML
    private TextField txtTelefono;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtTelefono.setDisable(true);
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        mostrardatos();
        
    }    

    @FXML
    private void Busqueda(ActionEvent event) {
        
        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBusqueda.getText();
        if(buscar.isEmpty()){
            
            table.setItems(lista);
            
        }
        else{
            
            listaFiltrada.clear();
            for (proveedor listas : lista) {
                
                if(listas.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listas);
                    
                }
                
            }
            
            table.setItems(listaFiltrada);
            
        }
        
    }

    @FXML
    private void click(MouseEvent event) {
        
        proveedor one = table.getSelectionModel().getSelectedItem();
        txtNombre.setText(one.getNombre());
        txtCorreo.setText(one.getCorreo());
        txtTelefono.setText(one.getTelefono());
        txtId.setText(String.valueOf(one.getId()));
        
        btnModificar.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        
        btnNuevo.setDisable(true);
        
    }

    @FXML
    private void Nuevo(ActionEvent event) {
        
        txtNombre.setDisable(false);
        txtCorreo.setDisable(false);
        txtTelefono.setDisable(false);
        
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
       txtTelefono.setDisable(false);
        
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
            
            one.setId(Integer.parseInt(txtId.getText()));

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
        txtTelefono.clear();
        
        mostrardatos();
        
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        String telefono = txtTelefono.getText();
        if (!telefono.matches("\\d+")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "El número de teléfono solo puede contener números.");
            return;
        }
        
        one.setNombre(txtNombre.getText());
        one.setCorreo(txtCorreo.getText());
        one.setTelefono(txtTelefono.getText());
        
        if(modificar){
            
            one.setId(Integer.parseInt(txtId.getText()));

            
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
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        btnNuevo.setDisable(false);
        btnLimpiar.setDisable(false);
        
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        
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
        
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        
        txtBusqueda.clear();
        
        modificar = false;
        
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        
        txtBusqueda.clear();
        
    }
    
    public void mostrardatos(){
        
    lista = FXCollections.observableArrayList(one.consulta());
    ColumID.setCellValueFactory(new PropertyValueFactory<>("id"));
    ColumNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    ColumCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
    ColumTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    table.setItems(lista);   
        
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
    private void Reporte(ActionEvent event) {
        
        reportes r=new reportes();
        String ubicacion= "/reportes/proveedor.jasper";
        String titulo= "Informe de Proveedores";
        r.generarReporte(ubicacion, titulo);
        
    }
    
    private void mostrarPane() {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(configuracion);
        transition.setToX(0); 
        transition.setDuration(Duration.millis(500)); 
        transition.play();
    }
    
}
