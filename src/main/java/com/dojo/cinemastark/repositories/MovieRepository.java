package com.dojo.cinemastark.repositories;

import com.dojo.cinemastark.models.Categories;
import com.dojo.cinemastark.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findAll();
    List<Movie> findByCategories(Categories categories);
    List<Movie> findAllByCategoriesNotContaining(Categories categories);
    List<Movie> findTop10ByOrderByViewsDesc();
    List<Movie> findTop9ByOrderByUpdatedAtDesc();
    List<Movie> findTop10ByOrderByCreatedAtDesc();
    List<Movie> findByAnimeName(String AnimeName);
}
