package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

@Entity
public class Store extends PanacheEntity {
    @JsonbProperty("store_name")
    private String storeName;
    private int rent;
    @ManyToOne
    private Person shopkeeper;
    private Category category;

    public Store(String storeName, int rent, Person shopkeeper, Category category) {
        this.storeName = storeName;
        this.rent = rent;
        this.shopkeeper = shopkeeper;
        this.category = category;
    }

    public Store() {

    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public Person getShopkeeper() {
        return shopkeeper;
    }

    public void setShopkeeper(Person shopkeeper) {
        this.shopkeeper = shopkeeper;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Store "+storeName;
    }
}
