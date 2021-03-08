package model;

import java.sql.Date;

public class Contract {
    private int number;
    private int partner;
    private String description;
    private Date conclusion;
    private Date ending;
    private double cost;

    public Contract(int number, int partner, String description, Date conclusion, Date ending, double cost) {
        this.number = number;
        this.partner = partner;
        this.description = description;
        this.conclusion = conclusion;
        this.ending = ending;
        this.cost = cost;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPartner() {
        return partner;
    }

    public void setPartner(int partner) {
        this.partner = partner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
