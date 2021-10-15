package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Event extends PanacheEntity {
    private String name;
    @JsonbDateFormat("yyyy-MM-dd")
    private Date date;
    @OneToMany
    @JsonbProperty("involved_Stores")
    private List<Store> involvedStores;

    @JsonbTransient
    @Transient
    private DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public Event(String name, Date date) {
        this.date = date;
        this.name = name;
        this.involvedStores = new ArrayList<>();
    }

    public Event(String name, Date date, List<Store> involvedStores) {
        this.date = date;
        this.name = name;
        this.involvedStores = involvedStores;
    }

    public Event() {

    }
/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Store> getInvolvedStores() {
        return involvedStores;
    }

    public void addInvolvedStore(Store store) {
        this.involvedStores.add(store);
    }

    @Override
    public String toString() {
        return "Event " + name + " on " + date;
    }
}
