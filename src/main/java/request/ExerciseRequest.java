package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Exercise;
import sample.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseRequest {
    private static Connection connection = Controller.getConnection();

    private ObservableList<Exercise> exercises = FXCollections.observableArrayList();

    private ObservableList<Exercise> getExercises(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM exercise ORDER BY id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                exercises.add(new Exercise(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description")
                        ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    TableView<Exercise> createTBExercise(){
        TableView<Exercise> tableView = new TableView<>(getExercises());

        TableColumn<Exercise, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().add(id);

        TableColumn<Exercise, String> title = new TableColumn<>("Название");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.getColumns().add(title);

        TableColumn<Exercise, String> description = new TableColumn<>("Описание");

        description.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Exercise, String> call(TableColumn<Exercise, String> arg0) {
                return new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            Text text = new Text(item);
                            text.setWrappingWidth(description.getWidth());
                            this.setWrapText(true);

                            setGraphic(text);
                        }
                    }
                };
            }
        });

        PropertyValueFactory<Exercise, String> propertyValueFactory = new PropertyValueFactory<>("description");

        description.setCellValueFactory(propertyValueFactory);
        description.setPrefWidth(200);

        tableView.getColumns().add(description);

        tableView.setPrefWidth(573);
        return tableView;
    }

}
