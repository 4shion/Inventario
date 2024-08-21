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
            scene = new Scene(loadFXML("main"));
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            stage.setMaximized(true);
            
            // Cargar la imagen del icono
            Image icon = new Image(getClass().getResourceAsStream("logo_e_corner.png"));
            // Establecer el icono de la ventana
            stage.getIcons().add(icon);
            
            stage.show();
            
        }
        else{
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error de conexión", "Por favor, revise la conexión a la base de datos");
            
        }
    }

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
