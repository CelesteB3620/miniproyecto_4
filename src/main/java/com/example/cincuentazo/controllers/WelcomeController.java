package com.example.cincuentazo.controllers;

import com.example.cincuentazo.view.GameView;
import com.example.cincuentazo.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller for the WelcomeView. Handles user interactions and events
 * on the welcome screen for the "CINCUENTAZO" game.
 * @author Laura Celeste Berrio - Leonardo Rios Saavedra
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
        // Close the current window
        ((javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Handles the event triggered by clicking the "Instructions" button.
     * Opens the instructions or provides a placeholder for future implementation.
     *
     * @param event The action event triggered by the "Instructions" button.
     */
    @FXML
    void onHandleInstructionsButton(ActionEvent event) {

        new AlertBox().showAlert("INFORMATION", "INSTRUCCIONES DEL JUEGO", "El juego consiste en que cada jugador recibe 4 cartas del mazo principal y posteriormente se saca de ese mismo mazo 1 carta que se pone en la mesa. Cada jugador en su turno debe escoger una carta, ponerla en la mesa y sacar una carta del mazo que reemplace la que acaba de poner ya que siempre debe tener 4 cartas, las cartas en la mesa se irán sumando y el jugador que en su turno ponga una carta que al sumarse con las cartas de la mesa sobrepase la cuenta de 50 perderá y saldrá del juego. El juego continúa hasta que solo quede un jugador. Las cartas tienen los siguientes valores:  las cartas del 2 al 8 y el 10 suman su número, las cartas con el número 9 ni suman ni restan o sea valen 0, las cartas con las letras J, Q y K restan 10, las cartas con la letra A suman 1 o 10 según lo que escoja el jugador. Usted puede jugar hasta contra 3 jugadores.", Alert.AlertType.INFORMATION);
    }
}
