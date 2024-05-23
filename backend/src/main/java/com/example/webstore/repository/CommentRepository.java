package com.example.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webstore.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    
}
