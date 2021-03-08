package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private final static String URL = "jdbc:mysql://localhost:3306/sportclub_db";
    private final static String USER = "root";
    private final static String PSWD = "Tjkcydlht2!";

    public static Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PSWD);
    }

    public static Connection getNewConnection(String login, String password) throws SQLException {
        return DriverManager.getConnection(URL, login, password);
    }
}
