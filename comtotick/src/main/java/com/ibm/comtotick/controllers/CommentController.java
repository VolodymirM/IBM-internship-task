package com.ibm.comtotick.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.comtotick.dataentities.Comment;
import com.ibm.comtotick.services.CommentService;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @GetMapping("/comments")
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    @PostMapping("/comments")
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

}
