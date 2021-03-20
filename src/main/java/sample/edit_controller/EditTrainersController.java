/**
 * Sample Skeleton for 'editTrainers.fxml' Controller Class
 */

package sample.edit_controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import support.AlertWindow;
import sample.Controller;
import sample.MainSceneController;

public class EditTrainersController {
    private static Connection connection = Controller.getConnection();
    private static int trainer_id;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="acceptBtn"
    private Button acceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="trainerSurname"
    private TextField trainerSurname; // Value injected by FXMLLoader

    @FXML // fx:id="trainerName"
    private TextField trainerName; // Value injected by FXMLLoader

    @FXML // fx:id="trainerBirthday"
    private TextField trainerBirthday; // Value injected by FXMLLoader

    @FXML // fx:id="trainerTelephone"
    private TextField trainerTelephone; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        setTextInput();

        acceptBtn.setOnAction(actionEvent -> {
            editTrainer();
        });

        cancelBtn.setOnAction(actionEvent ->{
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
    }

    //изменяем тренера
    private void editTrainer(){
        //проверяем ввод, если все нормально, то продолжаем
        if(checkingInput()) {
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;

                //запрос для изменения сведений о спортсмене
                preparedStatement = connection.prepareStatement(
                        "UPDATE trainers SET surname = ?, name = ?, birthday = ?, telephone = ? WHERE id = ?");

                preparedStatement.setString(1, trainerSurname.getText().trim());
                preparedStatement.setString(2, trainerName.getText().trim());
                preparedStatement.setString(3, trainerBirthday.getText().trim());
                preparedStatement.setString(4, trainerTelephone.getText().trim());
                preparedStatement.setString(5, String.valueOf(trainer_id));
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshTrainerTableView();
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
        //проверка данных тренера
        String trainerLastName = trainerSurname.getText().trim();
        if(!trainerLastName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Фамилия' тренера!\nПоле не должно содержать цифр!");
            return false;
        }
        String trainerFirstName = trainerName.getText();
        if(!trainerFirstName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Имя' тренера!\nПоле не должно содержать цифр!");
            return false;
        }
        String trainerBD = trainerBirthday.getText();
        if(!trainerBD.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Дата рождения' тренера!\nДата должна быть в формате - гггг-мм-дд!");
            return false;
        }
        String trainerTel = trainerTelephone.getText();
        if(!trainerTel.matches("^\\d{11}$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Телефон' тренера!\nНомер должен содержать 11 цифр и не может содержать спецсимволов!");
            return false;
        }

        return true;
    }

    public static void setTrainer_id(int lineNumber) {
        EditTrainersController.trainer_id = lineNumber;
    }

    private void setTextInput(){
        trainerSurname.setText(
                MainSceneController.standardRequest.getTrainerTableView().getSelectionModel().getSelectedItem().getSurname());
        trainerName.setText(
                MainSceneController.standardRequest.getTrainerTableView().getSelectionModel().getSelectedItem().getName());
        trainerBirthday.setText(
                MainSceneController.standardRequest.getTrainerTableView().getSelectionModel().getSelectedItem().getBirthday().toString());
        trainerTelephone.setText(
                MainSceneController.standardRequest.getTrainerTableView().getSelectionModel().getSelectedItem().getTelephone().toString());
    }
}
