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
import javax.persistence.OneToMany;

@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goodId;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private int cost;

    @Column(name = "count")
    private int count;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

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

    // @OneToMany
    // @JoinColumn(name = "good_id")
    // private Set<Comment> comments = new HashSet<Comment>();

    public Good(String name, int cost, int count, String manufacturer, String description, String imagePath) {
        this.name = name;
        this.cost = cost;
        this.count = count;
        this.manufacturer = manufacturer;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Good() {

    }

    public int getId() {
        return goodId;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public Set<Category> setCategories(Set<Category> categories) {
        return this.categories = categories;
    }

    // public Set<Comment> getComments() {
    //     return this.comments;
    // }

    // public void setComments(Set<Comment> comments) {
    //     this.comments = comments;
    // }
    
}
