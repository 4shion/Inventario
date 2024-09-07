package com.mycompany.inventario;

import com.mycompany.inventario.campos.cliente;
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
    @FXML
    private TextField numFactura;

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
        p.obtenerIdClientePorNombre(txtNomCliente.getText());
        
        System.out.println("id cliente: " + p.getIdCliente());
                
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
        
        if(p.insertar()){

            alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Insertado correctamente");
            p.searchId();
            System.out.println("la id del pedido es:" + p.getIdPedido());

        }
         else{
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
            // Obtiene el nombre del material seleccionado en el ComboBox
            String nombreMaterial = CbmMateriales.getSelectionModel().getSelectedItem();

            // Verifica si se ha seleccionado un material y se ha ingresado una cantidad
            if (nombreMaterial == null || TxtCant.getText().isEmpty()) {
                // Muestra un mensaje de error si alguno de los campos está vacío
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un material y una cantidad");
                return;
            }

            // Intenta convertir la cantidad ingresada en un número
            double cantidad = Double.parseDouble(TxtCant.getText());
            // Obtiene el precio del material seleccionado
            double precio = p.obtenerPrecioMaterial(nombreMaterial);
            // Obtiene el stock actual del material
            double stockActual = p.obtenerStockActual(nombreMaterial);
            // Calcula el stock restante después de agregar la cantidad solicitada
            double stockRestante = stockActual - cantidad;
            // Obtiene la unidad de medida
            String unidad = p.obtenerUnidadMedida(nombreMaterial);

            // Verifica si el stock restante es negativo (cantidad solicitada excede el stock)
            if (stockRestante < 0) {
                // Muestra un mensaje de error si la cantidad solicitada excede el stock
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad solicitada excede la cantidad del stock");
                return;
            }

            // Busca en la tabla si ya existe un pedido con el mismo material
            pedido pedidoExistente = null;
            for (pedido p : table.getItems()) {
                if (p.getNombreM().equals(nombreMaterial)) {
                    // Si se encuentra un pedido existente, lo asigna a la variable
                    pedidoExistente = p;
                    break;
                }
            }

            // Si ya existe un pedido con el mismo material, actualiza su cantidad y stock
            if (pedidoExistente != null) {
                // Actualiza la cantidad del pedido existente
                pedidoExistente.setCant(cantidad);
                // Actualiza el stock restante del pedido existente
                pedidoExistente.setStockRestante(stockRestante);
                // Actualiza el precio del pedido existente
                pedidoExistente.setPrecio(precio);
                // Actualiza la unidad del pedido exitente
                pedidoExistente.setUnidad(unidad);
                // Refresca la tabla para mostrar los cambios
                table.refresh();
                CbmMateriales.getSelectionModel().clearSelection();
            } else {
                // Si no existe un pedido con el mismo material, crea uno nuevo
                pedido nuevoPedido = new pedido(0, "", 0, 0, 0, cantidad, "", nombreMaterial, stockRestante, precio, unidad);
                // Añade el nuevo pedido a la tabla
                table.getItems().add(nuevoPedido);
                // Elimina el material seleccionado del ComboBox
                CbmMateriales.getItems().remove(nombreMaterial);
            }

            // Limpia el ComboBox y el campo de cantidad después de agregar o modificar
            TxtCant.clear();
            CbmMateriales.setDisable(false);
            btnGuardar.setDisable(false);
            CbmMateriales.getSelectionModel().clearSelection();
            CbmMateriales.setValue(null);

            // Recarga el Combo Box
            // Muestra los datos en la tabla
            mostrarDatos();
            // Calcula el subtotal de los pedidos
            calcularSubtotal();
        
        } catch (NumberFormatException e) {
            // Maneja el caso en que la cantidad ingresada no es un número válido
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error en la entrada de cantidad", e);
        } catch (IllegalArgumentException e) {
            // Maneja casos donde la entrada es inválida (si aplica)
            Logger.getLogger(PedidoController.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } catch (Exception e) {
            // Maneja cualquier otro tipo de excepción que pueda ocurrir
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al agregar el pedido", e);
        }

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
    private void swicthToProveedor(ActionEvent event) {
        
        try {
            App.setRoot("proveedor");
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void abrirGestorContra() {
    
        m.abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    
    @FXML
    private void abrirPerfilAdmin() {
    
        m.abrirformularios("pswdAdmin.fxml", "Ingrese su codigo de Administrador");
    
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
    
    @FXML
    private void Click(MouseEvent event) {
        
        p = table.getSelectionModel().getSelectedItem();
        TxtCant.setText(String.valueOf(p.getCant()));
        CbmMateriales.setValue(p.getNombreM());
        btnEliminar.setDisable(false);
        btnGuardar.setDisable(true);
        CbmMateriales.setDisable(true);
        
    }
    
    private void mostrarDatos(){
        ColumMaterial.setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        // Utilizar las propiedades calculadas para las columnas
        ColumCantidad.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCantidadConUnidad()));

        ColumStock.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStockRestanteConUnidad()));
        
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
    
    private Map<String, String> buscarDatosCliente() {
        String nombreCliente = txtNomCliente.getText();
        String correo = "";
        int telf = 0;


        String consulta = "SELECT correo, telefono FROM Cliente WHERE nombre = ?";
        Map<String, String> datosCliente = new HashMap<>();

        try (PreparedStatement stmt = conexionDB.getCon().prepareStatement(consulta)) {
            stmt.setString(1, nombreCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                correo = rs.getString("correo");
                telf = rs.getInt("telefono");
            } else {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Cliente no registrado");
            }
            p.setCorreo(correo);
            p.setTelf(telf);
        } catch (SQLException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datosCliente;
    }
    
    @FXML
    private void Factura(ActionEvent event) {

    if (txtNomCliente.getText().isEmpty() || TxtServicio.getText().isEmpty()) {
        alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Debe completar todos los campos para generar la factura");
        return;
    }
    
    cliente client = new cliente();
    client.buscarDatosCliente(txtNomCliente.getText());
    
    if (client.getNombre().isEmpty()) {
        return;
    }
        reportes report = new reportes();
        double subtotal = calcularSubtotal();
        double total = calcularTotal();
        int numFactura = f.getNumFactura();
        System.out.println(numFactura);

        
        f.setSubTotal(subtotal);
        f.setTotal(total);
        f.setNumFactura(numFactura);
        f.setIdPedido(p.getIdPedido());
        //asignar nroFactura por medio de metodo (vos podes Walter)
        
        try {
            f.insertar();
            reportes r=new reportes();
            String ubicacion = "/reportes.frameexperts/factura.jasper";
            String titulo = "Factura N~" + String.valueOf(numFactura);

            r.generarFactura(ubicacion, titulo, numFactura);
            System.out.println("Reporte exitoso");
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al generar el reporte", e);
            System.out.println("no funca xd");
        }
    }

    @FXML
    private void bajarPDF(ActionEvent event) {
    }
    
    @FXML
    private void verificar() {
    }
    
}