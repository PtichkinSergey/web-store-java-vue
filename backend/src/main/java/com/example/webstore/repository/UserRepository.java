package com.example.webstore.repository;

import com.example.webstore.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

// Интерфейс доступа к данным
public interface UserRepository extends JpaRepository<User, Integer>{

}
