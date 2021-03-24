/**
 * Sample Skeleton for 'editTimeTable.fxml' Controller Class
 */

package sample.edit_controller;

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
import support.AlertWindow;
import sample.Controller;
import sample.MainSceneController;

public class EditTimeTableController {
    private static int id;
    private Connection connection = Controller.getConnection();

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
        setTextInput();
        acceptBtn.setOnAction(actionEvent -> {
            editTimeTable();
        });
        cancelBtn.setOnAction(actionEvent ->{
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
    }

    //изменяем расписание
    private void editTimeTable(){
        //проверяем ввод, если все нормально, то продолжаем
        if(checkingInput()) {
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;

                //запрос для изменения сведения о договоре
                preparedStatement = connection.prepareStatement(
                        "UPDATE timetable SET date = ?, time = ?, description = ?, trainer = ?, exercise_id = ? WHERE id = ?");

                preparedStatement.setString(1, exerciseDate.getText().trim());
                preparedStatement.setString(2, exerciseTime.getText().trim());
                preparedStatement.setString(3, exerciseDescription.getText().trim());
                preparedStatement.setString(4, trainerID.getText().trim());
                preparedStatement.setString(5, exerciseID.getText().trim());
                preparedStatement.setString(6, String.valueOf(id));
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshTimeTableTableView();
                AlertWindow.showAlertWithoutHeaderText("Данные успешно обновлены!");

                stage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                AlertWindow.showAlertWithoutHeaderText("Если вы видете это сообщение, то обратитесь к администратору!");
            }
        }
    }

    //проверяем вводимые данные на соответствие шаблонам
    //и выводим сообщение в случае ошибки во введенных данных
    private boolean checkingInput(){
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

    public static int getId(){
        return id;
    }

    public static void setId(int x){
        id = x;
    }

    private void setTextInput(){
        exerciseDescription.setText(
                MainSceneController.standardRequest.getTimeTableTableView().getSelectionModel().getSelectedItem().getDescription());
        exerciseDate.setText(
                MainSceneController.standardRequest.getTimeTableTableView().getSelectionModel().getSelectedItem().getDate().toString());
        exerciseTime.setText(
                MainSceneController.standardRequest.getTimeTableTableView().getSelectionModel().getSelectedItem().getTime().toString());
        exerciseID.setText(String.valueOf(
                MainSceneController.standardRequest.getTimeTableTableView().getSelectionModel().getSelectedItem().getExercise_id()));
        trainerID.setText(String.valueOf(
                MainSceneController.standardRequest.getTimeTableTableView().getSelectionModel().getSelectedItem().getTrainer()));
    }
}
