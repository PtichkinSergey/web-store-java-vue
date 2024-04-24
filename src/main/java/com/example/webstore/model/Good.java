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
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private int cost;

    @Column(name = "count")
    private int count;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="employee_task", joinColumns = JoinColumns(name = "employee_id", referencedColumnName = "id"),
           inverseJoinColumns= JoinColumns(name = "task_id", referencedColumnName = "id"))
    private Set<Category> tasks = new HashSet<Category>();

    public Good(String name, int cost, int count) {
        this.name = name;
        this.cost = cost;
        this.count = count;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCount(int count) {
        this.count = count;
    }

    
}
