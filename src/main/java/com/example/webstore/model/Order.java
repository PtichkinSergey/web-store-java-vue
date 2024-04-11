package com.example.webstore.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Order(User user, Status status) {
        this.user = user;
        this.status = status;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Status getStatus() {
        return status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
