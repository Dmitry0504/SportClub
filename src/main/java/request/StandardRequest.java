package request;

import javafx.scene.control.TableView;
import model.*;

import java.sql.SQLException;

public class StandardRequest {

    private TableView<Sportsman> sportsmanTableView = new SportsmenRequest().createTBSportsmen();
    private TableView<Trainer> trainerTableView = new TrainersRequest().createTBTrainers();
    private TableView<Exercise> exerciseTableView = new ExerciseRequest().createTBExercise();
    private TableView<Partner> partnerTableView = new PartnerRequest().createTBPartners();
    private TableView<TimeTable> timeTableTableView = new TimeTableRequest().createTBTimeTable();
    private TableView<Contract> contractTableView = new ContractsRequest().createTBContracts();
    private TableView<PersonalTimeTable> personalTimeTableTableView = new PersonalTimeTableRequest().createTBPersonalTimeTable();

    public void refreshAll(){
        refreshContractTableView();
        refreshExerciseTableView();
        refreshTimeTableTableView();
        refreshPartnerTableView();
        refreshSportsmanTableView();
        refreshTrainerTableView();
        refreshPersonalTimeTableTableView();
    }

    public void refreshPersonalTimeTableTableView() {
        personalTimeTableTableView = new PersonalTimeTableRequest().createTBPersonalTimeTable();
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

    public TableView<PersonalTimeTable> getPersonalTimeTableTableView() {
        return personalTimeTableTableView;
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

    public TableView<Sportsman> getSearchingSportsmanTableView(String sportsmanSurname) throws SQLException {
        return new SportsmenRequest().searchSportsman(sportsmanSurname);
    }

}
