package model;

import java.sql.Date;
import java.sql.Time;

public class TimeTable {
    private int id;
    private Date date;
    private Time time;
    private String description;
    private int trainer;
    private int exercise_id;

    public TimeTable(int id, Date date, Time time, String description, int trainerID, int exerciseID) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.trainer = trainerID;
        this.exercise_id = exerciseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTrainer() {
        return trainer;
    }

    public void setTrainer(int trainer) {
        this.trainer = trainer;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }
}
