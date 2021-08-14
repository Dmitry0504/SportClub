package support;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controller;
import sample.MainSceneController;
import sample.edit_controller.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 Класс создает контекстное меню для записей в таблицах и
 устанавливает обработчики для каждого пункта меню
 */
public class MyContextMenu {
    private static Connection connection = Controller.getConnection();
    private static FXMLLoader fxmlLoader;

    public static ContextMenu sportsmanContext(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("Дополнительно");
        MenuItem change = new MenuItem("Изменить");
        MenuItem delete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(change, info, delete);

        info.setOnAction(e ->{
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "select partners.id, partners.surname, partners.name, contracts.description,\n" +
                                "contracts.cost, contracts.conclusion, contracts.ending, sportsmen.surname,\n" +
                                "sportsmen.name from partners join contracts on partners.id = contracts.partner\n" +
                                "join sportsmen on contracts.number = sportsmen.id where sportsmen.id = ?;");

                preparedStatement.setInt(1, EditSportsmenController.getSportsman_id());

                ResultSet resultSet = preparedStatement.executeQuery();
                StringBuilder sb = new StringBuilder("id контрагента - ");
                while (resultSet.next()){
                    sb.append(resultSet.getInt("id")).append(".\nФамилия контрагента - ");
                    sb.append(resultSet.getString("surname")).append("\nИмя контрагента - ");
                    sb.append(resultSet.getString("name")).append(".\nОписание договора:\n");
                    sb.append(resultSet.getString("description")).append(".\nСтоимость - ");
                    sb.append(resultSet.getDouble("cost")).append(".\nДата заключения - ");
                    sb.append(resultSet.getDate("conclusion")).append(".\nДата окончания - ");
                    sb.append(resultSet.getDate("ending")).append(".\nФамилия члена клуба - ");
                    sb.append(resultSet.getString("surname")).append(".\nИмя члена клуба - ");
                    sb.append(resultSet.getString("name")).append(".");
                }
                AlertWindow.showAlertWithoutHeaderText(sb.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        change.setOnAction(e -> {
            fxmlLoader = new FXMLLoader(MyContextMenu.class.getResource(
                    "/additionalScenes/edit_scenes/editSportsmen.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Изменение данных члена клуба");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e ->{
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Вместе с этой записью вы также удалите сведения о договоре и контрагенте.\n" +
                            "Вы действительно хотите удалить выбранную запись?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "delete from partners where id = (select partner from contracts where number = ?);");

                    preparedStatement.setInt(1, EditSportsmenController.getSportsman_id());

                    preparedStatement.executeUpdate();
                    MainSceneController.standardRequest.refreshAll();
                }catch (SQLException exception){
                    exception.printStackTrace();
                }
            }


        });

        return contextMenu;
    }

    public static ContextMenu contractContext() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem change = new MenuItem("Изменить");
        MenuItem delete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(change, delete);

        change.setOnAction(e ->{
            fxmlLoader = new FXMLLoader(MyContextMenu.class.getResource("/additionalScenes/edit_scenes/editContract.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Изменение договора");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e ->{
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Вместе с этой записью вы также удалите сведения о спортсмене и контрагенте.\n" +
                            "Вы действительно хотите удалить выбранную запись?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "delete from partners where id = (select partner from contracts where number = ?);");

                    preparedStatement.setInt(1, EditContractController.getNumber());

                    preparedStatement.executeUpdate();
                    MainSceneController.standardRequest.refreshAll();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        return contextMenu;
    }

    public static ContextMenu partnerContext() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("Дополнительно");
        MenuItem change = new MenuItem("Изменить");
        MenuItem delete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(change, delete, info);

        change.setOnAction(e ->{
            fxmlLoader = new FXMLLoader(MyContextMenu.class.getResource(
                    "/additionalScenes/edit_scenes/editPartners.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Изменение данных контрагента");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e ->{
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Вместе с этой записью вы также удалите сведения о спортсмене и договоре.\n" +
                            "Вы действительно хотите удалить выбранную запись?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "delete from partners where id = ?;");

                    preparedStatement.setInt(1, EditPartnersController.getContragentID());

                    preparedStatement.executeUpdate();
                    MainSceneController.standardRequest.refreshAll();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        return contextMenu;
    }

    public static ContextMenu personalTimeTableContext() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("Дополнительно");
        MenuItem change = new MenuItem("Изменить");
        MenuItem delete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(change, delete, info);

        change.setOnAction(e ->{
            fxmlLoader = new FXMLLoader(MyContextMenu.class.getResource(
                    "/additionalScenes/edit_scenes/editPersonalTimeTable.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Изменение записи");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e ->{
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Вы действительно хотите удалить выбранную запись?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "delete from personal_timetable where id = ?;");

                    preparedStatement.setInt(1, EditPersonalTimeTableController.getId());

                    preparedStatement.executeUpdate();
                    MainSceneController.standardRequest.refreshAll();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        return contextMenu;
    }

    public static ContextMenu timeTableContext() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("Дополнительно");
        MenuItem change = new MenuItem("Изменить");
        MenuItem delete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(change, delete, info);

        change.setOnAction(e ->{
            fxmlLoader = new FXMLLoader(MyContextMenu.class.getResource(
                    "/additionalScenes/edit_scenes/editTimeTable.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Изменение расписания");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e ->{
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Вы действительно хотите удалить выбранную запись?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "delete from timetable where id = ?;");

                    preparedStatement.setInt(1, EditTimeTableController.getId());

                    preparedStatement.executeUpdate();
                    MainSceneController.standardRequest.refreshAll();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        return contextMenu;
    }

    public static ContextMenu trainersContext() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("Дополнительно");
        MenuItem change = new MenuItem("Изменить");
        MenuItem delete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(change, delete, info);

        change.setOnAction(e ->{
            fxmlLoader = new FXMLLoader(MyContextMenu.class.getResource(
                    "/additionalScenes/edit_scenes/editTrainers.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Изменение данных тренера");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e ->{
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Вы действительно хотите удалить выбранную запись?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "delete from trainers where id = ?;");

                    preparedStatement.setInt(1, EditTrainersController.getTrainer_id());

                    preparedStatement.executeUpdate();
                    MainSceneController.standardRequest.refreshAll();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        return contextMenu;
    }

    public static ContextMenu exercisesContext() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("Дополнительно");
        MenuItem change = new MenuItem("Изменить");
        MenuItem delete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(change, delete, info);

        change.setOnAction(e ->{
            fxmlLoader = new FXMLLoader(MyContextMenu.class.getResource(
                    "/additionalScenes/edit_scenes/editExercise.fxml"));
            Parent root1;
            try {
                root1 = fxmlLoader.load();
                Stage addStage = new Stage();
                addStage.initModality(Modality.WINDOW_MODAL);
                addStage.setTitle("Изменение занятия");
                addStage.setScene(new Scene(root1));
                addStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e ->{
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Вы действительно хотите удалить выбранную запись?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "delete from exercise where id = ?;");

                    preparedStatement.setInt(1, EditExerciseController.getId());

                    preparedStatement.executeUpdate();
                    MainSceneController.standardRequest.refreshAll();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        return contextMenu;
    }
}
