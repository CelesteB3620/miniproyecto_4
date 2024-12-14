package com.example.cincuentazo.view.alert;
import javafx.scene.control.Alert;

import java.util.List;

/**
 * An interface for defining methods to display alert boxes and option dialogs in a JavaFX application.
 */
public interface AlertBoxInterface {

    /**
     * Displays a simple alert dialog with a title, header, message, and alert type.
     *
     * @param title     The title of the alert window.
     * @param header    The header text for the alert. Can be {@code null} if no header is needed.
     * @param message   The main content message for the alert.
     * @param alertType The type of alert to be displayed (e.g., INFORMATION, WARNING, ERROR, CONFIRMATION).
     */
    void showAlert(String title, String header, String message, Alert.AlertType alertType);

    /**
     * Displays a dialog with multiple options for the user to select from.
     *
     * @param title   The title of the dialog window.
     * @param header  The header text for the dialog. Can be {@code null} if no header is needed.
     * @param options A list of options to be displayed as buttons.
     * @return The text of the option selected by the user, or {@code null} if no option was chosen.
     */
    String showOptions(String title, String header, List<String> options);
}