package com.dojo.cinemastark.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The movie name is required!")
    @Size(min = 3, max = 30, message = "The name should include at least 3 characters!")
    private String movieName;

    @NotEmpty(message = "The movie description is required!")
    @Size(min = 5, message = "The description should include at least 5 characters!")
    private String overview;

    @NotEmpty(message = "The genre is required!")
    @Size(min = 2, max = 20, message = "The genre should include at least 5 characters!")
    private String genre;

    @NotEmpty(message = "The released date is required!")
    @Min(value = 1)
    private Integer released;

    @NotEmpty(message = "The duration is required!")
    @Min(value = 1)
    private Integer duration;

    @NotEmpty(message = "The cast field is required!")
    @Size(min = 2, max = 100, message = "The cast field should include at least 2 characters!")
    private String cast;

    @NotEmpty(message = "The production field is required!")
    @Size(min = 2, max = 100, message = "The production field should include at least 2 characters!")
    private String production;

    @NotEmpty(message = "The country is required!")
    @Size(min = 2, max = 20, message = "The country should include at least 2 characters!")
    private String country;

    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "categories_movies",
            joinColumns = @JoinColumn(name = "movies_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Categories> categories;

    public Movie() {
    }

    public Movie(Long id, String movieName, String overview, String genre, Integer released, Integer duration, String cast, String production, String country) {
        this.id = id;
        this.movieName = movieName;
        this.overview = overview;
        this.genre = genre;
        this.released = released;
        this.duration = duration;
        this.cast = cast;
        this.production = production;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overView) {
        this.overview = overView;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getReleased() {
        return released;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
