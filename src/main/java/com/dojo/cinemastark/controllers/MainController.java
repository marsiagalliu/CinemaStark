package com.dojo.cinemastark.controllers;

import com.dojo.cinemastark.models.*;
import com.dojo.cinemastark.services.CategoryService;
import com.dojo.cinemastark.services.CommentService;
import com.dojo.cinemastark.services.MovieService;
import com.dojo.cinemastark.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CategoryService categoriesServices;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("movies" , movieService.allMovies());
        model.addAttribute("moviesThree" , movieService.topthree());
        model.addAttribute("category", categoriesServices.getall());
        model.addAttribute("newAddMovies", movieService.newAdd());
        model.addAttribute("trendingAnime", movieService.trending());

        return "index.jsp";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("category", categoriesServices.getall());
        return "login.jsp";
    }
    @GetMapping("/signup")
    public String signup(Model model, @ModelAttribute("newUser") User newUser,
                         @ModelAttribute("newLogin") User newLogin){
        model.addAttribute("category", categoriesServices.getall());
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());

        return "signup.jsp";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
                        HttpSession session) {
        User user = userService.login(newLogin, result);

        System.out.println(user);

        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
        session.setAttribute("loggedInUserID", user.getId());
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
                           HttpSession session) {
        userService.register(newUser, result);

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }

        session.setAttribute("loggedInUserID", newUser.getId());
        return "redirect:/";
    }

    @GetMapping("/watching/{id}")
    public String watching(@PathVariable("id")Long id,  Model model, HttpSession session){
       Movie movie = movieService.findMovie(id);
        model.addAttribute("moviesId" , movie);
        Integer count = movie.getViews();
        if(count == null){
            movie.setViews(1);
            movieService.createMovie(movie);
        }else {
            movie.setViews(count + 1);
            movieService.createMovie(movie);
        }
        return "anime-watching.jsp";
    }

    @PostMapping("/details/{id}")
    public String addComment(@PathVariable("id") Long id, @Valid @ModelAttribute("newComment") Comment comment, BindingResult result,
                             HttpSession session){
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            return "anime-details.jsp";
        } else {
            Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");

            if (loggedInUserID == null) {
                System.out.println("user is not logged in");
                return "redirect:/login";
            }
            System.out.println("user is logged in");
            User loggedInUser = userService.findOneUser(loggedInUserID);
            comment.setUser(loggedInUser);
            comment.setMovie(movieService.findMovie(id));
            comment.setId(null);
            commentService.createComment(comment);
            return "redirect:details/" + id;
        }
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id")Long id,  Model model){
        Movie movie = movieService.findMovie(id);
        List<Comment> comments = commentService.findAllByMovieId(id);
        model.addAttribute("moviesId" , movie);
        model.addAttribute("movies" , movieService.allMovies());
        model.addAttribute("categories", categoriesServices.getall());
        model.addAttribute("comment", comments);
        return "anime-details.jsp";
    }

    @GetMapping("/category/{id}")
    public  String categoryMovie(@PathVariable("id")Long id , Model model){
        Category categories = categoriesServices.getById(id);
        model.addAttribute("category" ,categories );
        model.addAttribute("categories", categoriesServices.getall());
        model.addAttribute("MoviesInCategory" , movieService.assingMovie(categories));
        return "categories.jsp";
    }


    @GetMapping("/search/{AnimeName}")
    public String serarchByName(@PathVariable("AnimeName") String AnimeName, Model model){
        model.addAttribute("serachBar" , movieService.findByName(AnimeName));
        model.addAttribute("categories", categoriesServices.getall());
        return "search.jsp";
    }

    @PostMapping("/search")
    public String searchput(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("movieSerch" , movieService.findByName(name));
        return "redirect:/search/" + name;
    }


    private static String UPLOADED_FOLDER = "src/main/resources/static/img/";
    private static String UPLOADED_FOLDER_VIDEO = "src/main/resources/static/videos/";

    @GetMapping("/anime/new")
    public String newAnime(@ModelAttribute("anime") Movie anime, Model model , HttpSession session){
        return "newAnime.jsp";
    }
    @PostMapping("/anime/new")
    public String createAnime(@Valid @ModelAttribute("anime") Movie anime, BindingResult result , @RequestParam("pic") MultipartFile file,  @RequestParam("video") MultipartFile video,HttpSession session , RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "newAnime.jsp";
        }



        try{


            byte[] bytes =file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            String images = "/img/" + file.getOriginalFilename();



            byte[] bytess =video.getBytes();
            Path pathh = Paths.get(UPLOADED_FOLDER_VIDEO + video.getOriginalFilename());
            Files.write(pathh, bytess);
            String videoo = "/videos/" + video.getOriginalFilename();


            anime.setVideoAnime(videoo);
            anime.setCoverImg(images);
            movieService.createMovie(anime);
        } catch (IOException e){
            e.printStackTrace();
        }

        return "redirect:/";
    }





    @GetMapping("/category/new")
    public String newCategory(@ModelAttribute("category") Category category , Model model, HttpSession session){
        return "newCategory.jsp";
    }
    @PostMapping("/category/new")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model , HttpSession session){
        if (result.hasErrors()){
            return "newCategory.jsp";
        }

        categoriesServices.createCategory(category);
        return "redirect:/";
    }



    @GetMapping("/categories/{id}")
    public String showCategori(@PathVariable("id") Long id, Model model , HttpSession session){

        Category category = categoriesServices.getById(id);
        model.addAttribute("category", category);
        model.addAttribute("assingMovie", movieService.assingMovie( category));
        model.addAttribute("unassingMovie", movieService.unAssingMovie(category));
        return "addMovieToCategory.jsp";
    }
    @PostMapping("/categories/{id}")
    public String addInProduct(@PathVariable("id") Long id, @RequestParam("productId") Long productId, Model model){
        Category category = categoriesServices.getById(id);
        Movie movie = movieService.findMovie(productId);
        category.getMovies().add(movie);
        categoriesServices.updateCategory(category);
        return "redirect:/categories/"+id;
    }
}
