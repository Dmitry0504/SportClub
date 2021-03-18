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
import model.Partner;
import model.Trainer;
import sample.Controller;
import sample.edit_controller.EditContractController;
import sample.edit_controller.EditPartnersController;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class PartnerRequest {
    //запрос полной инфы по спортсмену(договору)
    //select partners.id, partners.surname, partners.name, contracts.description,
    // contracts.cost, contracts.conclusion, contracts.ending, sportsmen.surname,
    // sportsmen.name, sportsmen.id from partners join contracts on partners.id = contracts.partner
    // join sportsmen on contracts.number = sportsmen.id where sportsmen.id = 1;
    private static Connection connection = Controller.getConnection();

    private ObservableList<Partner> partners = FXCollections.observableArrayList();

    private ObservableList<Partner> getPartners(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM partners ORDER BY id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                partners.add(new Partner(
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
        return partners;
    }

    TableView<Partner> createTBPartners(){
        TableView<Partner> tableView = new TableView<>(getPartners());


        TableColumn<Partner, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().add(id);

        TableColumn<Partner, String> surname = new TableColumn<>("Фамилия");
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tableView.getColumns().add(surname);

        TableColumn<Partner, String> name = new TableColumn<>("Имя");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(name);

        TableColumn<Partner, Date> birthday = new TableColumn<>("Дата рождения");
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableView.getColumns().add(birthday);

        TableColumn<Partner, Integer> telephone = new TableColumn<>("Телефон");
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        tableView.getColumns().add(telephone);

        tableView.setPrefWidth(573);

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 1){
                        int lineNumber = tableView.getSelectionModel().getSelectedItem().getId();
                        String lastName = tableView.getSelectionModel().getSelectedItem().getSurname();
                        String firstName = tableView.getSelectionModel().getSelectedItem().getName();
                        Date birthday = tableView.getSelectionModel().getSelectedItem().getBirthday();
                        BigDecimal telephone = tableView.getSelectionModel().getSelectedItem().getTelephone();
                        EditPartnersController.setContragentID(lineNumber);

                    }
                    if(mouseEvent.getClickCount() == 2){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                                "/additionalScenes/editPartners.fxml"));
                        Parent root1;
                        try {
                            root1 = fxmlLoader.load();
                            Stage addStage = new Stage();
                            addStage.initModality(Modality.WINDOW_MODAL);
                            addStage.setTitle("Изменение данных контрагента");
                            addStage.setScene(new Scene(root1));
                            addStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        return tableView;
    }
}
