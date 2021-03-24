/**
 * Sample Skeleton for 'mainScene.fxml' Controller Class
 */

package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import request.StandardRequest;
import support.AlertWindow;

public class MainSceneController {

    public static StandardRequest standardRequest = new StandardRequest();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="treeView"
    private TreeView<String> treeView; // Value injected by FXMLLoader

    @FXML // fx:id="ouputLayout"
    private AnchorPane ouputLayout; // Value injected by FXMLLoader

    @FXML // fx:id="addBtn"
    private Button addBtn; // Value injected by FXMLLoader

    @FXML // fx:id="addTrainerBtn"
    private Button addTrainerBtn; // Value injected by FXMLLoader

    @FXML // fx:id="addTrainingBtn"
    private Button addTrainingBtn; // Value injected by FXMLLoader

    @FXML // fx:id="signBtn"
    private Button signBtn; // Value injected by FXMLLoader

    @FXML
    private Button helpBtn2;

    private TableView<?> tb;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        TreeItem<String> partners = new TreeItem<>("Контрагенты");
        TreeItem<String> contracts = new TreeItem<>("Договоры");
        TreeItem<String> sportsmen = new TreeItem<>("Члены клуба");
        TreeItem<String> trainers = new TreeItem<>("Тренеры");
        TreeItem<String> exercise = new TreeItem<>("Занятия");
        TreeItem<String> timetable = new TreeItem<>("Расписание");
        TreeItem<String> personalTimetable = new TreeItem<>("Личное расписание");
        TreeItem<String> root = new TreeItem<>("Спортклуб");
        root.setExpanded(true);
        root.getChildren().addAll(
                contracts, partners, sportsmen, trainers, exercise, timetable, personalTimetable);
        treeView.setRoot(root);


        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {

                switch (t1.getValue()){
                    case "Контрагенты":
                        ouputLayout.getChildren().clear();
                        tb = standardRequest.getPartnerTableView();
                        if(tb.getItems().isEmpty()){
                            AlertWindow.showAlertWithoutHeaderText("Таблица пуста");
                            return;
                        }
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Договоры":
                        ouputLayout.getChildren().clear();
                        tb = standardRequest.getContractTableView();
                        if(tb.getItems().isEmpty()){
                            AlertWindow.showAlertWithoutHeaderText("Таблица пуста");
                            return;
                        }
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Члены клуба":
                        ouputLayout.getChildren().clear();
                        tb = standardRequest.getSportsmanTableView();
                        if(tb.getItems().isEmpty()){
                            AlertWindow.showAlertWithoutHeaderText("Таблица пуста");
                            return;
                        }
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Тренеры":
                        ouputLayout.getChildren().clear();
                        tb = standardRequest.getTrainerTableView();
                        if(tb.getItems().isEmpty()){
                            AlertWindow.showAlertWithoutHeaderText("Таблица пуста");
                            return;
                        }
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Занятия":
                        ouputLayout.getChildren().clear();
                        tb = standardRequest.getExerciseTableView();
                        if(tb.getItems().isEmpty()){
                            AlertWindow.showAlertWithoutHeaderText("Таблица пуста");
                            return;
                        }
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Расписание":
                        ouputLayout.getChildren().clear();
                        tb = standardRequest.getTimeTableTableView();
                        if(tb.getItems().isEmpty()){
                            AlertWindow.showAlertWithoutHeaderText("Таблица пуста");
                            return;
                        }
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Личное расписание":
                        ouputLayout.getChildren().clear();
                        tb = standardRequest.getPersonalTimeTableTableView();
                        if(tb.getItems().isEmpty()){
                            AlertWindow.showAlertWithoutHeaderText("Таблица пуста");
                            return;
                        }
                        ouputLayout.getChildren().add(tb);
                        break;
                    default:
                        ouputLayout.getChildren().clear();
                        break;
                }
            }
        });

        addBtn.setOnAction(actionEvent -> {
            //Stage stage = (Stage) addBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/additionalScenes/add_scenes/addingScene.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Создание нового договора");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addTrainerBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/additionalScenes/add_scenes/addTrainers.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Добавление нового тренера");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addTrainingBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/additionalScenes/add_scenes/addTimeTable.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Добавление новой записи в расписание");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        signBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/additionalScenes/add_scenes/addPersonalTimeTable.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Записать на занятие");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        helpBtn2.setOnAction(actionEvent -> {
            AlertWindow.showAlertWithoutHeaderText("В окне слева выберете интересующую Вас таблицу и\n" +
                    "в окне справа отобразится её содержимое,\n" +
                    "для изменения, удаления и получения дополнительной информации о записи\n" +
                    "вызовите контекстное меню нажав ПКМ на интересующей записи.\n" +
                    "Для добавления новых спортсменов, занятий, тренеров, записей в расписание\n" +
                    "воспользуйтесь соответствующими кнопками.\n" +
                    "Для обновления содержимого таблицы переместитесь между вкладками\n" +
                    "и откройте интересующую Вас таблицу вновь.\n" +
                    "Для получения более подробной информации обращайтесь к Вашему администратору.");
        });
    }

}
