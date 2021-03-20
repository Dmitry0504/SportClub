package model;

public class PersonalTimeTable {
    private int id;
    private int sportsmen_id;
    private int training_id;

    public PersonalTimeTable(int id, int sportsmen_id, int training_id) {
        this.id = id;
        this.sportsmen_id = sportsmen_id;
        this.training_id = training_id;
    }

    public int getId() {
        return id;
    }

    public int getSportsmen_id() {
        return sportsmen_id;
    }

    public int getTraining_id() {
        return training_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSportsmen_id(int sportsmen_id) {
        this.sportsmen_id = sportsmen_id;
    }

    public void setTraining_id(int training_id) {
        this.training_id = training_id;
    }
}
