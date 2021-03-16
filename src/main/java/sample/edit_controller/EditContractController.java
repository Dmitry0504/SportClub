/**
 * Sample Skeleton for 'editContract.fxml' Controller Class
 */

package sample.edit_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contract;
import request.ContractsRequest;
import request.StandardRequest;
import sample.AlertWindow;
import sample.Controller;
import sample.Main;
import sample.MainSceneController;

public class EditContractController {
    private static int i;
    private Connection connection = Controller.getConnection();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="acceptBtn"
    private Button acceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="contractDescription"
    private TextArea contractDescription; // Value injected by FXMLLoader

    @FXML // fx:id="contractConclusion"
    private TextField contractConclusion; // Value injected by FXMLLoader

    @FXML // fx:id="contractEnding"
    private TextField contractEnding; // Value injected by FXMLLoader

    @FXML // fx:id="contractCost"
    private TextField contractCost; // Value injected by FXMLLoader

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
                        "UPDATE contracts SET description = ?, conclusion = ?, ending = ?, cost = ? WHERE number = ?");

                preparedStatement.setString(1, contractDescription.getText().trim());
                preparedStatement.setString(2, contractConclusion.getText().trim());
                preparedStatement.setString(3, contractEnding.getText().trim());
                preparedStatement.setString(4, contractCost.getText().trim());
                preparedStatement.setString(5, String.valueOf(i));
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshContractTableView();
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
        //проверка данных о договоре
        String description = contractDescription.getText();
        if(description.isEmpty()){
            AlertWindow.showAlertWithoutHeaderText(
                    "Поле 'Описание' не должно быть пустым!");
            return false;
        }
        String conclusion = contractConclusion.getText();
        if(!conclusion.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Дата начала'!\nДата должна быть в формате - гггг-мм-дд!");
            return false;
        }
        String ending = contractEnding.getText();
        if(!ending.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Дата окончания'!\nДата должна быть в формате - гггг-мм-дд!");
            return false;
        }
        String cost = contractCost.getText();
        if(!cost.matches("^\\d+\\.?\\d*$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Стоимость'!\nПоле не должно содержать букв и спецсимволов, кроме '.'!");
            return false;
        }

        return true;
    }

    public static void setNumber(int x){
        i = x;
    }

}
