/**
 * Sample Skeleton for 'addPersonalTimeTable.fxml' Controller Class
 */

package sample.add_controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import support.AlertWindow;
import sample.Controller;
import sample.MainSceneController;

public class AddPersonalTimeTableController {
    private Connection connection = Controller.getConnection();
    private static final Logger logger = Logger.getLogger(AddPersonalTimeTableController.class);

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="acceptBtn"
    private Button acceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exerciseID"
    private TextField exerciseID; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanID"
    private TextField sportsmanID; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        acceptBtn.setOnAction(actionEvent -> {
            editContract();
        });
        cancelBtn.setOnAction(actionEvent ->{
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
    }

    //изменяем договор
    private void editContract(){
        //проверяем ввод, если все нормально, то продолжаем
        if(checkingInput()) {
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;

                //запрос для изменения сведения о договоре
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO personal_timetable(sportsmen_id, training_id) VALUES (?, ?)");

                preparedStatement.setString(1, sportsmanID.getText().trim());
                preparedStatement.setString(2, exerciseID.getText().trim());
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshPersonalTimeTableTableView();
                AlertWindow.showAlertWithoutHeaderText("Данные успешно добавлены!");

                logger.info("Добавлена запись " + sportsmanID.getText() + " " + exerciseID.getText());

                stage.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                AlertWindow.showAlertWithoutHeaderText("Если вы видете это сообщение, то обратитесь к администратору!");
            }
        }
    }

    //проверяем вводимые данные на соответствие шаблонам
    //и выводим сообщение в случае ошибки во введенных данных
    public boolean checkingInput(){
        //проверка данных о занятии
        String sportsmen_id = sportsmanID.getText();
        if(!sportsmen_id.matches("^\\d+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'id члена клуба'!\nПоле должно содержать только цифры!");
            return false;
        }
        String training_id = exerciseID.getText();
        if(!training_id.matches("^\\d+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'id занятия'!\nПоле должно содержать только цифры!");
            return false;
        }

        return true;
    }
}
