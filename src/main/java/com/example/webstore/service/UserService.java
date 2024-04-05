package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import com.example.webstore.model.User;

public interface UserService {
    public User create(User user);
    public List<User> readAll();
    public Optional<User> findById(int id);
    public User update(User user);
    public void delete(int id);
}
