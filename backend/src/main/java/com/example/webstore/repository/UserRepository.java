package com.example.webstore.repository;

import com.example.webstore.model.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

// Интерфейс доступа к данным
public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
