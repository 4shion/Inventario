package com.mycompany.inventario;

import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.campos.pedido;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.reportes;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    @FXML
    private ImageView engranaje;
    @FXML
    private TextField txtNomCliente;

    private TextField correoCliente;
    private TextField telfCliente;
    private conexion conexionDB = new conexion();
    private ObservableList<materia> listaMateriales;
    alertas alert = new alertas();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ColumMaterial.setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        ColumCantidad.setCellValueFactory(new PropertyValueFactory<>("Cant"));
        ColumStock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        cargarMaterial();
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        // Implementar lógica para eliminar
    }

    @FXML
    private void Guardar(ActionEvent event) {
        // Implementar lógica para guardar
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        // Implementar lógica para limpiar
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
        listaMateriales = FXCollections.observableArrayList(new materia().consulta());
        for (materia object : listaMateriales) {
            CbmMateriales.getItems().add(object.getNombre());
        }
    }

    private int buscarMaterial() {
        for (materia object : listaMateriales) {
            if (object.getNombre().equals(CbmMateriales.getSelectionModel().getSelectedItem())) {
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
                correoCliente.setText(rs.getString("correo"));
                telfCliente.setText(rs.getString("telefono"));
            } else {
                correoCliente.setText("");
                telfCliente.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Factura(ActionEvent event) {
        reportes report = new reportes();
        buscarDatosCliente();

        double subtotal = calcularSubtotal();
        double total = calcularTotal();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombreCliente", txtNomCliente.getText());
        parametros.put("correoCliente", correoCliente.getText());
        parametros.put("telfCliente", telfCliente.getText());
        parametros.put("servicio", TxtServicio.getText());
        parametros.put("subtotal", subtotal);
        parametros.put("total", total);

        try {
            report.generarReporte("/reportes/factura.jasper", "Factura", parametros);
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al generar el reporte", e);
        }
    }

    private double obtenerPrecioMaterial(String nombreMaterial) {
        double precio = 0.0;
        String query = "SELECT precio FROM materiaPrima WHERE nombre = ?";

        try (Connection con = conexionDB.getCon();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nombreMaterial);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                precio = rs.getDouble("precio");
            }
        } catch (SQLException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al obtener precio del material", e);
        }

        return precio;
    }

    private double calcularSubtotal() {
        double subtotal = 0.0;
        for (pedido p : table.getItems()) {
            subtotal += p.getPrecio() * p.getCant();
        }
        txtCosto.setText(String.format("Subtotal: %.2f", subtotal));
        return subtotal;
    }

    private double calcularTotal() {
        double subtotal = calcularSubtotal();
        return subtotal + (subtotal * 0.23); // 23% IVA
    }

    @FXML
    private void Agregar(ActionEvent event) {
        try {
            
            String nombreMaterial = CbmMateriales.getSelectionModel().getSelectedItem();
            System.out.println("Nombre del material: " + nombreMaterial);
            
            //Verificación que ningun campo sea nulo
            if (nombreMaterial == null || TxtCant.getText().isEmpty()) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un material y una cantidad");
                return;
            }

            double cantidad = Double.parseDouble(TxtCant.getText());
            System.out.println("Cantidad: " + cantidad);

            double precio = obtenerPrecioMaterial(nombreMaterial);
            System.out.println("Precio: " + precio);

            double stockActual = obtenerStockActual(nombreMaterial);
            System.out.println("Stock actual: " + stockActual);

            double stockRestante = stockActual - cantidad;
            System.out.println("Stock restante: " + stockRestante);

            if (stockRestante < 0) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad solicitada excede la cantidad del stock");
                return;
            }

            // Añade un nuevo pedido a la tabla
            pedido nuevoPedido = new pedido(0, "", 0, 0, 0, cantidad, "", nombreMaterial, 0, precio);
            nuevoPedido.setStockRestante(stockRestante);

            table.getItems().add(nuevoPedido);
            
            TxtCant.clear();
            CbmMateriales.getSelectionModel().clearSelection();
            
            calcularSubtotal();
        } catch (NumberFormatException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error en la entrada de cantidad", e);
        } catch (IllegalArgumentException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al agregar el pedido", e);
        }
    }

    private double obtenerStockActual(String nombreMaterial) {
        double stockActual = 0.0;
        String query = "SELECT cantidad FROM materiaPrima WHERE nombre = ?";

        try (Connection con = conexionDB.getCon();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nombreMaterial);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                stockActual = rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al obtener stock del material", e);
        }

        return stockActual;
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
