module com.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.cincuentazo to javafx.fxml;
    opens com.example.cincuentazo.controllers to javafx.fxml;
    exports com.example.cincuentazo;


}