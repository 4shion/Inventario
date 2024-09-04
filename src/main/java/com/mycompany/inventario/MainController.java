/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
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
import javafx.stage.Stage;

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

    private Stage ventanaEmergente = null; // Quita el "final"
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
        
        if(pPro){
            
            btnProveedores.setTooltip(TextButton("Este usuario tiene permiso para modificar esta vista"));
            
        }
        else{
            
            btnProveedores.setTooltip(TextButton("Este usuario no tiene permiso para modificar esta vista"));
            
        }
        
        if(pUsu){
            
            btnUsuarios.setTooltip(TextButton("Este usuario tiene permiso para modificar esta vista"));
            
        }
        else{
            
            btnUsuarios.setTooltip(TextButton("Este usuario no tiene permiso para modificar esta vista"));
            
        }
        
        if(pMate){
            
            btnMateriales.setTooltip(TextButton("Este usuario tiene permiso para modificar esta vista"));
            
        }
        else{
            
            btnMateriales.setTooltip(TextButton("Este usuario no tiene permiso para modificar esta vista"));
            
        }
        
        if(pCli){
            
            btnClientes.setTooltip(TextButton("Este usuario tiene permiso para modificar esta vista"));
            
        }
        else{
            
            btnClientes.setTooltip(TextButton("Este usuario no tiene permiso para modificar esta vista"));
            
        }
        
        if(pPe){
            
            btnPedidos.setTooltip(TextButton("Este usuario tiene permiso para modificar esta vista"));
            
        }
        else{
            
            btnPedidos.setTooltip(TextButton("Este usuario no tiene permiso para modificar esta vista"));
            
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
        btnSesion.setText("Iniciar Sesión");
        alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Sesión cerrada correctamente");
        login.cerrarSesion();
        configurarTooltips();
    }
}
