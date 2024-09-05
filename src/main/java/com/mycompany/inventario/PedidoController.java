package com.mycompany.inventario;

import com.mycompany.inventario.campos.factura;
import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.campos.pedido;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.navegacion;
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
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.MouseEvent;
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
    
    private conexion conexionDB = new conexion();
    private ObservableList<materia> listaMateriales;
    
    MainController m = new MainController();
    navegacion nav = new navegacion();
    alertas alert = new alertas();
    pedido p = new pedido();
    factura f = new factura();
    @FXML
    private TextField correoCliente;
    @FXML
    private TextField telfCliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEliminar.setDisable(true);
        cargarMaterial();
        TxtServicio.setPrefWidth(200);
        TxtServicio.setWrapText(true);
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        pedido materialseleccionado = table.getSelectionModel().getSelectedItem();

        if (materialseleccionado != null) {
            String nombreMaterial = materialseleccionado.getNombreM();

            table.getItems().remove(materialseleccionado);
            CbmMateriales.getItems().add(nombreMaterial);

            TxtCant.clear();
            CbmMateriales.getSelectionModel().clearSelection();
            btnEliminar.setDisable(true);

            calcularSubtotal();

            if (table.getItems().isEmpty()) {
                txtCosto.setText("SubTotal:");
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Advertencia", "Seleccione un material para eliminar");
        }
        
        CbmMateriales.setDisable(false);
    }

    @FXML
    private void Guardar(ActionEvent event) {
        p.setServicio(TxtServicio.getText());
        p.setNombreC(txtNomCliente.getText());
        
        int numFilas = table.getItems().size();
        String[] listaMaterialesN = new String[numFilas];
        double[] listaCant = new double[numFilas];

        for (int i = 0; i < numFilas; i++) {
            pedido materiales = table.getItems().get(i);
            listaMaterialesN[i] = materiales.getNombreM();
            listaCant[i] = materiales.getCant();
        }

        p.setListaMaterialesN(listaMaterialesN);
        p.setListaCant(listaCant);
        
        if (p.insertar()) {
            alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Insertado correctamente");
        } else {
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");
        }
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        TxtServicio.clear();
        txtNomCliente.clear();
        TxtCant.clear();
        txtCosto.setText("SubTotal:");
        CbmMateriales.getSelectionModel().clearSelection();
        table.getItems().clear();
        CbmMateriales.setDisable(false);
        btnGuardar.setDisable(false);
    }
    
    @FXML
    private void Agregar(ActionEvent event) {
        try {
            String nombreMaterial = CbmMateriales.getSelectionModel().getSelectedItem();

            if (nombreMaterial == null || TxtCant.getText().isEmpty()) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un material y una cantidad");
                return;
            }

            double cantidad = Double.parseDouble(TxtCant.getText());
            double precio = obtenerPrecioMaterial(nombreMaterial);
            double stockActual = obtenerStockActual(nombreMaterial);
            double stockRestante = stockActual - cantidad;
            String unidad = obtenerUnidadMedida(nombreMaterial);

            if (stockRestante < 0) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad solicitada excede la cantidad del stock");
                return;
            }

            pedido pedidoExistente = null;
            for (pedido p : table.getItems()) {
                if (p.getNombreM().equals(nombreMaterial)) {
                    pedidoExistente = p;
                    break;
                }
            }

            if (pedidoExistente != null) {
                pedidoExistente.setCant(cantidad);
                pedidoExistente.setStockRestante(stockRestante);
                pedidoExistente.setPrecio(precio);
                pedidoExistente.setUnidad(unidad);
                table.refresh();
                CbmMateriales.getSelectionModel().clearSelection();
            } else {
                pedido nuevoPedido = new pedido(0, "", 0, 0, 0, cantidad, "", nombreMaterial, stockRestante, precio, unidad);
                table.getItems().add(nuevoPedido);
                CbmMateriales.getItems().remove(nombreMaterial);
            }

            TxtCant.clear();
            CbmMateriales.setDisable(false);
            btnGuardar.setDisable(false);
            CbmMateriales.getSelectionModel().clearSelection();
            CbmMateriales.setValue(null);

            mostrarDatos();
            calcularSubtotal();
        } catch (NumberFormatException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error en la entrada de cantidad", e);
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al agregar el pedido", e);
        }
    }

    private void GenerarFactura(ActionEvent event) {
        String nombreCliente = txtNomCliente.getText();
        String servicio = TxtServicio.getText();
        double subtotal = calcularSubtotal();
        double total = subtotal;

        if (nombreCliente.isEmpty() || servicio.isEmpty()) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Debe completar todos los campos antes de generar la factura");
            return;
        }
