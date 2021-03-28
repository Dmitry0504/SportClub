/**
 * Sample Skeleton for 'addTimeTable.fxml' Controller Class
 */

package sample.add_controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import support.AlertWindow;
import sample.Controller;
import sample.MainSceneController;

public class AddTimeTableController {
    private Connection connection = Controller.getConnection();
    private static final Logger logger = Logger.getLogger(AddTimeTableController.class);

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="acceptBtn"
    private Button acceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exerciseDescription"
    private TextArea exerciseDescription; // Value injected by FXMLLoader

    @FXML // fx:id="exerciseDate"
    private TextField exerciseDate; // Value injected by FXMLLoader

    @FXML // fx:id="exerciseTime"
    private TextField exerciseTime; // Value injected by FXMLLoader

    @FXML // fx:id="trainerID"
    private TextField trainerID; // Value injected by FXMLLoader

    @FXML // fx:id="exerciseID"
    private TextField exerciseID; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        acceptBtn.setOnAction(actionEvent -> {
            addTimeTable();
        });
        cancelBtn.setOnAction(actionEvent ->{
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
    }

    //добавляем запись в расписание
    private void addTimeTable(){
        //проверяем ввод, если все нормально, то продолжаем
        if(checkingInput()) {
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;

                //запрос для изменения сведения о договоре
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO timetable(date, time, description, trainer, exercise_id) VALUES  (?, ?, ?, ?, ?)");

                preparedStatement.setString(1, exerciseDate.getText().trim());
                preparedStatement.setString(2, exerciseTime.getText().trim());
                preparedStatement.setString(3, exerciseDescription.getText().trim());
                preparedStatement.setString(4, trainerID.getText().trim());
                preparedStatement.setString(5, exerciseID.getText().trim());
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshTimeTableTableView();
                AlertWindow.showAlertWithoutHeaderText("Данные успешно добавлены!");

                logger.info("Добавлена запись: "
                        + exerciseDate.getText().trim() + " "
                        + exerciseTime.getText().trim() + " "
                        + exerciseDescription.getText().trim());
                stage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                AlertWindow.showAlertWithoutHeaderText("Если вы видете это сообщение, то обратитесь к администратору!");
            }
        }
    }

    //проверяем вводимые данные на соответствие шаблонам
    //и выводим сообщение в случае ошибки во введенных данных
    public boolean checkingInput(){
        //проверка данных о пункте расписания
        String description = exerciseDescription.getText();
        if(description.isEmpty()){
            AlertWindow.showAlertWithoutHeaderText(
                    "Поле 'Описание' не должно быть пустым!");
            return false;
        }
        String date = exerciseDate.getText();
        if(!date.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Дата'!\nДата должна быть в формате - гггг-мм-дд!");
            return false;
        }
        String time = exerciseTime.getText();
        if(!time.matches("^\\d\\d:\\d\\d:\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Время'!\nВремя должно быть в формате - hh:mm:ss!");
            return false;
        }
        String trainerID_text = trainerID.getText();
        if(!trainerID_text.matches("^\\d+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'id тренера'!\nПоле должно содержать только цифры!");
            return false;
        }
        String exerciseID_text = exerciseID.getText();
        if(!exerciseID_text.matches("^\\d+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'id занятия'!\nПоле должно содержать только цифры!");
            return false;
        }

        return true;
    }
}
