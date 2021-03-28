/**
 * Sample Skeleton for 'editPartners.fxml' Controller Class
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
import org.apache.log4j.Logger;
import support.AlertWindow;
import sample.Controller;
import sample.MainSceneController;

public class EditPartnersController {
    private static Connection connection = Controller.getConnection();
    private static int contragent_id;
    private static final Logger logger = Logger.getLogger(EditPartnersController.class);

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="acceptBtn"
    private Button acceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="partnerSurname"
    private TextField partnerSurname; // Value injected by FXMLLoader

    @FXML // fx:id="partnerName"
    private TextField partnerName; // Value injected by FXMLLoader

    @FXML // fx:id="partnerBirthday"
    private TextField partnerBirthday; // Value injected by FXMLLoader

    @FXML // fx:id="partnerTelephone"
    private TextField partnerTelephone; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        setTextInput();

        acceptBtn.setOnAction(actionEvent -> {
            editPartner();
        });

        cancelBtn.setOnAction(actionEvent ->{
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
    }

    //изменяем партнера
    private void editPartner(){
        //проверяем ввод, если все нормально, то продолжаем
        if(checkingInput()) {
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;

                //запрос для изменения сведений о партнере
                preparedStatement = connection.prepareStatement(
                        "UPDATE partners SET surname = ?, name = ?, birthday = ?, telephone = ? WHERE id = ?");

                preparedStatement.setString(1, partnerSurname.getText().trim());
                preparedStatement.setString(2, partnerName.getText().trim());
                preparedStatement.setString(3, partnerBirthday.getText().trim());
                preparedStatement.setString(4, partnerTelephone.getText().trim());
                preparedStatement.setString(5, String.valueOf(contragent_id));
                preparedStatement.executeUpdate();

                preparedStatement.close();

                MainSceneController.standardRequest.refreshPartnerTableView();
                AlertWindow.showAlertWithoutHeaderText("Данные успешно обновлены!");

                logger.info("Обновлены данные " + contragent_id);
                stage.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                AlertWindow.showAlertWithoutHeaderText("Если вы видете это сообщение, то обратитесь к администратору!");
            }
        }
    }

    //проверяем вводимые данные на соответствие шаблонам
    //и выводим сообщение в случае ошибки во введенных данных
    private boolean checkingInput(){
        //проверка данных контрагента
        String contragentSurname = partnerSurname.getText().trim();
        if(!contragentSurname.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Фамилия' контрагента!\nПоле не должно содержать цифр!");
            return false;
        }
        String contragentName = partnerName.getText();
        if(!contragentName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Имя' контрагента!\nПоле не должно содержать цифр!");
            return false;
        }
        String contragentBirthday = partnerBirthday.getText();
        if(!contragentBirthday.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Дата рождения' контрагента!\nДата должна быть в формате - гггг-мм-дд!");
            return false;
        }
        String contagentTelephone = partnerTelephone.getText();
        if(!contagentTelephone.matches("^\\d{11}$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Телефон' контрагента!\nНомер должен содержать 11 цифр и не может содержать спецсимволов!");
            return false;
        }

        return true;
    }

    public static int getContragentID() {
        return contragent_id;
    }

    public static void setContragentID(int lineNumber) {
        EditPartnersController.contragent_id = lineNumber;
    }

    private void setTextInput(){
        partnerSurname.setText(
                MainSceneController.standardRequest.getPartnerTableView().getSelectionModel().getSelectedItem().getSurname());
        partnerName.setText(
                MainSceneController.standardRequest.getPartnerTableView().getSelectionModel().getSelectedItem().getName());
        partnerBirthday.setText(
                MainSceneController.standardRequest.getPartnerTableView().getSelectionModel().getSelectedItem().getBirthday().toString());
        partnerTelephone.setText(
                MainSceneController.standardRequest.getPartnerTableView().getSelectionModel().getSelectedItem().getTelephone().toString());
    }
}
