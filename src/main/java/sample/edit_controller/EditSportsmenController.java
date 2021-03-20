/**
 * Sample Skeleton for 'editSportsmen.fxml' Controller Class
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

public class EditSportsmenController {
    private static Connection connection = Controller.getConnection();
    private static int sportsman_id;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="acceptBtn"
    private Button acceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanSurname"
    private TextField sportsmanSurname; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanName"
    private TextField sportsmanName; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanBirthday"
    private TextField sportsmanBirthday; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanTelephone"
    private TextField sportsmanTelephone; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        setTextInput();

        acceptBtn.setOnAction(actionEvent -> {
            editSportsman();
        });

        cancelBtn.setOnAction(actionEvent ->{
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
    }

    //изменяем партнера
    private void editSportsman(){
        //проверяем ввод, если все нормально, то продолжаем
        if(checkingInput()) {
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;

                //запрос для изменения сведений о спортсмене
                preparedStatement = connection.prepareStatement(
                        "UPDATE sportsmen SET surname = ?, name = ?, birthday = ?, telephone = ? WHERE id = ?");

                preparedStatement.setString(1, sportsmanSurname.getText().trim());
                preparedStatement.setString(2, sportsmanName.getText().trim());
                preparedStatement.setString(3, sportsmanBirthday.getText().trim());
                preparedStatement.setString(4, sportsmanTelephone.getText().trim());
                preparedStatement.setString(5, String.valueOf(sportsman_id));
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshSportsmanTableView();
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
        //проверка данных спортсмена
        String sportsmanLastName = sportsmanSurname.getText().trim();
        if(!sportsmanLastName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Фамилия' спортсмена!\nПоле не должно содержать цифр!");
            return false;
        }
        String sportsmanFirstName = sportsmanName.getText();
        if(!sportsmanFirstName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Имя' спортсмена!\nПоле не должно содержать цифр!");
            return false;
        }
        String sportsmanBD = sportsmanBirthday.getText();
        if(!sportsmanBD.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Дата рождения' спортсмена!\nДата должна быть в формате - гггг-мм-дд!");
            return false;
        }
        String sportsmanTel = sportsmanTelephone.getText();
        if(!sportsmanTel.matches("^\\d{11}$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Телефон' спортсмена!\nНомер должен содержать 11 цифр и не может содержать спецсимволов!");
            return false;
        }

        return true;
    }

    public static int getSportsman_id() {
        return sportsman_id;
    }

    public static void setSportsman_id(int lineNumber) {
        EditSportsmenController.sportsman_id = lineNumber;
    }

    private void setTextInput(){
        sportsmanSurname.setText(
                MainSceneController.standardRequest.getSportsmanTableView().getSelectionModel().getSelectedItem().getSurname());
        sportsmanName.setText(
                MainSceneController.standardRequest.getSportsmanTableView().getSelectionModel().getSelectedItem().getName());
        sportsmanBirthday.setText(
                MainSceneController.standardRequest.getSportsmanTableView().getSelectionModel().getSelectedItem().getBirthday().toString());
        sportsmanTelephone.setText(
                MainSceneController.standardRequest.getSportsmanTableView().getSelectionModel().getSelectedItem().getTelephone().toString());
    }
}
