package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webstore.model.Status;
import com.example.webstore.repository.StatusRepository;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status create(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public List<Status> readAll() {
        return statusRepository.findAll();
    }

    @Override
    public Optional<Status> findById(int id) {
        return statusRepository.findById(id);
    }

    @Override
    public Status update(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public void delete(int id) {
        statusRepository.deleteById(id);
    }
    
}
