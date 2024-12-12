package com.example.cincuentazo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeView extends Stage {

    public WelcomeView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/cincuentazo/welcome-view.fxml")
        );
        Parent root = loader.load(); // Cargar la raíz desde el archivo FXML
        this.setTitle("CINCUENTAZO"); // Establecer el título de la ventana
        Scene scene = new Scene(root); // Crear la escena con la raíz cargada
//        this.getIcons().add(new Image(
//                getClass().getResourceAsStream("/com/example/cincuentazo/images/.png")
//        )); // Establecer el icono de la ventana
        this.setScene(scene); // Establecer la escena en la ventana
        setResizable(false);
        this.show(); // Mostrar la ventana

    }

    public static WelcomeView getInstance() throws IOException {
        return WelcomeViewHolder.INSTANCE = new WelcomeView();
    }

    private static class WelcomeViewHolder {
        private static WelcomeView INSTANCE; // Instancia única de WelcomeView
    }
}