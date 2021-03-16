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

    @FXML // fx:id="editBtn"
    private Button editBtn; // Value injected by FXMLLoader

    @FXML // fx:id="deleteBtn"
    private Button deleteBtn; // Value injected by FXMLLoader

    private TableView<?> tb;

    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        TreeItem<String> partners = new TreeItem<>("Контрагенты");
        TreeItem<String> contracts = new TreeItem<>("Договоры");
        TreeItem<String> sportsmen = new TreeItem<>("Члены клуба");
        TreeItem<String> trainers = new TreeItem<>("Тренеры");
        TreeItem<String> exercise = new TreeItem<>("Занятия");
        TreeItem<String> timetable = new TreeItem<>("Расписание");
        TreeItem<String> root = new TreeItem<>("Спортклуб");
        root.setExpanded(true);
        root.getChildren().addAll(
                contracts, partners, sportsmen, trainers, exercise, timetable);
        treeView.setRoot(root);


        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {

                switch (t1.getValue()){
                    case "Контрагенты":
                        ouputLayout.getChildren().clear();
                        tb = StandardRequest.getPartnerTableView();
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Договоры":
                        ouputLayout.getChildren().clear();
                        tb = new StandardRequest().getContractTableView();
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Члены клуба":
                        ouputLayout.getChildren().clear();
                        tb = StandardRequest.getSportsmanTableView();
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Тренеры":
                        ouputLayout.getChildren().clear();
                        tb = StandardRequest.getTrainerTableView();
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Занятия":
                        ouputLayout.getChildren().clear();
                        tb = StandardRequest.getExerciseTableView();
                        ouputLayout.getChildren().add(tb);
                        break;
                    case "Расписание":
                        ouputLayout.getChildren().clear();
                        tb = StandardRequest.getTimeTableTableView();
                        ouputLayout.getChildren().add(tb);
                        break;
                    default:
                        ouputLayout.getChildren().clear();
                        break;
                }
            }
        });

        addBtn.setOnAction(actionEvent -> {
            Stage stage = (Stage) addBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addingScene.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Создание нового договора");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteBtn.setOnAction(actionEvent -> {

        });
    }

}
