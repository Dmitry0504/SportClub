package support;

import javafx.scene.control.Alert;

public class AlertWindow {

    // Show a Information Alert with header Text
    public static void showAlertWithHeaderText() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Connection");
        alert.setHeaderText("Results:");
        alert.setContentText("Connect to the database successfully!");

        alert.showAndWait();
    }

    // Show a Information Alert with default header Text
    public static void showAlertWithDefaultHeaderText(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // alert.setHeaderText("Results:");
        alert.setContentText(text);

        alert.showAndWait();
    }

    // Show a Information Alert without Header Text
    public static void showAlertWithoutHeaderText(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

}
