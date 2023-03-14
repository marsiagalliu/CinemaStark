package com.dojo.cinemastark.controllers;

import com.dojo.cinemastark.services.CategoryService;
import com.dojo.cinemastark.services.MovieService;
import com.dojo.cinemastark.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @RequestMapping("/index.html")
    public String index(){
        return "index.jsp";
    }

    @RequestMapping("/anime-watching.html")
    public String watch(){
        return "anime-watching.jsp";
    }

    @RequestMapping("/anime-details.html")
    public String detail(){
        return "anime-details.jsp";
    }

    @RequestMapping("/blog.html")
    public String blog(){
        return "blog.jsp";
    }

    @RequestMapping("/blog-details.html")
    public String blogDetails(){
        return "blog-details.jsp";
    }

    @RequestMapping("/categories.html")
    public String category(){
        return "categories.jsp";
    }

    @RequestMapping("/login.html")
    public String login(){
        return "login.jsp";
    }

    @RequestMapping("/signup.html")
    public String signup(){
        return "signup.jsp";
    }
}
