package com.example.webstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webstore.model.Comment;
import com.example.webstore.model.Good;
import com.example.webstore.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> readAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> selectByGoodId(int goodId) {
        List<Comment> allComments = commentRepository.findAll();
        List<Comment> filteredList = new ArrayList<Comment>();
        for(int i = 0; i < allComments.size(); i++) {
            Good good = allComments.get(i).getGood();
            if(good.getId() == goodId) {
                filteredList.add(allComments.get(i));
            }
        }
        return filteredList;
    }

    @Override
    public Optional<Comment> findById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(int id) {
        commentRepository.deleteById(id);
    }
    
}
