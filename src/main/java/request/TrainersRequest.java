package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Trainer;
import sample.Controller;
import sample.edit_controller.EditTrainersController;
import support.MyContextMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TrainersRequest {
    private static Connection connection = Controller.getConnection();

    private ObservableList<Trainer> trainers = FXCollections.observableArrayList();

    private ObservableList<Trainer> getTrainers(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trainers ORDER BY id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                trainers.add(new Trainer(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthday"),
                        resultSet.getBigDecimal("telephone")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    TableView<Trainer> createTBTrainers(){
        TableView<Trainer> tableView = new TableView<>(getTrainers());


        TableColumn<Trainer, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().add(id);

        TableColumn<Trainer, String> surname = new TableColumn<>("Фамилия");
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tableView.getColumns().add(surname);

        TableColumn<Trainer, String> name = new TableColumn<>("Имя");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(name);

        TableColumn<Trainer, Date> birthday = new TableColumn<>("Дата рождения");
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableView.getColumns().add(birthday);

        TableColumn<Trainer, Integer> telephone = new TableColumn<>("Телефон");
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        tableView.getColumns().add(telephone);

        tableView.setPrefWidth(573);

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    tableView.setContextMenu(MyContextMenu.trainersContext());
                    int lineNumber = tableView.getSelectionModel().getSelectedItem().getId();
                    EditTrainersController.setTrainer_id(lineNumber);
                }
            }
        });
        return tableView;
    }
}
