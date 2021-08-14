package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Partner;
import sample.Controller;
import sample.edit_controller.EditPartnersController;
import support.MyContextMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class PartnerRequest {
    //получаем соединение с базой данных от класса контроллера окна авторизации
    private static Connection connection = Controller.getConnection();
    //инициализация списка контрагентов
    private ObservableList<Partner> partners = FXCollections.observableArrayList();
    //получение данных из БД и заполнение списка
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
    //создание табличного представления
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
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    tableView.setContextMenu(MyContextMenu.partnerContext());
                    int lineNumber = tableView.getSelectionModel().getSelectedItem().getId();
                    EditPartnersController.setContragentID(lineNumber);
                }
            }
        });
        return tableView;
    }
}
