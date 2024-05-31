package com.example.webstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "email")
    private String email;

    @Column(name = "is_admin")
    private boolean isAdmin;

    public User(String firstName, String secondName, String email, boolean isAdmin) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public User() {

    }

    public int getId() {
		return userId;
	}

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
