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

    private TableView<Sportsman> sportsmanTableView = new SportsmenRequest().createTBSportsmen();
    private TableView<Trainer> trainerTableView = new TrainersRequest().createTBTrainers();
    private TableView<Exercise> exerciseTableView = new ExerciseRequest().createTBExercise();
    private TableView<Partner> partnerTableView = new PartnerRequest().createTBPartners();
    private TableView<TimeTable> timeTableTableView = new TimeTableRequest().createTBTimeTable();
    private TableView<Contract> contractTableView = new ContractsRequest().createTBContracts();

    public void refreshAll(){
        refreshContractTableView();
        refreshExerciseTableView();
        refreshTimeTableTableView();
        refreshPartnerTableView();
        refreshSportsmanTableView();
        refreshTrainerTableView();
    }

    public void refreshTimeTableTableView() {
        timeTableTableView = new TimeTableRequest().createTBTimeTable();
    }

    public void refreshContractTableView() {
        contractTableView = new ContractsRequest().createTBContracts();
    }

    public void refreshSportsmanTableView() {
        sportsmanTableView = new SportsmenRequest().createTBSportsmen();
    }

    public void refreshTrainerTableView() {
        trainerTableView = new TrainersRequest().createTBTrainers();
    }

    public void refreshExerciseTableView() {
        exerciseTableView = new ExerciseRequest().createTBExercise();
    }

    public void refreshPartnerTableView() {
        partnerTableView = new PartnerRequest().createTBPartners();
    }

    public TableView<Contract> getContractTableView() {
        return contractTableView;
    }

    public TableView<TimeTable> getTimeTableTableView() {
        return timeTableTableView;
    }

    public TableView<Partner> getPartnerTableView() {
        return partnerTableView;
    }

    public TableView<Exercise> getExerciseTableView() {
        return exerciseTableView;
    }

    public TableView<Sportsman> getSportsmanTableView() {
        return sportsmanTableView;
    }

    public TableView<Trainer> getTrainerTableView(){
        return trainerTableView;
    }

}
