package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import com.example.webstore.model.Comment;

public interface CommentService {
    public Comment create(Comment comment);
    public List<Comment> readAll();
    public List<Comment> selectByGoodId(int goodId);
    public Optional<Comment> findById(int id);
    public Comment update(Comment comment);
    public void delete(int id);
}
