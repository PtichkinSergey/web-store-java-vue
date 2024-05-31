package com.example.webstore.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int good_id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private int cost;

    @Column(name = "count")
    private int count;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column
    private String description;

    @Column
    private String image_path;

    @ManyToMany(cascade = {
        CascadeType.ALL
    })
    @JoinTable(
        name = "good_category",
        joinColumns = {
            @JoinColumn(name = "good_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "category_id")
        }
    )
    private Set<Category> categories = new HashSet<Category>();

    public Good(String name, int cost, int count, String manufacturer, String description, String image_path) {
        this.name = name;
        this.cost = cost;
        this.count = count;
        this.manufacturer = manufacturer;
        this.description = description;
        this.image_path = image_path;
    }

    public Good() {

    }

    public int getId() {
        return good_id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getCount() {
        return count;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public Set<Category> setCategories(Set<Category> categories) {
        return this.categories = categories;
    }
}
