package com.dojo.cinemastark.repositories;

import com.dojo.cinemastark.models.Comment;
import com.dojo.cinemastark.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAll();

    List<Comment> findAllByMovieId(Long id);
}