<<<<<<< HEAD
    }
    
    @FXML
    private void abrirGestorContra() {
    
        m.abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    @FXML
    private void abrirPerfilAdmin() {
    
        m.abrirformularios("pswdAdmin.fxml", "Ingrese su codigo de Administrador");
    
    }
=======
>>>>>>> diseño

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre_cliente", nombreCliente);
            parametros.put("servicio", servicio);
            parametros.put("subtotal", subtotal);
            parametros.put("total", total);

            Connection conexion = conexionDB.getCon();
            reportes generar = new reportes();
            generar.generarReporte("Factura.jasper", parametros, conexion);
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al generar la factura", e);
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
    
    private void cargarMaterial() {
        listaMateriales = FXCollections.observableArrayList();
        for (materia object : listaMateriales) {
            CbmMateriales.getItems().add(object.getNombre());
        }

    }

    private double obtenerPrecioMaterial(String nombreMaterial) throws SQLException {
        Connection connection = conexionDB.getCon();
        String query = "SELECT precio FROM materiales WHERE nombre = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nombreMaterial);
        ResultSet rs = stmt.executeQuery();
        double precio = 0.0;
        if (rs.next()) {
            precio = rs.getDouble("precio");
        }
        return precio;
    }

    private double obtenerStockActual(String nombreMaterial) throws SQLException {
        Connection connection = conexionDB.getCon();
        String query = "SELECT stock FROM materiales WHERE nombre = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nombreMaterial);
        ResultSet rs = stmt.executeQuery();
        double stock = 0.0;
        if (rs.next()) {
            stock = rs.getDouble("stock");
        }
        return stock;
    }

    private String obtenerUnidadMedida(String nombreMaterial) throws SQLException {
        Connection connection = conexionDB.getCon();
        String query = "SELECT unidad FROM materiales WHERE nombre = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nombreMaterial);
        ResultSet rs = stmt.executeQuery();
        String unidad = "";
        if (rs.next()) {
            unidad = rs.getString("unidad");
        }
        return unidad;
    }

    private double calcularSubtotal() {
        double subtotal = 0.0;
        for (pedido p : table.getItems()) {
            subtotal += p.getCant() * p.getPrecio();
        }
        txtCosto.setText("SubTotal: " + subtotal);
        return subtotal;
    }

    private void mostrarDatos() {
        ColumMaterial.setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        ColumCantidad.setCellValueFactory(new PropertyValueFactory<>("cant"));
        ColumStock.setCellValueFactory(cellData -> {
            double stockRestante = cellData.getValue().getStockRestante();
            String stockStr = stockRestante % 1 == 0 ? String.valueOf((int) stockRestante) : String.valueOf(stockRestante);
            return new SimpleStringProperty(stockStr);
        });
    }

    @FXML
    private void Click(MouseEvent event) {
    }

    @FXML
    private void Factura(ActionEvent event) {
    }

    @FXML
    private void switchToMain(ActionEvent event) {
    }

    @FXML
    private void switchToMateriales(ActionEvent event) {
    }

    @FXML
    private void switchToCliente(ActionEvent event) {
    }

    @FXML
    private void switchToPedido(ActionEvent event) {
    }

    @FXML
    private void swicthToProveedor(ActionEvent event) {
    }

    @FXML
    private void switchToUsuarios(ActionEvent event) {
    }

    @FXML
    private void switchToHistorial(ActionEvent event) {
    }

    @FXML
    private void bajarPDF(ActionEvent event) {
    }

    @FXML
    private void abrirGestorContra(ActionEvent event) {
    }

    @FXML
    private void verificar(ActionEvent event) {
    }
}
