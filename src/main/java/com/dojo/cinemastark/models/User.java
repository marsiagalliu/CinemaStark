package com.dojo.cinemastark.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required!")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String userName;

    @NotEmpty(message = "Email is required!")
    @Email(message = "Please enter a valid email!") // @Email annotation has regex built in for email patterns
    private String email;

    @NotEmpty(message = "Password is required!")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    private String password;

//    @Column(updatable=false)
//    @OneToMany(mappedBy="addedBy", fetch = FetchType.LAZY)
//    private List<Listing> listingsCreated;

    @OneToMany(mappedBy="user",fetch = FetchType.LAZY)
    @Column(updatable=false)
    private List<Comment> comments;

    @OneToMany(mappedBy="user",fetch = FetchType.LAZY)
    @Column(updatable=false)
    private List<Movie> movies;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favourite" , joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn( name = "movies_id"))
    private List<Movie> favorites;

    // empty constructor
    public User() {
    }


    // loaded constructor
    public User(String userName, String email, String password) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Movie> favorites) {
        this.favorites = favorites;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    //    public List<Listing> getListingsCreated() {
//        return listingsCreated;
//    }
//
//    public void setListingsCreated(List<Listing> listingsCreated) {
//        this.listingsCreated = listingsCreated;
//    }

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




}

