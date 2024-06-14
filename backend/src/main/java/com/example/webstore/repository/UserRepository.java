package com.example.webstore.repository;

import com.example.webstore.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Интерфейс доступа к данным
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
