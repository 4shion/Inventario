/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.clases;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author User
 */
public class navegacion {
    
    public navegacion(){
    }
            
    // Método para agregar el manejo de teclas de flecha para campos de texto
    public void addArrowKeyNavigation(TextField textField, javafx.scene.control.Control nextField, javafx.scene.control.Control previousField) {
        textField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (previousField != null) previousField.requestFocus(); // Mueve el enfoque al campo anterior
                    break;
                case DOWN:
                    if (nextField != null) nextField.requestFocus(); // Mueve el enfoque al siguiente campo
                    break;
                default:
                    break;
            }
        });
    }

    // Método para agregar el manejo de teclas de flecha para ComboBox
    public void addArrowKeyNavigation(ComboBox<String> comboBox, javafx.scene.control.Control nextField, javafx.scene.control.Control previousField) {
        comboBox.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (previousField != null) previousField.requestFocus();
                    break;
                case DOWN:
                    if (nextField != null) nextField.requestFocus();
                    break;
                default:
                    break;
            }
        });
    }

    // Método para agregar el manejo de teclas de flecha para TextArea
    public void addArrowKeyNavigation(TextArea textArea, javafx.scene.control.Control nextField, javafx.scene.control.Control previousField) {
        textArea.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (previousField != null) previousField.requestFocus();
                    break;
                case DOWN:
                    if (nextField != null) nextField.requestFocus();
                    break;
                default:
                    break;
            }
        });
    }
    
}
