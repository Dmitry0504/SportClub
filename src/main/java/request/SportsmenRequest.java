package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Sportsman;
import sample.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SportsmenRequest {
    private static Connection connection = Controller.getConnection();

    private static ObservableList<Sportsman> sportsmen = FXCollections.observableArrayList();

    private static ObservableList<Sportsman> getSportsmen(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sportsmen ORDER BY id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                sportsmen.add(new Sportsman(
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
        return sportsmen;
    }

    static TableView<Sportsman> createTBSportsmen(){
        TableView<Sportsman> tableView = new TableView<>(getSportsmen());

        TableColumn<Sportsman, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().add(id);

        TableColumn<Sportsman, String> surname = new TableColumn<>("Фамилия");
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tableView.getColumns().add(surname);

        TableColumn<Sportsman, String> name = new TableColumn<>("Имя");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(name);

        TableColumn<Sportsman, Date> birthday = new TableColumn<>("Дата рождения");
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableView.getColumns().add(birthday);

        TableColumn<Sportsman, Integer> telephone = new TableColumn<>("Телефон");
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        tableView.getColumns().add(telephone);

        tableView.setPrefWidth(573);
        return tableView;
    }
}
