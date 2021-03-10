package model;

import javafx.beans.property.*;

import java.sql.Date;

public class Contract {
    private IntegerProperty number;
    private IntegerProperty partner;
    private StringProperty description;
    private Date conclusion;
    private Date ending;
    private DoubleProperty cost;

    public Contract(
            int number, int partner, String description,
            Date conclusion, Date ending, double cost) {
        this.number = new SimpleIntegerProperty(number);
        this.partner = new SimpleIntegerProperty(partner);
        this.description = new SimpleStringProperty(description);
        this.conclusion = conclusion;
        this.ending = ending;
        this.cost = new SimpleDoubleProperty(cost);
    }

    public int getNumber() {
        return number.getValue();
    }

    public void setNumber(int number) {
        this.number = new SimpleIntegerProperty(number);
    }

    public int getPartner() {
        return partner.getValue();
    }

    public void setPartner(int partner) {
        this.partner = new SimpleIntegerProperty(partner);
    }

    public String getDescription() {
        return description.toString();
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public Date getConclusion() {
        return conclusion;
    }

    public void setConclusion(Date conclusion) {
        this.conclusion = conclusion;
    }

    public Date getEnding() {
        return ending;
    }

    public void setEnding(Date ending) {
        this.ending = ending;
    }

    public double getCost() {
        return cost.doubleValue();
    }

    public void setCost(double cost) {
        this.cost = new SimpleDoubleProperty(cost);
    }
}
