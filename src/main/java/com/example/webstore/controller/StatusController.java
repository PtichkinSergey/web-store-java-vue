package com.example.webstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.Status;
import com.example.webstore.service.StatusServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class StatusController {
    private final StatusServiceImpl statusService;

    public StatusController(StatusServiceImpl statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/status")
    public ResponseEntity<List<Status>> getAllStatus() {
        try {
            List<Status> statusList = new ArrayList<Status>();
            statusService.readAll().forEach(statusList::add);
            if (statusList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(statusList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable("id") int id) {
        Optional<Status> statusData = statusService.findById(id);
        if (statusData.isPresent()) {
            return new ResponseEntity<>(statusData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
