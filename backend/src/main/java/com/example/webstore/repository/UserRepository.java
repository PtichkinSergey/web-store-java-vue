package com.example.webstore.repository;

import com.example.webstore.model.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

// Репозиторий для доступа к данным таблицы сущности пользователя
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @NonNull
    public Optional<User> findById(@NonNull Integer id);

    @NonNull
    public Iterable<User> findAll();

    @NonNull
    public User save(@NonNull User user);

    public void deleteById(@NonNull Integer id);

    @NonNull
    public Optional<User> findByEmail(String email);
}
