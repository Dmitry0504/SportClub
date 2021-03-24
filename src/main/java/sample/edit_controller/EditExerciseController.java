/**
 * Sample Skeleton for 'editExercise.fxml' Controller Class
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

public class EditExerciseController {
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

    @FXML // fx:id="title"
    private TextField title; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        setTextInput();
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
                        "UPDATE exercise SET title = ?, description = ? WHERE id = ?");

                preparedStatement.setString(1, title.getText().trim());
                preparedStatement.setString(2, exerciseDescription.getText().trim());
                preparedStatement.setString(3, String.valueOf(id));
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshExerciseTableView();
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
    public boolean checkingInput(){
        //проверка данных о занятии
        String description = exerciseDescription.getText();
        if(description.isEmpty()){
            AlertWindow.showAlertWithoutHeaderText(
                    "Поле 'Описание' не должно быть пустым!");
            return false;
        }
        String exerciseTitle = title.getText();
        if(exerciseTitle.isEmpty()){
            AlertWindow.showAlertWithoutHeaderText(
                    "Поле 'Название' не должно быть пустым!");
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
                MainSceneController.standardRequest.getExerciseTableView().getSelectionModel().getSelectedItem().getDescription());
        title.setText(
                MainSceneController.standardRequest.getExerciseTableView().getSelectionModel().getSelectedItem().getTitle());
    }

}
