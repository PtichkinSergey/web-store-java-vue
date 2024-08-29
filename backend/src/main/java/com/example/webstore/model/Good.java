package com.example.webstore.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Класс сущности товара
 */
@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Column(name = "cost")
    private int cost;

    @Column(name = "discount")
    private float discount;

    @NotNull
    @Column(name = "count")
    private int count;

    @Column(name = "manufacturer", length = 50)
    private String manufacturer;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "image_path", length = 255)
    private String imagePath;

    @Version
    @Column(name = "version")
    private int version;

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
    private Set<Category> categories = new HashSet<>();

    public Good(String name, int cost, float discount, int count, String manufacturer, String description, String imagePath, int version) {
        this.name = name;
        this.cost = cost;
        this.discount = discount;
        this.count = count;
        this.manufacturer = manufacturer;
        this.description = description;
        this.imagePath = imagePath;
        this.version = version;
    }

    public Good() {

    }

    public int getId() {
        return id;
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

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public Set<Category> setCategories(Set<Category> categories) {
        return this.categories = categories;
    }
}
