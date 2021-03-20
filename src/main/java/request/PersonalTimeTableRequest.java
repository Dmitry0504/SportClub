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
import model.PersonalTimeTable;
import sample.Controller;
import sample.edit_controller.EditPersonalTimeTableController;
import support.MyContextMenu;

import java.io.IOException;
import java.sql.*;

public class PersonalTimeTableRequest {
    private static Connection connection = Controller.getConnection();

    private ObservableList<PersonalTimeTable> personalTimeTable = FXCollections.observableArrayList();

    private ObservableList<PersonalTimeTable> getPersonalTimeTables(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM personal_timetable ORDER BY id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                personalTimeTable.add(new PersonalTimeTable(
                        resultSet.getInt("id"),
                        resultSet.getInt("sportsmen_id"),
                        resultSet.getInt("training_id")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personalTimeTable;
    }

    TableView<PersonalTimeTable> createTBPersonalTimeTable(){
        TableView<PersonalTimeTable> tableView = new TableView<>(getPersonalTimeTables());

        TableColumn<PersonalTimeTable, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().add(id);

        TableColumn<PersonalTimeTable, Integer> trainer = new TableColumn<>("id члена клуба");
        trainer.setCellValueFactory(new PropertyValueFactory<>("sportsmen_id"));
        tableView.getColumns().add(trainer);

        TableColumn<PersonalTimeTable, Integer> exercise_id = new TableColumn<>("id занятия");
        exercise_id.setCellValueFactory(new PropertyValueFactory<>("training_id"));
        tableView.getColumns().add(exercise_id);

        tableView.setPrefWidth(573);

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    tableView.setContextMenu(MyContextMenu.personalTimeTableContext());
                    int lineNumber = tableView.getSelectionModel().getSelectedItem().getId();
                    EditPersonalTimeTableController.setId(lineNumber);
                }
            }
        });

        return tableView;
    }


}
