package com.example.cincuentazo.view.alert;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * A utility class for displaying alerts and option dialogs in a JavaFX application.
 * Implements the {@code AlertBoxInterface}.
 */
public class AlertBox implements AlertBoxInterface {

    /**
     * Displays an alert with a specified title, header, message, and alert type.
     *
     * @param title     The title of the alert window.
     * @param header    The header text for the alert. Can be {@code null} for no header.
     * @param message   The main content text for the alert.
     * @param alertType The type of alert to display (e.g., INFORMATION, WARNING, ERROR, CONFIRMATION).
     */
    @Override
    public void showAlert(String title, String header, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a dialog with a list of options for the user to select.
     *
     * @param title   The title of the dialog window.
     * @param header  The header text for the dialog. Can be {@code null} for no header.
     * @param options A list of options to present to the user as buttons.
     * @return The text of the selected option, or {@code null} if no option was selected.
     */
    @Override
    public String showOptions(String title, String header, List<String> options) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);

        // Convert options into ButtonType objects
        List<ButtonType> buttonTypes = new ArrayList<>();
        for (String option : options) {
            buttonTypes.add(new ButtonType(option));
        }
        alert.getButtonTypes().setAll(buttonTypes);

        // Show the dialog and retrieve the user's selection
        Optional<ButtonType> result = alert.showAndWait();
        return result.map(ButtonType::getText).orElse(null);
    }
}
