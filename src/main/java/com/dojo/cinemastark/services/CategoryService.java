package com.dojo.cinemastark.services;

import com.dojo.cinemastark.models.Category;
import com.dojo.cinemastark.models.Movie;
import com.dojo.cinemastark.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {


    @Autowired
    private CategoryRepository categoryRepo;


    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    //find all
    public List<Category> getall() {
        return categoryRepo.findAll();
    }



    // find by id
    public Category getById(Long id) {
        Optional<Category> optional = categoryRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    //find by movies
    public List<Category> getByMovies(Movie movie) {
        return categoryRepo.findAllByMovies(movie);
    }

    //find by movies not container
    public List<Category> getByMoviesNotContains(Movie movie) {
        return categoryRepo.findByMoviesNotContaining(movie);
    }

    // create
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    //update
    public Category updateCategory(Category category) {
        return categoryRepo.save(category);
    }

    //delete
    public void deleteCategoryById(Long id) {
        categoryRepo.deleteById(id);
    }
}
