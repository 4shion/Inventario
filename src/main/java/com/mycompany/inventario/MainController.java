/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.clases.conexion;
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
import javafx.scene.control.Button;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        verificarUsuario();
        
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
            
            abrirformularios("loginAdmi.fxml", "");
            
        } else if (btnSesion.getText().equals("Iniciar Sesión")) {
            
            abrirformularios("login.fxml", "");
            
        }
    }
    
    private void verificarUsuario() {
        
        String sql = "SELECT COUNT(*) FROM usuario";
        
        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){

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
    
    public void abrirformularios(String fxml, String titulo){
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            
            Parent root = loader.load();
            
            if (fxml.equals("loginAdmi.fxml")) {
                LoginAdmiController controller = loader.getController();
                controller.setMainController(this); // Pasar la referencia del MainController
            }
            
            if (fxml.equals("login.fxml")) {
                LoginAdmiController controller = loader.getController();
                controller.setMainController(this); // Pasar la referencia del MainController
            }
            
            Stage stage = new Stage();
            stage.setTitle(titulo);
            //Medidas 660, 480
            stage.setScene(new Scene(root, 660, 480));
            
            //Medidas X 260 Y 60
            stage.setX(350);
            stage.setY(100);
            
            // Cargar la imagen del icono
            Image icon = new Image(getClass().getResourceAsStream("logo_e_corner.png"));
            // Establecer el icono de la ventana
            stage.getIcons().add(icon);
            
            stage.show();
            
        } catch (IOException ex) {
            
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    public void actualizarBotonSesion() {
        
        verificarUsuario();
        
    }
    
    public void iniciar(){
        
        btnSesion.setText("Cerrar sesión");
        
    }
}
