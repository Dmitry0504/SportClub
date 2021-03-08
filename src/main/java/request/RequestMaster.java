package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Sportsman;
import sample.Controller;

import javax.swing.text.TabableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RequestMaster {
    private static Connection connection = Controller.getConnection();



    public static String showTrainers(){
        StringBuilder sb = new StringBuilder();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trainers");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                sb.append(resultSet.getInt("id")).append(" ");
                sb.append(resultSet.getString("surname")).append(" ");
                sb.append(resultSet.getString("name")).append(" ");
                sb.append(resultSet.getDate("birthday")).append(" ");
                sb.append(resultSet.getInt("telephone")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
