package com.example.cincuentazo.controllers;

import com.example.cincuentazo.view.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller for the WelcomeView. Handles user interactions and events
 * on the welcome screen for the "CINCUENTAZO" game.
 * @autor Laura Celeste Berrio - Leonardo Rios Saavedra
 */
public class WelcomeController {

    /**
     * Text field for entering the number of machines to play against.
     * Accepts values between 1 and 3.
     */
    @FXML
    private TextField maquinasTextField;

    /**
     * Handles the event triggered by clicking the "Play" button.
     * Validates the input from the `maquinasTextField` and initializes the game
     * with the specified number of machines.
     *
     * @param event The action event triggered by the "Play" button.
     * @throws IOException If there is an issue transitioning to the game view.
     */
    @FXML
    void onHandlePlayButton(ActionEvent event) throws IOException {
        String machinesNumber = maquinasTextField.getText();

        // Validate input: must be a number between 1 and 3
        if (machinesNumber.matches("[1-3]")) {
            GameView.getInstance().getGameController().initializeGame(Integer.parseInt(machinesNumber));
        }
    }

    /**
     * Handles the event triggered by clicking the "Instructions" button.
     * Opens the instructions or provides a placeholder for future implementation.
     *
     * @param event The action event triggered by the "Instructions" button.
     */
    @FXML
    void onHandleInstructionsButton(ActionEvent event) {
        // Placeholder for instructions handling logic
    }
}
