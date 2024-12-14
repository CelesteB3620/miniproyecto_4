package com.example.cincuentazo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the welcome view window for the "CINCUENTAZO" game.
 * This class is responsible for loading and displaying the welcome screen using an FXML file.
 * It implements the Singleton design pattern to ensure only one instance of the welcome view exists.
 */
public class WelcomeView extends Stage {

    /**
     * Constructs a new WelcomeView instance and initializes the welcome screen.
     *
     * @throws IOException If there is an issue loading the FXML file.
     */
    public WelcomeView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/cincuentazo/welcome-view.fxml")
        );
        Parent root = loader.load(); // Load the root node from the FXML file
        this.setTitle("CINCUENTAZO"); // Set the window title
        Scene scene = new Scene(root); // Create a scene with the loaded root node
        this.setScene(scene); // Set the scene on the stage
        setResizable(false); // Disable resizing of the window
        this.show(); // Display the window
    }

    /**
     * Returns the singleton instance of WelcomeView. If the instance does not already exist,
     * it will be created.
     *
     * @return The singleton instance of WelcomeView.
     * @throws IOException If there is an issue creating a new WelcomeView instance.
     */
    public static WelcomeView getInstance() throws IOException {
        return WelcomeViewHolder.INSTANCE = new WelcomeView();
    }

    /**
     * A static holder class for the singleton instance of WelcomeView.
     * This ensures lazy initialization of the singleton.
     */
    private static class WelcomeViewHolder {
        private static WelcomeView INSTANCE; // The unique instance of WelcomeView
    }
}