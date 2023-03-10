package com.dojo.cinemastark.repositories;

import com.dojo.cinemastark.models.Categories;
import com.dojo.cinemastark.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepo extends CrudRepository<Categories , Long> {

    List<Categories> findAll();
    List<Categories> findAllByMovies(Movie movie);
    List<Categories> findByMoviesNotContaining(Movie movie);
}
