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
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(NewContractController.class);

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

    //?????????????????? ?????????? ??????????????
    private void createContract(){
        //?????????????????? ????????, ???????? ?????? ??????????????????, ???? ????????????????????
        if(checkingInput()) {
            Stage stage = (Stage) createBtn.getScene().getWindow();
            try {
                PreparedStatement preparedStatement;
                //?????????????? ???????????? ???????????? ?????? ???????????????????? ???????????????? ?? ??????????????????????
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO partners(surname, name, birthday, telephone) VALUES (?, ?, ?, ?)");

                preparedStatement.setString(1, contragentSurname.getText().trim());
                preparedStatement.setString(2, contragentName.getText().trim());
                preparedStatement.setString(3, contragentBirthday.getText().trim());
                preparedStatement.setString(4, contagentTelephone.getText().trim());

                preparedStatement.executeUpdate();

                //???????????? ???????????? ?????? ???????????????????? ???????????????? ?? ????????????????
                //???????????????????????????? ???????????????? ?????????? ?????????????????? ???????????? ?? ?????????????? ??????????????????????
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

                //???????????? ???????????? - ?????? ???????????????????? ?????????????? ?? ?????????????? ????????????????????
                //???????????????????????????? ???????????????? ???????????????? ?? ?????????????????? ???????????? ???????????????? - ???? ?????????? id ????????????????????
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

                AlertWindow.showAlertWithoutHeaderText("???????????? ?????????????? ??????????????????!");

                logger.info("???????????????? ?????????? ??????????????. ??????????????????: " + sportsmanSurname.getText().trim() + " " + sportsmanName.getText().trim());
                MainSceneController.standardRequest.refreshAll();
                //???????????????????????? ???? ???????????????????? ????????????????
                returnToPrevStage();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                AlertWindow.showAlertWithoutHeaderText("???????? ???? ???????????? ?????? ??????????????????, ???? ???????????????????? ?? ????????????????????????????!");
            }
        }
    }

    //?????????????????? ???? ???????????????????? ????????????????
    private void returnToPrevStage(){
        Stage stage = (Stage) createBtn.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //?????????????????? ???????????????? ???????????? ???? ???????????????????????? ????????????????
    //?? ?????????????? ?????????????????? ?? ???????????? ???????????? ???? ?????????????????? ????????????
    public boolean checkingInput(){
        //???????????????? ???????????? ??????????????????????
        String partnerSurname = contragentSurname.getText().trim();
        if(!partnerSurname.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '??????????????' ??????????????????????!\n???????? ???? ???????????? ?????????????????? ????????!");
            return false;
        }
        String partnerName = contragentName.getText();
        if(!partnerName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '??????' ??????????????????????!\n???????? ???? ???????????? ?????????????????? ????????!");
            return false;
        }
        String partnerBirthday = contragentBirthday.getText();
        if(!partnerBirthday.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '???????? ????????????????' ??????????????????????!\n???????? ???????????? ???????? ?? ?????????????? - ????????-????-????!");
            return false;
        }
        String partnerTelephone = contagentTelephone.getText();
        if(!partnerTelephone.matches("^\\d{11}$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '??????????????' ??????????????????????!\n?????????? ???????????? ?????????????????? 11 ???????? ?? ???? ?????????? ?????????????????? ????????????????????????!");
            return false;
        }

        //???????????????? ???????????? ?? ????????????????
        String description = contractDescription.getText();
        if(description.isEmpty()){
            AlertWindow.showAlertWithoutHeaderText(
                    "???????? '????????????????' ???? ???????????? ???????? ????????????!");
            return false;
        }
        String conclusion = contractConclusion.getText();
        if(!conclusion.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '???????? ????????????'!\n???????? ???????????? ???????? ?? ?????????????? - ????????-????-????!");
            return false;
        }
        String ending = contractEnding.getText();
        if(!ending.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '???????? ??????????????????'!\n???????? ???????????? ???????? ?? ?????????????? - ????????-????-????!");
            return false;
        }
        String cost = contractCost.getText();
        if(!cost.matches("^\\d+\\.?\\d*$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '??????????????????'!\n???????? ???? ???????????? ?????????????????? ???????? ?? ????????????????????????, ?????????? '.'!");
            return false;
        }

        //???????????????? ???????????? ?? ????????????????????
        String sportsmanLastName = sportsmanSurname.getText();
        if(!sportsmanLastName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '??????????????' ????????????????????!\n???????? ???? ???????????? ?????????????????? ????????!");
            return false;
        }
        String sportsmanFirstName = sportsmanName.getText();
        if(!sportsmanFirstName.matches("^\\D+\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '??????' ????????????????????!\n???????? ???? ???????????? ?????????????????? ????????!");
            return false;
        }
        String sportsmanBD = sportsmanBirthday.getText();
        if(!sportsmanBD.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\b")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '???????? ????????????????' ????????????????????!\n???????? ???????????? ???????? ?? ?????????????? - ????????-????-????!");
            return false;
        }
        String sportsmanTel = sportsmanTelephone.getText();
        if(!sportsmanTel.matches("^\\d{11}$")){
            AlertWindow.showAlertWithoutHeaderText(
                    "?????????????????? ???????? '??????????????' ????????????????????!\n?????????? ???????????? ?????????????????? 11 ???????? ?? ???? ?????????? ?????????????????? ????????????????????????!");
            return false;
        }

        return true;
    }
}
