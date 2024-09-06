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

    //Nat Contra:123
    //Walter Contra:6291291 CodAdmi:321
    
    private static Scene scene;
    alertas alert = new alertas();
    private static Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        conexion conec = new conexion();
        if(conec.getCon() != null){
           
            root = (Pane) loadFXML("main");
            scene = new Scene(root, 1200, 700);
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            stage.setResizable(false);

            // Cargar la imagen del icono
            Image icon = new Image(getClass().getResourceAsStream("logo_e_corner.png"));
            // Establecer el icono de la ventana
            stage.getIcons().add(icon);

            Pane configuracion = new Pane();
            configuracion.setPrefSize(300, 300);
            configuracion.setTranslateX(1200);
            ((Pane) root).getChildren().add(configuracion);
            
            stage.show();   
            
        }
        else{
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error de conexión", "Por favor, revise la conexión a la base de datos");
            
        }
    }


    public static Scene getScene() {
        return scene;
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
