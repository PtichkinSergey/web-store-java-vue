package com.example.webstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;

    @Column(name = "text")
    private String text;

    @Column(name = "rating")
    private int rating;

    public Comment(String text, int rating) {
        this.text = text;
        this.rating = rating;
    }

    public Comment() {
    }

    public int getId() {
        return commentId;
    }

    public User getUser() {
        return user;
    }

    public Good getGood() {
        return good;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
