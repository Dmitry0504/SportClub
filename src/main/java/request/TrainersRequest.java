package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Sportsman;
import model.Trainer;
import sample.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TrainersRequest {
    private static Connection connection = Controller.getConnection();

    private static ObservableList<Trainer> trainers = FXCollections.observableArrayList();

    private static ObservableList<Trainer> getTrainers(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trainers ORDER BY id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                trainers.add(new Trainer(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthday"),
                        resultSet.getInt("telephone")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    static TableView<Trainer> createTBTrainers(){
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
        return tableView;
    }
}
