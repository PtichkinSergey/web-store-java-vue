package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.webstore.model.Role;

// Репозиторий для доступа к данным таблицы сущности роли
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{
    @NonNull
    public Optional<Role> findById(@NonNull Integer id);

    @NonNull
    public Iterable<Role> findAll();

    @NonNull
    public Role save(@NonNull Role role);

    public void deleteById(@NonNull Integer id);
    
    public Optional<Role> findByName(String name);
}
