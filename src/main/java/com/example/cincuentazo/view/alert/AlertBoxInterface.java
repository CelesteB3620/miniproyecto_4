package com.example.cincuentazo.view.alert;
import javafx.scene.control.Alert;

import java.util.List;

public interface AlertBoxInterface {
    public void showAlert(String title, String header, String message, Alert.AlertType alertType);

    String showOptions(String title, String header, List<String> options);
}
