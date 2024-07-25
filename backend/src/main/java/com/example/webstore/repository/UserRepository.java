package com.example.webstore.repository;

import com.example.webstore.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

// Интерфейс доступа к данным
public interface UserRepository extends CrudRepository<User, Integer> {
    @NonNull
    @Query(value = "Select u from User u where u.id = :id")
    public Optional<User> findById(@NonNull Integer id);

    @NonNull
    @Query(value = "Select u from User u")
    public Iterable<User> findAll();

    @NonNull
    // @Query(value = 
    //     "Insert into User (first_name, second_name, email, password, role_id) " + 
    //     "select :user.getFirstName(), :user.getSecondName(), :user.getEmail(), :user.getPassword(), r.id from Role where r.name = :user.getRole().getName()"
    // )
    public User save(@NonNull User user);

    @Query(value = "Delete from User u where u.id = :id")
    public void deleteById(@NonNull Integer id);

    @NonNull
    @Query(value = "Select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);
}
