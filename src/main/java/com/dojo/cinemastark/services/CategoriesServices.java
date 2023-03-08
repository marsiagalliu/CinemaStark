package com.dojo.cinemastark.services;

import com.dojo.cinemastark.models.Categories;
import com.dojo.cinemastark.models.Movie;
import com.dojo.cinemastark.repositories.CategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriesServices {


    @Autowired
    private CategoriesRepo categoryRepo;


    public CategoriesServices(CategoriesRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    //find all
    public List<Categories> getall() {
        return categoryRepo.findAll();
    }



    // find by id
    public Categories getById(Long id) {
        Optional<Categories> optional = categoryRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    //find by movies
    public List<Categories> getByMovies(Movie movie) {
        return categoryRepo.findAllByMovies(movie);
    }

    //find by movies not container
    public List<Categories> getByMoviesNotContains(Movie movie) {
        return categoryRepo.findByMoviesNotContaining(movie);
    }

    // create
    public Categories createCategory(Categories category) {
        return categoryRepo.save(category);
    }

    //update
    public Categories updateCategory(Categories category) {
        return categoryRepo.save(category);
    }

    //delete
    public void deleteCategoryById(Long id) {
        categoryRepo.deleteById(id);
    }
}
