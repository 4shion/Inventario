/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.campos.pedido;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.reportes;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PedidoController implements Initializable {

    @FXML
    private TextArea TxtServicio;
    @FXML
    private TableView<pedido> table;
    @FXML
    private TableColumn<pedido, String> ColumMaterial;
    @FXML
    private TableColumn<pedido, String> ColumCantidad;
    @FXML
    private TableColumn<pedido, String> ColumStock;
    @FXML
    private ComboBox<String> CbmMateriales;
    @FXML
    private Text txtCosto;
    @FXML
    private Button BtnFactura;
    @FXML
    private TextField TxtCant;
    @FXML
    private Button BtnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private TextField idPedido;

    @FXML
    private Pane configuracion;
    
    pedido p = new pedido();
    materia m = new materia();
    
    ObservableList<materia> listaMateriales;
    
    @FXML
    private ImageView engranaje;

    @FXML
    private TextField txtNomCliente;

    private TextField correoCliente;

    private TextField telfCliente;
    
    private conexion conexionDB = new conexion();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarMaterial();    
        
    }    

    @FXML
    private void Factura(ActionEvent event) {
        
        reportes report = new reportes();

        Map<String, Object> parametros = new HashMap<>();
        // parametros.put("nombreCliente", txtNomCliente.getText());

        report.generarReporte("/reportes/factura.jasper", "Factura", parametros);
        
    }

    @FXML
    private void Agregar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }

    @FXML
    private void Guardar(ActionEvent event) {
    }

    @FXML
    private void Limpiar(ActionEvent event) {
    }

    @FXML
    private void switchToMain(ActionEvent event) {
        try {
            App.setRoot("main");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToUsuarios(ActionEvent event) {
        try {
            App.setRoot("usuario");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToHistorial(ActionEvent event) {
        try {
            App.setRoot("historial");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToMateriales(ActionEvent event) {
        try {
            App.setRoot("materia");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToCliente(ActionEvent event) {
        try {
            App.setRoot("cliente");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToPedido(ActionEvent event) {
        try {
            App.setRoot("pedido");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
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
        } else {
            configuracion.setVisible(true);
            slideIn.play();
            rotateTransition.setByAngle(-60);
        }

        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.playFromStart();
        
    }
    
    private void cargarMaterial() {
        
        listaMateriales = FXCollections.observableArrayList(m.consulta());
        for (materia object : listaMateriales) {
            
            CbmMateriales.getItems().add(object.getNombre());
        
        }

    }
    
    private int buscarMaterial() {
        
        for (materia object : listaMateriales) {
            
            if (object.getNombre().contains(CbmMateriales.getSelectionModel().getSelectedItem())) {
                
                return object.getId();                
                
            }
            
        }  
        
        return 0;
        
    }
    
    private void buscarDatosCliente() {
        String nombreCliente = txtNomCliente.getText();
        String query = "SELECT correo, telefono FROM clientes WHERE nombre = ?";

        try (PreparedStatement stmt = conexionDB.getCon().prepareStatement(query)) {
            stmt.setString(1, nombreCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                correoCliente.setText(correo);
                telfCliente.setText(telefono);
            } else {
                correoCliente.setText("");
                telfCliente.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void swicthToProveedor(ActionEvent event) {
        try {
            App.setRoot("proveedor");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
