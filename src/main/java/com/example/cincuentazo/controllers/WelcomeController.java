package com.example.cincuentazo.controllers;

import com.example.cincuentazo.view.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private TextField maquinasTextField;

    @FXML
    void onHandlePlayButton(ActionEvent event) throws IOException {

        String machinesNumber = maquinasTextField.getText();

        if (machinesNumber.matches("[1-3]")){

            GameView.getInstance().getGameController().initializeGame(Integer.parseInt(machinesNumber));
        }
    }

    @FXML
    void onHandleInstructionsButton(ActionEvent event) {

    }
}
