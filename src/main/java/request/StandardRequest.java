package request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import sample.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StandardRequest {
    private static Connection connection = Controller.getConnection();

    private static TableView<Sportsman> sportsmanTableView = SportsmenRequest.createTBSportsmen();

    private static TableView<Trainer> trainerTableView = TrainersRequest.createTBTrainers();

    private static TableView<Exercise> exerciseTableView = ExerciseRequest.createTBExercise();

    private static TableView<Partner> partnerTableView = PartnerRequest.createTBPartners();

    private static TableView<TimeTable> timeTableTableView = TimeTableRequest.createTBTimeTable();

    private TableView<Contract> contractTableView = new ContractsRequest().createTBContracts();

//    public static void refreshAll(){
//        refreshContractTableView();
//        refreshExerciseTableView();
//        refreshTimeTableTableView();
//        refreshPartnerTableView();
//        refreshSportsmanTableView();
//        refreshTrainerTableView();
//    }

    private static void refreshTimeTableTableView() {
        timeTableTableView = TimeTableRequest.createTBTimeTable();
    }

    public void refreshContractTableView() {
        contractTableView = new ContractsRequest().createTBContracts();
    }

    private static void refreshSportsmanTableView() {
        sportsmanTableView = SportsmenRequest.createTBSportsmen();
    }

    private static void refreshTrainerTableView() {
        trainerTableView = TrainersRequest.createTBTrainers();
    }

    private static void refreshExerciseTableView() {
        exerciseTableView = ExerciseRequest.createTBExercise();
    }

    private static void refreshPartnerTableView() {
        partnerTableView = PartnerRequest.createTBPartners();
    }

    public TableView<Contract> getContractTableView() {
        return contractTableView;
    }

    public static TableView<TimeTable> getTimeTableTableView() {
        return timeTableTableView;
    }

    public static TableView<Partner> getPartnerTableView() {
        return partnerTableView;
    }

    public static TableView<Exercise> getExerciseTableView() {
        return exerciseTableView;
    }

    public static TableView<Sportsman> getSportsmanTableView() {
        return sportsmanTableView;
    }

    public static TableView<Trainer> getTrainerTableView(){
        return trainerTableView;
    }


}
