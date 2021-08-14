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
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import support.AlertWindow;

public class Controller {
    private static String login;
    private static String password;
    private static final Logger logger = Logger.getLogger(Controller.class);

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

    @FXML
    private Button helpBtn;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        inputLogin.setTooltip(new Tooltip("Введите сюда свой логин"));
        inputPassword.setTooltip(new Tooltip("Введите сюда свой пароль"));
        login = inputLogin.getText();
        password = inputPassword.getText();

        nextBtn.setOnAction(actionEvent -> {

            try {
                connection = Connector.getNewConnection(inputLogin.getText().trim(), inputPassword.getText().trim());
                logger.info(inputLogin.getText() + " вошел в систему");
            } catch (SQLException e) {
                AlertWindow.showAlertWithoutHeaderText("Проверьте введенные данные!");
                logger.error("Введены неверные данные!");
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

        helpBtn.setOnAction(actionEvent -> {
            AlertWindow.showAlertWithoutHeaderText("Программа создана для работы с базой данных 'Спортклуб'.\n" +
                    "Для входа в систему используйте логин и пароль выданные администратором.\n" +
                    "Спасибо за использование отечественного программного обеспечения!");
        });

    }


}
