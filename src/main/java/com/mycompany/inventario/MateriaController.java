/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.campos.proveedor;
import com.mycompany.inventario.clases.reportes;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.permisos;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MateriaController extends App implements Initializable{

    @FXML
    private TableView<materia> table;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private ComboBox<String> cboSelProov;
    @FXML
    private TextField txtCantidad;
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
    private TableColumn<materia, Integer> colId;
    @FXML
    private TableColumn<materia, String> colNombre;
    @FXML
    private TableColumn<materia, Double> colPrecio;
    @FXML
    private TableColumn<materia, Integer> colCantidad;
    @FXML
    private TableColumn<materia, String> colProveedor;
    
    private BorderPane contentPane;
    
    materia m = new materia();
    proveedor p = new proveedor();
    Login login = new Login();
    permisos per = new permisos();
    
    ObservableList<materia> listaMateria;
    ObservableList<proveedor> listaProveedor;
    ObservableList<materia> listaFiltrada;

    @FXML
    private TableColumn<materia, Integer> colCantMin;
    
    boolean bandera = false;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtCamMín;
    
    alertas alert = new alertas();
    @FXML
    private Pane configuracion;
    
    @FXML
    private ImageView engranaje;
    @FXML
    private TextField TxtUniMed;
    @FXML
    private TableColumn<materia, String> ColumUni;
    
    MainController main = new MainController();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(per.Materiales(login.getUsuarioActual())){
            txtNombre.setDisable(true);
            txtPrecio.setDisable(true);
            txtCantidad.setDisable(true);
            cboSelProov.setDisable(true);
            txtCamMín.setDisable(true);
            TxtUniMed.setDisable(true);

            btnGuardar.setDisable(true);
            btnCancelar.setDisable(true);
            btnEliminar.setDisable(true);
            btnModificar.setDisable(true);

            mostrarDatos();
        }
        else{
            
            click.
            
        }
    }    
    
    public Tooltip TextButton(String s){
        
        Tooltip t = new Tooltip(s);
        return t;
        
    }

    @FXML
    private void Busqueda(ActionEvent event) {
        
       
       listaFiltrada = FXCollections.observableArrayList();
       String buscar = txtBusqueda.getText();
        if(buscar.isEmpty()){
            
            table.setItems(listaMateria);
            
        }
        else{
            
            listaFiltrada.clear();
            for (materia listasMateria : listaMateria) {
                
                if(listasMateria.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listasMateria);
                    
                }
                
            }
            
            table.setItems(listaFiltrada);
            
        }
        
    }

    @FXML
    private void Nuevo(ActionEvent event) {
        
        // hablitar los campos y botones
        txtNombre.setDisable(false);
        txtPrecio.setDisable(false);
        txtCantidad.setDisable(false);
        txtCamMín.setDisable(false);
        TxtUniMed.setDisable(false);
        
        cboSelProov.setDisable(false);
        
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        
        //se carga los prompt
        cboSelProov.setPromptText("Proveedor");
        
        //se carga los datos en los combos
        cargarProveedor();
        
    }

    @FXML
    private void Modificar(ActionEvent event) {
        
        txtNombre.setDisable(false);
        txtPrecio.setDisable(false);
        txtCantidad.setDisable(false);
        txtCamMín.setDisable(false);
        TxtUniMed.setDisable(false);
        
        cboSelProov.setDisable(false);
        
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        
        btnModificar.setDisable(true);
        btnNuevo.setDisable(true);
        btnEliminar.setDisable(true);
        btnLimpiar.setDisable(true);
        
        cargarProveedor();
        
        bandera = true;

        
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
            
            m.setId(Integer.parseInt(txtId.getText()));

            if(m.eliminar()){

                    alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Eliminado correctamente");

                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido eliminar correctamente");

                }
        } 
        else{
            
            Cancelar(event);
            
        }
        
        txtId.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        cboSelProov.getItems().clear();
        txtCamMín.clear();
        TxtUniMed.clear();
        
        mostrarDatos();
        
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        try{
            
            m.setNombre(txtNombre.getText());
            m.setPrecio(Double.parseDouble(txtPrecio.getText()));
            int pro = buscarProveedor();
            m.setIdProveedor(pro);
            double c = Double.parseDouble(txtCantidad.getText());
            double cm = Double.parseDouble(txtCamMín.getText());
            m.setUnidadMedida(TxtUniMed.getText());
            
            if(c <= cm){

                Alert alerta2 = new Alert(Alert.AlertType.CONFIRMATION);
                alerta2.setHeaderText(null);
                alerta2.setContentText("La Cantidad Mínima introducida es mayor o igual que la Cantidad Total. ¿Desea continuar?");
                ButtonType btnSi = new ButtonType("Sí");
                ButtonType btnNo = new ButtonType("No");
                alerta2.getButtonTypes().setAll(btnSi, btnNo);

                Optional<ButtonType> result = alerta2.showAndWait();
                if (result.get() == btnNo) {
                    return; // Detenemos la ejecución si el usuario elige No
                }

            }

            m.setCantidad(c);
            m.setCantidad_min(cm);




            if(bandera){//modificar

                m.setId(Integer.parseInt(txtId.getText()));

                if(m.modificar()){
                
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Modificado correctamente");
                
                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido modificado correctamente");

                }

            bandera = false; 

            }else{

                if(m.insertar()){

                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Insertado correctamente");

                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");

                }

            } 

            mostrarDatos();
            Cancelar(event);
            Limpiar(event);
        }
        catch (NumberFormatException e) {
            
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Error en el formato de número: " + e.getMessage());
            alerta.show();
            
        }

        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        
        //limpiar txt
        txtId.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCamMín.clear();
        TxtUniMed.clear();
        
        cboSelProov.getItems().clear();
          
           
        txtNombre.setDisable(true);
        txtPrecio.setDisable(true);
        txtCantidad.setDisable(true);
        cboSelProov.setDisable(true);
        txtCamMín.setDisable(true);
        TxtUniMed.setDisable(true);
        
        //deshabilitar btn
        btnGuardar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);
        
        //habilitar
        btnNuevo.setDisable(false);
        btnLimpiar.setDisable(false);
        
    }
    
    public void mostrarDatos(){
        
       listaMateria = FXCollections.observableArrayList(m.consulta());
       colId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
       colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
       colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
       colProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreproveedor"));
       colCantMin.setCellValueFactory(new PropertyValueFactory<>("cantidad_min"));
       ColumUni.setCellValueFactory(new PropertyValueFactory<>("unidadMedida"));
       table.setItems(listaMateria);
       
   }
    
    private void cargarProveedor() {
        
        listaProveedor = FXCollections.observableArrayList(p.consulta());
        for (proveedor object : listaProveedor) {
            
            cboSelProov.getItems().add(object.getNombre());
        
        }

    }
    
    private int buscarProveedor() {
        
        for (proveedor object : listaProveedor) {
            
            if (object.getNombre().contains(cboSelProov.getSelectionModel().getSelectedItem())) {
                
                return object.getId();                
                
            }
            
        }  
        
        return 0;
        
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        
        txtId.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCamMín.clear();
        txtBusqueda.clear();
        TxtUniMed.clear();
        
        cboSelProov.getItems().clear();
        
    }

    @FXML
    private void Click(MouseEvent event) {
        
        materia m = table.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(m.getId()));
        txtNombre.setText(m.getNombre());
        TxtUniMed.setText(m.getUnidadMedida());
        txtPrecio.setText(String.valueOf(m.getPrecio()));
        txtCantidad.setText(String.valueOf(m.getCantidad()));
        txtCamMín.setText(String.valueOf(m.getCantidad_min()));
        cboSelProov.setValue(m.getNombreproveedor());
        
        btnModificar.setDisable(false);
        btnEliminar.setDisable(false);
        btnCancelar.setDisable(false);
        
        txtNombre.setDisable(true);
        txtPrecio.setDisable(true);
        txtCantidad.setDisable(true);
        cboSelProov.setDisable(true);
        txtCamMín.setDisable(true);
        
        btnNuevo.setDisable(true);
        
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
    private void abrirGestorContra() {
    
        main.abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    @FXML
    private void abrirPerfilAdmin() {
    
        main.abrirformularios("perfilAdmin.fxml", "Perfil de Administrador");
    
    }

    @FXML
    private void Reporte(ActionEvent event) {
        reportes r = new reportes();
        String ubicacion = "/reportes/materia.jasper";
        String titulo = "Informe de Materiales";

        Map<String, Object> parametros = new HashMap<>();

        r.generarReporte(ubicacion, titulo, parametros);
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
    

    
}
