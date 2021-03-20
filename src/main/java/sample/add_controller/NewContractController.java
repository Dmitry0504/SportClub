/**
 * Sample Skeleton for 'addingScene.fxml' Controller Class
 */

package sample.add_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import support.AlertWindow;
import sample.Controller;
import sample.MainSceneController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewContractController {
    private static Connection connection = Controller.getConnection();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="contragentSurname"
    private TextField contragentSurname; // Value injected by FXMLLoader

    @FXML // fx:id="contragentName"
    private TextField contragentName; // Value injected by FXMLLoader

    @FXML // fx:id="contragentBirthday"
    private TextField contragentBirthday; // Value injected by FXMLLoader

    @FXML // fx:id="contagentTelephone"
    private TextField contagentTelephone; // Value injected by FXMLLoader

    @FXML // fx:id="contractDescription"
    private TextArea contractDescription; // Value injected by FXMLLoader

    @FXML // fx:id="contractConclusion"
    private TextField contractConclusion; // Value injected by FXMLLoader

    @FXML // fx:id="contractEnding"
    private TextField contractEnding; // Value injected by FXMLLoader

    @FXML // fx:id="contractCost"
    private TextField contractCost; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanSurname"
    private TextField sportsmanSurname; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanName"
    private TextField sportsmanName; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanBirthday"
    private TextField sportsmanBirthday; // Value injected by FXMLLoader

    @FXML // fx:id="sportsmanTelephone"
    private TextField sportsmanTelephone; // Value injected by FXMLLoader

    @FXML // fx:id="createBtn"
    private Button createBtn; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        createBtn.setOnAction(e -> {
            createContract();

        });
        cancelBtn.setOnAction(e -> {
            returnToPrevStage();
        });

    }

    //добавляем новый договор
    private void createContract(){
        //проверяем ввод, если все нормально, то продолжаем
        if(checkingInput()) {
            Stage stage = (Stage) createBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;
                //создаем первый запрос для добавления сведений о контрагенте
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO partners(surname, name, birthday, telephone) VALUES (?, ?, ?, ?)");

                preparedStatement.setString(1, contragentSurname.getText().trim());
                preparedStatement.setString(2, contragentName.getText().trim());
                preparedStatement.setString(3, contragentBirthday.getText().trim());
                preparedStatement.setString(4, contagentTelephone.getText().trim());

                preparedStatement.executeUpdate();

                //второй запрос для добавления сведения о договоре
                //предварительно получаем номер последней записи в таблице контрагенты
                preparedStatement = connection.prepareStatement("SELECT MAX(id) as id FROM partners");
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                int number = rs.getInt("id");
                rs.close();

                preparedStatement = connection.prepareStatement(
                        "INSERT INTO contracts(partner, description, conclusion, ending, cost) VALUES (?, ?, ?, ?, ?)");

                preparedStatement.setString(1, String.valueOf(number));
                preparedStatement.setString(2, contractDescription.getText().trim());
                preparedStatement.setString(3, contractConclusion.getText().trim());
                preparedStatement.setString(4, contractEnding.getText().trim());
                preparedStatement.setString(5, contractCost.getText().trim());

                preparedStatement.executeUpdate();

                //третий запрос - для добавления записей в таблицу Спортсмены
                //предварительно получаем сведения о последнем номере договора - он будет id спортсмена
                preparedStatement = connection.prepareStatement("SELECT MAX(number) as number FROM contracts");
                rs = preparedStatement.executeQuery();
                rs.next();
                number = rs.getInt("number");
                rs.close();

                preparedStatement = connection.prepareStatement(
                        "INSERT INTO sportsmen(id, surname, name, birthday, telephone) VALUES (?, ?, ?, ?, ?)");

                preparedStatement.setString(1, String.valueOf(number));
                preparedStatement.setString(2, sportsmanSurname.getText().trim());
                preparedStatement.setString(3, sportsmanName.getText().trim());
                preparedStatement.setString(4, sportsmanBirthday.getText().trim());
                preparedStatement.setString(5, sportsmanTelephone.getText().trim());

                preparedStatement.executeUpdate();

                preparedStatement.close();

                AlertWindow.showAlertWithoutHeaderText("Данные успешно добавлены!");
                MainSceneController.standardRequest.refreshAll();
                //возвращаемся на предыдущую страницу
                returnToPrevStage();
            } catch (SQLException e) {
                e.printStackTrace();
                AlertWindow.showAlertWithoutHeaderText("Если вы видете это сообщение, то обратитесь к администратору!");
            }
        }
    }

    //вовращает на предыдущую страницу
    private void returnToPrevStage(){
        Stage stage = (Stage) createBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //проверяем вводимые данные на соответствие шаблонам
    //и выводим сообщение в случае ошибки во введенных данных
    public boolean checkingInput(){
        //проверка данных контрагента
        String partnerSurname = contragentSurname.getText().trim();
        if(!partnerSurname.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Фамилия' контрагента!\nПоле не должно содержать цифр!");
            return false;
        }
        String partnerName = contragentName.getText();
        if(!partnerName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Имя' контрагента!\nПоле не должно содержать цифр!");
            return false;
        }
        String partnerBirthday = contragentBirthday.getText();
        if(!partnerBirthday.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Дата рождения' контрагента!\nДата должна быть в формате - гггг-мм-дд!");
            return false;
        }
        String partnerTelephone = contagentTelephone.getText();
        if(!partnerTelephone.matches("^\\d{11}$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "Проверьте поле 'Телефон' контрагента!\nНомер должен содержать 11 цифр и не может содержать спецсимволов!");
            return false;
        }

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

        //проверка данных о спортсмене
        String sportsmanLastName = sportsmanSurname.getText();
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
}
