package com.dojo.cinemastark.repositories;

import com.dojo.cinemastark.models.Category;
import com.dojo.cinemastark.models.Movie;
import com.dojo.cinemastark.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findAll();
    List<Movie> findByCategories(Category categories);
    List<Movie> findAllByCategoriesNotContaining(Category categories);
    List<Movie> findTop10ByOrderByViewsDesc();
    List<Movie> findTop9ByOrderByUpdatedAtDesc();
    List<Movie> findTop10ByOrderByCreatedAtDesc();
    List<Movie> findByAnimeName(String AnimeName);
    List<Movie> findByUserFavorites(User user);
}
