package com.dojo.cinemastark.repositories;

import com.dojo.cinemastark.models.Category;
import com.dojo.cinemastark.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAll();
    List<Category> findAllByMovies(Movie movie);
    List<Category> findByMoviesNotContaining(Movie movie);
}
