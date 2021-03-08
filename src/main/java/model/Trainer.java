package model;

import java.util.Date;

public class Trainer {
    private int id;
    private String surname;
    private String name;
    private Date birthday;
    private int telephone;

    public Trainer(int id, String surname, String name, Date birthday, int telephone) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.birthday = birthday;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }
}
