package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.example.webstore.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{
    @NonNull
    @Query(value = "Select r from Role r where r.id = :id")
    public Optional<Role> findById(@NonNull Integer id);

    @NonNull
    @Query(value = "Select r from Role r")
    public Iterable<Role> findAll();

    @NonNull
    // @Query(value = 
    //     "Insert into Role (name) select :role.getName()"
    // )
    public Role save(@NonNull Role role);

    @Query(value = "Delete from User u where u.id = :id")
    public void deleteById(@NonNull Integer id);
    
    @Query(value = "Select r from Role r where r.name = :name")
    Optional<Role> findByName(String name);
}
