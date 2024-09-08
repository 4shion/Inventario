package com.mycompany.inventario;

import com.mycompany.inventario.campos.cliente;
import com.mycompany.inventario.campos.factura;
import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.campos.pedido;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.reportes;
import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.cell.PropertyValueFactory;

public class PedidoController implements Initializable {

    @FXML private TextArea TxtServicio;
    @FXML private TableView<pedido> table;
    @FXML private TableColumn<pedido, String> ColumMaterial;
    @FXML private TableColumn<pedido, String> ColumCantidad;
    @FXML private TableColumn<pedido, String> ColumStock;
    @FXML private ComboBox<String> CbmMateriales;
    @FXML private Text txtCosto;
    @FXML private Button BtnFactura;
    @FXML private TextField TxtCant;
    @FXML private Button BtnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;
    @FXML private TextField idPedido;
    @FXML private Pane configuracion;
    @FXML private ImageView engranaje;
    @FXML private TextField txtNomCliente;
    @FXML private TextField correoCliente;
    @FXML private TextField telfCliente;
    @FXML private TextField numFactura;

    private conexion conexionDB = new conexion();
    private ObservableList<materia> listaMateriales;
    private alertas alert = new alertas();
    private pedido p = new pedido();
    private factura f = new factura();
    private cliente client = new cliente();
    private reportes r = new reportes();
    private MainController main = new MainController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEliminar.setDisable(true);
        cargarMaterial();
        TxtServicio.setPrefWidth(200);
        TxtServicio.setWrapText(true);
        configurarColumnas();
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        pedido materialseleccionado = table.getSelectionModel().getSelectedItem();
        if (materialseleccionado != null) {
            String nombreMaterial = materialseleccionado.getNombreM();
            table.getItems().remove(materialseleccionado);
            CbmMateriales.getItems().add(nombreMaterial);
            limpiarCampos();
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
        p.obtenerIdClientePorNombre(txtNomCliente.getText());
        p.setTotalPedido(calcularTotal());

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
            p.searchId();
            System.out.println("La id del pedido es: " + p.getIdPedido());
        } else {
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");
        }
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        limpiarCampos();
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
            double precio = p.obtenerPrecioMaterial(nombreMaterial);
            double stockActual = p.obtenerStockActual(nombreMaterial);
            double stockRestante = stockActual - cantidad;
            String unidad = p.obtenerUnidadMedida(nombreMaterial);

            if (stockRestante < 0) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad solicitada excede la cantidad del stock");
                return;
            }

            pedido pedidoExistente = table.getItems().stream()
                .filter(p -> p.getNombreM().equals(nombreMaterial))
                .findFirst()
                .orElse(null);

            if (pedidoExistente != null) {
                pedidoExistente.setCant(cantidad);
                pedidoExistente.setStockRestante(stockRestante);
                pedidoExistente.setPrecio(precio);
                pedidoExistente.setUnidad(unidad);
                table.refresh();
            } else {
                pedido nuevoPedido = new pedido(cantidad,nombreMaterial, stockRestante, precio, unidad);
                table.getItems().add(nuevoPedido);
                CbmMateriales.getItems().remove(nombreMaterial);
            }

            limpiarCampos();
            CbmMateriales.setDisable(false);
            btnGuardar.setDisable(false);
            mostrarDatos();
            calcularSubtotal();

        } catch (NumberFormatException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error en la entrada de cantidad", e);
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al agregar el pedido", e);
        }
    }

    @FXML
    private void Factura(ActionEvent event) {
        f.setSubTotal(calcularSubtotal());
        f.setTotal(calcularTotal());
        client.buscarDatosCliente(txtNomCliente.getText());
        f.setIdcliente(client.getId());
        p.searchId();
        f.setIdPedido(p.getIdPedido());
        f.insertar();
        f.obtenerNumFac();
        int numFactura = f.getNumFactura();

        try {
            String ubicacion = "/reportes/frameexperts/factura.jasper";
            String titulo = "Factura N~" + numFactura;
            r.generarFactura(ubicacion, titulo, numFactura);
            System.out.println("Reporte exitoso");
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al generar el reporte", e);
        }
    }

    @FXML
    private void Config(ActionEvent event) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(500), configuracion);
        slide.setFromX(configuracion.isVisible() ? 0 : 800);
        slide.setToX(configuracion.isVisible() ? 800 : 0);
        slide.play();

        RotateTransition rotate = new RotateTransition(Duration.millis(350), engranaje);
        rotate.setByAngle(configuracion.isVisible() ? -60 : 60);
        rotate.play();

        configuracion.setVisible(!configuracion.isVisible());
    }

    @FXML
    private void Click(MouseEvent event) {
        pedido seleccionado = table.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            TxtCant.setText(String.valueOf(seleccionado.getCant()));
            CbmMateriales.setValue(seleccionado.getNombreM());
            btnEliminar.setDisable(false);
            btnGuardar.setDisable(true);
            CbmMateriales.setDisable(true);
        }
    }

    private void mostrarDatos() {
        ColumMaterial.setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        ColumCantidad.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCantidadConUnidad()));
        ColumStock.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStockRestanteConUnidad()));
    }

    private void cargarMaterial() {
        listaMateriales = FXCollections.observableArrayList(new materia().consulta());
        CbmMateriales.getItems().addAll(
            listaMateriales.stream().map(materia::getNombre).toList()
        );
    }

    private void limpiarCampos() {
        TxtServicio.clear();
        txtNomCliente.clear();
        TxtCant.clear();
        txtCosto.setText("SubTotal:");
        CbmMateriales.getSelectionModel().clearSelection();
    }

    private double calcularSubtotal() {
        double subtotal = table.getItems().stream()
            .mapToDouble(p -> p.getPrecio() * p.getCant())
            .sum();
        txtCosto.setText("SubTotal: " + subtotal);
        return subtotal;
    }

    private double calcularTotal() {
        return calcularSubtotal() * 0.23;
    }

    private void configurarColumnas() {
        ColumMaterial.setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        ColumCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadConUnidad"));
        ColumStock.setCellValueFactory(new PropertyValueFactory<>("stockRestanteConUnidad"));
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
    
        main.abrirformularios("pswdAdmin.fxml", "Ingrese su codigo de Administrador");
    
    }

    @FXML
    private void bajarPDF(ActionEvent event) {
    }
    
    @FXML
    private void verificar(ActionEvent event) {
    }
}
