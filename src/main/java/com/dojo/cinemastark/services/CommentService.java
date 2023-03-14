package com.dojo.cinemastark.services;

import com.dojo.cinemastark.models.Comment;
import com.dojo.cinemastark.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> allComments() {
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findAllByMovieId(Long id) {
        return commentRepository.findAllByMovieId(id);
    }
}
