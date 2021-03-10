package sample;

/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {
    private static String login;
    private static String password;

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="inputLogin"
    private TextField inputLogin; // Value injected by FXMLLoader

    @FXML // fx:id="inputPassword"
    private PasswordField inputPassword; // Value injected by FXMLLoader

    @FXML
    private Button nextBtn;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        login = inputLogin.getText();
        password = inputPassword.getText();

        nextBtn.setOnAction(actionEvent -> {

            try {
                connectBD();
            } catch (SQLException e) {
                AlertWindow.showAlertWithoutHeaderText("Проверьте введенные данные!");
                return;
            }

            Stage stage = (Stage) nextBtn.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Спортклуб");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private static void connectBD() throws SQLException {
        if(connection != null)
            connection.close();
        connection = Connector.getNewConnection();
    }

    public static void refreshConnection(){
        try {
            connectBD();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
