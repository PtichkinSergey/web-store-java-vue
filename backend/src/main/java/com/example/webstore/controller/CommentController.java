package com.example.webstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.Comment;
import com.example.webstore.service.CommentServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam("good_id") Integer goodId) {
        try {
            List<Comment> commentList = new ArrayList<Comment>();
            commentService.selectByGoodId(goodId).forEach(commentList::add);
            if (commentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") int id) {
        Optional<Comment> commentData = commentService.findById(id);
        if (commentData.isPresent()) {
            return new ResponseEntity<>(commentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/comments")
    public String postMethodName(@RequestBody Comment comment) {
        commentService.create(comment);
        return "success";
    }
}
