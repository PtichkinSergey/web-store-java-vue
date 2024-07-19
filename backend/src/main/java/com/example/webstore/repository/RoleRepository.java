package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.webstore.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{
    @Query(value = "Select r from Role r where r.name = :name")
    Optional<Role> findByName(String name);
}
