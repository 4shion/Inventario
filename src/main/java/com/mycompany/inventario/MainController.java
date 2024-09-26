/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.cliente;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.permisos;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainController extends conexion implements Initializable {

    @FXML
    private Button btnSesion;

    private boolean sesionIniciada = false;
    private boolean Admi = false;

    alertas alert = new alertas();
    Login login = new Login();
    permisos p = new permisos();

    private Stage ventanaEmergente = null; 
    @FXML
    private Button btnMateriales;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnPedidos;
    @FXML
    private Button btnProveedores;
    @FXML
    private Button btnUsuarios;
    
    private boolean pPro = false;
    private boolean pUsu = false;
    private boolean pMate = false;
    private boolean pCli = false;
    private boolean pPe = false;
    
    @FXML
    private Pane configuracion;
    
    @FXML
    private ImageView engranaje;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verificarUsuario();
    }

    public MainController() {
    }
    
    public Tooltip TextButton(String s){
        
        Tooltip t = new Tooltip(s);
        return t;
        
    }
    
    public void verificacionPermisos(){
        
        String usuarioActual = login.getUsuarioActual();

        // Verifica los permisos del usuario actual
        pPro = p.Proveedores(usuarioActual);
        pUsu = p.Usuarios(usuarioActual);
        pMate = p.Materiales(usuarioActual);
        pCli = p.Clientes(usuarioActual);
        pPe = p.Pedidos(usuarioActual);
        
        String USi = "Este usuario tiene permiso para modificar esta vista";
        String UNo = "Este usuario no tiene permiso para modificar esta vista";
        
        if(pPro){
            
            btnProveedores.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnProveedores.setTooltip(TextButton(UNo));
            
        }
        
        if(pUsu){
            
            btnUsuarios.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnUsuarios.setTooltip(TextButton(UNo));
            
        }
        
        if(pMate){
            
            btnMateriales.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnMateriales.setTooltip(TextButton(UNo));
            
        }
        
        if(pCli){
            
            btnClientes.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnClientes.setTooltip(TextButton(UNo));
            
        }
        
        if(pPe){
            
            btnPedidos.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnPedidos.setTooltip(TextButton(UNo));
            
        }
        
    }
    
    private void configurarTooltips() {
        // Configurar Tooltips iniciales
        btnMateriales.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnClientes.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnPedidos.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnProveedores.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnUsuarios.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
    }

    @FXML
    private void switchToMateriales(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("materia");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void switchToUsuarios(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("usuario");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void switchToCliente(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("cliente");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void switchToPedido(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("pedido");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void swicthToProveedor(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("proveedor");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void Sesion(ActionEvent event) {
        if (btnSesion.getText().equals("Registrarse")) {
            abrirformularios("loginAdmi.fxml", "Registro de Administrador");
        } else if (btnSesion.getText().equals("Iniciar Sesión")) {
            abrirformularios("login.fxml", "Iniciar Sesión");
        } else if (btnSesion.getText().equals("Cerrar Sesión")) {
            cerrarSesion();
        }
    }

    private void verificarUsuario() {
        String usuarioActual = Login.getUsuarioActual();

        if (usuarioActual != null) {
            btnSesion.setText("Cerrar Sesión");
            sesionIniciada = true;
            verificacionPermisos();
        } else {
            String sql = "SELECT COUNT(*) FROM usuario";

            try (Connection con = getCon();
                 PreparedStatement pstmt = con.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    int userCount = rs.getInt(1);

                    if (userCount > 0) {
                        btnSesion.setText("Iniciar Sesión");
                    } else {
                        btnSesion.setText("Registrarse");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (usuarioActual == null){
            
            configurarTooltips();
            
        }
         
    }

    public void abrirformularios(String fxml, String titulo) {
        try {
            // Cierra la ventana emergente actual si existe
            if (ventanaEmergente != null) {
                ventanaEmergente.close();
                ventanaEmergente = null;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            if (fxml.equals("loginAdmi.fxml")) {
                LoginAdmiController controller = loader.getController();
                controller.setMainController(this);
            } else if (fxml.equals("login.fxml")) {
                LoginController controller = loader.getController();
                controller.setMainController(this);
            }

            ventanaEmergente = new Stage();
            ventanaEmergente.setTitle(titulo);
            ventanaEmergente.setScene(new Scene(root));
            ventanaEmergente.setX(350);
            ventanaEmergente.setY(100);
            Image icon = new Image(getClass().getResourceAsStream("logo_e_corner.png"));
            ventanaEmergente.getIcons().add(icon);

            ventanaEmergente.setOnCloseRequest(event -> ventanaEmergente = null);

            ventanaEmergente.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void actualizarBotonSesion() {
        verificarUsuario();
    }

    public void iniciarSesion() {
        sesionIniciada = true;
        btnSesion.setText("Cerrar Sesión");
        verificacionPermisos();
    }

    public void cerrarSesion() {
        sesionIniciada = false;
        PswdAdminController.intentosFallidos = 0;
        btnSesion.setText("Iniciar Sesión");
        alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Sesión cerrada correctamente");
        login.cerrarSesion();
        configurarTooltips();
        App.getLoadedViews().clear();
        
    }
    
    @FXML
    private void Config(ActionEvent event) {

        if (sesionIniciada) {
            
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
            
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
        
    }
    
    @FXML
    private void bajarPDF() {
    
    }
    
    @FXML
    private void abrirGestorContra() {
    
        abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    @FXML
    private void abrirPerfilAdmin() {
    
        abrirformularios("pswdAdmin.fxml", "Ingrese su contraseña de Administrador");
    
    }
}
