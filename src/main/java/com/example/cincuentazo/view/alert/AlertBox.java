package com.example.cincuentazo.view.alert;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AlertBox implements AlertBoxInterface{

    @Override
    public void showAlert(String title, String header, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public String showOptions(String title, String header, List<String> options) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);

        List<ButtonType> buttonTypes = new ArrayList<>();
        for (String option : options) {
            buttonTypes.add(new ButtonType(option));
        }
        alert.getButtonTypes().setAll(buttonTypes);

        Optional<ButtonType> result = alert.showAndWait();
        return result.map(ButtonType::getText).orElse(null);
    }



}
