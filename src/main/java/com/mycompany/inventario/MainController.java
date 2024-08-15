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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
        
         try {
            if (btnSesion.getText().equals("Registrarse")) {
                
                App.setRoot("loginAdmi");
                
            } else if (btnSesion.getText().equals("Iniciar Sesión")) {
                
                App.setRoot("login");
            }
        } catch (IOException ex) {
            
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            
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
    
    
    
}
