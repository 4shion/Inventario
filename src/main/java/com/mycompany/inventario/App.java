package com.mycompany.inventario;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    alertas alert = new alertas();

    @Override
    public void start(Stage stage) throws IOException {
        conexion conec = new conexion();
        if(conec.getCon() != null){
            
            //840, 615 Main
            //660, 480 Login

            scene = new Scene(loadFXML("main"), 1200, 700);
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
<<<<<<< HEAD
            stage.setResizable(false);

=======
            
>>>>>>> diseño
            // Cargar la imagen del icono
            Image icon = new Image(getClass().getResourceAsStream("logo_e_corner.png"));
            // Establecer el icono de la ventana
            stage.getIcons().add(icon);
<<<<<<< HEAD

            Pane root = (Pane) scene.getRoot();
            
////           Crear el Pane de configuración
//            Pane configuracion = new Pane();
//            configuracion.setPrefSize(236, 439);
//            configuracion.setStyle("-fx-background-color: #808080;");
//            configuracion.setTranslateX(-236); 
//
//            
//            Button botonConfig = new Button("Mostrar Configuración");
//            botonConfig.setOnAction(e -> mostrarPane(configuracion));
//
//            root.getChildren().addAll(configuracion, botonConfig);

            stage.show();

        } else {

=======
            
            Pane configuracion = new Pane();
            configuracion.setPrefSize(200, 200);
            configuracion.setStyle("-fx-background-color: lightblue;");
            configuracion.setTranslateX(1425);
            
            stage.show();
            
        }
        else{
            
>>>>>>> diseño
            alert.ShowAlert(Alert.AlertType.ERROR, "Error de conexión", "Por favor, revise la conexión a la base de datos");
            
        }
    }

<<<<<<< HEAD
//    //prueba 
//    private void mostrarPane(Pane configuracion) {
//        TranslateTransition transition = new TranslateTransition();
//        transition.setNode(configuracion);
//        transition.setToX(0); 
//        transition.setDuration(Duration.millis(500)); 
//        transition.play();
//    }

=======
    public static Scene getScene() {
        return scene;
    }
    
>>>>>>> diseño
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
