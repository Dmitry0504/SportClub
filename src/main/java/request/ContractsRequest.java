package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Contract;
import sample.Controller;

import java.sql.*;

public class ContractsRequest {
    private static Connection connection = Controller.getConnection();

    private static ObservableList<Contract> contracts = FXCollections.observableArrayList();

    private static ObservableList<Contract> getContracts(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contracts ORDER BY number");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                contracts.add(new Contract(
                        resultSet.getInt("number"),
                        resultSet.getInt("partner"),
                        resultSet.getString("description"),
                        resultSet.getDate("conclusion"),
                        resultSet.getDate("ending"),
                        resultSet.getDouble("cost")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    static TableView<Contract> createTBContracts(){
        TableView<Contract> tableView = new TableView<>(getContracts());

        TableColumn<Contract, Integer> id = new TableColumn<>("Номер");
        id.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableView.getColumns().add(id);

        TableColumn<Contract, Integer> partner_id = new TableColumn<>("Партнер");
        partner_id.setCellValueFactory(new PropertyValueFactory<>("partner"));
        tableView.getColumns().add(partner_id);

        TableColumn<Contract, String> description = new TableColumn<>("Описание");
        description.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Contract, String> call(TableColumn<Contract, String> arg0) {
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

        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.getColumns().add(description);

        TableColumn<Contract, Date> conclusion = new TableColumn<>("Дата начала");
        conclusion.setCellValueFactory(new PropertyValueFactory<>("conclusion"));
        tableView.getColumns().add(conclusion);

        TableColumn<Contract, Date> ending = new TableColumn<>("Дата окончания");
        ending.setCellValueFactory(new PropertyValueFactory<>("ending"));
        tableView.getColumns().add(ending);

        TableColumn<Contract, Double> cost = new TableColumn<>("Стоимость");
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        tableView.getColumns().add(cost);

        tableView.setPrefWidth(573);
        return tableView;
    }
}
