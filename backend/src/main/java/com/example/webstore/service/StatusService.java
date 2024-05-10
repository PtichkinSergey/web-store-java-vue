package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import com.example.webstore.model.Status;

public interface StatusService {
    public Status create(Status status);
    public List<Status> readAll();
    public Optional<Status> findById(int id);
    public Status update(Status status);
    public void delete(int id);
}
