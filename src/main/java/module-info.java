module com.mycompany.inventario {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires jasperreports;
    requires bcrypt;

    opens com.mycompany.inventario to javafx.fxml;
    exports com.mycompany.inventario;
    exports com.mycompany.inventario.clases;
    exports com.mycompany.inventario.campos;
}
