package com.example.webstore.service;

import java.util.List;

import com.example.webstore.model.User;

public interface UserService {
    public User create(User user);
    public List<User> readAll();
    public User update(User user);
    public void delete(int id);
}
