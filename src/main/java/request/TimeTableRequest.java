package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.TimeTable;
import sample.Controller;
import sample.edit_controller.EditTimeTableController;
import support.MyContextMenu;

import java.io.IOException;
import java.sql.*;

public class TimeTableRequest {
    private static Connection connection = Controller.getConnection();

    private ObservableList<TimeTable> timeTables = FXCollections.observableArrayList();

    private ObservableList<TimeTable> getTimeTables(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM timetable ORDER BY id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                timeTables.add(new TimeTable(
                        resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getTime("time"),
                        resultSet.getString("description"),
                        resultSet.getInt("trainer"),
                        resultSet.getInt("exercise_id")

                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeTables;
    }

    TableView<TimeTable> createTBTimeTable(){
        TableView<TimeTable> tableView = new TableView<>(getTimeTables());

        TableColumn<TimeTable, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().add(id);

        TableColumn<TimeTable, Date> date = new TableColumn<>("Дата");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.getColumns().add(date);

        TableColumn<TimeTable, Time> time = new TableColumn<>("Время");
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        tableView.getColumns().add(time);

        TableColumn<TimeTable, String> description = new TableColumn<>("Описание");

        description.setCellFactory(new Callback<>() {
            @Override
            public TableCell<TimeTable, String> call(TableColumn<TimeTable, String> arg0) {
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
        PropertyValueFactory<TimeTable, String> propertyValueFactory = new PropertyValueFactory<>("description");
        description.setCellValueFactory(propertyValueFactory);
        tableView.getColumns().add(description);

        TableColumn<TimeTable, Integer> trainer = new TableColumn<>("id тренера");
        trainer.setCellValueFactory(new PropertyValueFactory<>("trainer"));
        tableView.getColumns().add(trainer);

        TableColumn<TimeTable, Integer> exercise_id = new TableColumn<>("id занятия");
        exercise_id.setCellValueFactory(new PropertyValueFactory<>("exercise_id"));
        tableView.getColumns().add(exercise_id);

        tableView.setPrefWidth(573);

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    tableView.setContextMenu(MyContextMenu.timeTableContext());
                    int lineNumber = tableView.getSelectionModel().getSelectedItem().getId();
                    EditTimeTableController.setId(lineNumber);
                }
            }
        });

        return tableView;
    }


}
