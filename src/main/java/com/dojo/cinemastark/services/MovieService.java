package com.dojo.cinemastark.services;

import com.dojo.cinemastark.models.Category;
import com.dojo.cinemastark.models.Movie;
import com.dojo.cinemastark.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }


    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie findMovie(Long id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if(optionalMovie.isPresent()) {
            return optionalMovie.get();
        } else {
            return null;
        }
    }

    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<Movie> assingMovie(Category categories){
        return movieRepository.findByCategories(categories);
    }
    public List<Movie> unAssingMovie(Category categories){
        return movieRepository.findAllByCategoriesNotContaining(categories);
    }
    public List<Movie> topthree(){
        return movieRepository.findTop10ByOrderByViewsDesc();
    }

    public List<Movie> findByName(String AnimeName){
        return movieRepository.findByAnimeName(AnimeName);
    }
    public List<Movie> newAdd(){
        return movieRepository.findTop10ByOrderByCreatedAtDesc();
    }
    public List<Movie> trending(){
        return movieRepository.findTop9ByOrderByUpdatedAtDesc();
    }
}
