/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.cliente;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.permisos;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ClienteController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
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
    private TextField txtBusqueda;
    @FXML
    private ImageView engranaje;
    
    private boolean modificar = false;
    
    cliente one = new cliente();
    
    ObservableList<cliente> lista;
    ObservableList<cliente> listaFiltrada;
    @FXML
    private TableColumn<cliente, Integer> ColumID;
    @FXML
    private TableColumn<cliente, String> ColumNombre;
    @FXML
    private TableColumn<cliente, String> ColumCorreo;
    @FXML
    private TableView<cliente> table;
    @FXML
    private TextField txtId;
    
    alertas alert = new alertas();
    @FXML
    private TextField txtTelefono;
    @FXML
    private TableColumn<cliente, String> ColumTelefono;
    @FXML
    private Pane configuracion;
    MainController m = new MainController();
    
    Login login = new Login();
    permisos per = new permisos();
    boolean permiso = false;
    String h = "Boton Inhabilitado";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        permiso = per.Clientes(login.getUsuarioActual());
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtTelefono.setDisable(true);
        
        if(permiso){

            btnGuardar.setDisable(true);
            btnCancelar.setDisable(true);
            btnEliminar.setDisable(true);
            btnModificar.setDisable(true);
            
        }
        else{
            
            btnGuardar.setDisable(false);
            btnCancelar.setDisable(false);
            btnEliminar.setDisable(false);
            btnModificar.setDisable(false);
            btnNuevo.setDisable(false);
            btnLimpiar.setDisable(false);
            
            btnNuevo.setTooltip(TextButton(h));
            btnCancelar.setTooltip(TextButton(h));
            btnEliminar.setTooltip(TextButton(h));
            btnGuardar.setTooltip(TextButton(h));
            btnModificar.setTooltip(TextButton(h));
            btnLimpiar.setTooltip(TextButton(h));
            
            btnGuardar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Guardar ha sido presionado.");
            });
            
            btnNuevo.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Nuevo ha sido presionado.");
            });
            
            btnEliminar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Eliminar ha sido presionado.");
            });
            
            btnCancelar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Cancelar ha sido presionado.");
            });
            
            btnModificar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Modificar ha sido presionado.");
            });
            
            btnLimpiar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Limpiar ha sido presionado.");
            });
            
        }
        
        mostrardatos();
    }
    
    public Tooltip TextButton(String s){

        Tooltip t = new Tooltip(s);
        return t;
        
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
        Stage stage = (Stage) alerta1.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/com/mycompany/inventario/logo_e_corner.png"));
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
        
        if (txtNombre.getText().isEmpty() || 
            txtCorreo.getText().isEmpty() || 
            txtTelefono.getText().isEmpty()) {

            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios");
            return;
        }
        if (txtNombre.getText().matches("\\d+")) { // Comprueba si el nombre solo contiene dígitos
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El nombre no puede contener solo números");
            return;
        }
        String telefono = txtTelefono.getText();
        if (!telefono.matches("\\d+")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "El número de teléfono solo puede contener números.");
            return;
        }
        if (!txtCorreo.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El formato del correo es incorrecto");
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
        txtTelefono.setDisable(true);
        
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
        txtTelefono.setDisable(true);
        
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
    private void click(MouseEvent event) {
        
        if (!permiso) {
            event.consume(); // Consume el evento para evitar cualquier otra acción
            return; // Salir del método
        }
        
        cliente one = table.getSelectionModel().getSelectedItem();
        if(one != null){
            txtNombre.setText(one.getNombre());
            txtCorreo.setText(one.getCorreo());
            txtId.setText(String.valueOf(one.getId()));
            txtTelefono.setText(one.getTelefono());

            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
            btnCancelar.setDisable(false);

            txtNombre.setDisable(true);
            txtCorreo.setDisable(true);
            txtTelefono.setDisable(true);

            btnNuevo.setDisable(true);
        }
        
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
    
    private void abrirGestorContra() {
    
        m.abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    private void abrirPerfilAdmin() {
    
        m.abrirformularios("pswdAdmin.fxml", "Ingrese su contraseña de Administrador");
    
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
            for (cliente listas : lista) {
                
                if(listas.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listas);
                    
                }
                
            }
            
            table.setItems(listaFiltrada);
            
        }
        
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

    @FXML
    private void bajarPDF(ActionEvent event) {
    }

    @FXML
    private void verificar(ActionEvent event) {
    }
    
}
