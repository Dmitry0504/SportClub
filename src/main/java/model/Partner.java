package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Partner {
    private int id;
    private String surname;
    private String name;
    private Date birthday;
    private BigDecimal telephone;

    public Partner(int id, String surname, String name, Date birthday, BigDecimal telephone) {
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

    public BigDecimal getTelephone() {
        return telephone;
    }

    public void setTelephone(BigDecimal telephone) {
        this.telephone = telephone;
    }
}
