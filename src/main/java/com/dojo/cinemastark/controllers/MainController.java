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


    // USER




    // INDEX

    @GetMapping("/")
    public String index(Model model , HttpSession session){
        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        if (loggedInUserID != null){
            model.addAttribute("userId" , userService.findOneUser(loggedInUserID));
            if (loggedInUserID == 1){
                return "redirect:/admin";
            }
        }
        model.addAttribute("movies" , movieService.allMovies());
        model.addAttribute("moviesThree" , movieService.topthree());
        model.addAttribute("category", categoriesServices.getall());
        model.addAttribute("newAddMovies", movieService.newAdd());
        model.addAttribute("trendingAnime", movieService.trending());


        return "index.jsp";
    }


    // LOGIN GET MAPPING
    @GetMapping("/login")
    public String login(Model model , @ModelAttribute("newLogin") LoginUser newLogin){
        model.addAttribute("category", categoriesServices.getall());
        return "login.jsp";
    }



    // SINGUP GET MAPPING
    @GetMapping("/signup")
    public String signup(Model model, @ModelAttribute("newUser") User newUser,
                         @ModelAttribute("newLogin") User newLogin){
        model.addAttribute("category", categoriesServices.getall());
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());

        return "signup.jsp";
    }


    // LOGIN POST MAPPING

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
                        HttpSession session) {
        User user = userService.login(newLogin, result);

        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "login.jsp";
        }
        session.setAttribute("loggedInUserID", user.getId());
        return "redirect:/";
    }



    // SIGNUP POST MAPPING

    @PostMapping("/signup")
    public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
                           HttpSession session) {
        userService.register(newUser, result);

        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "signup.jsp";
        }

        session.setAttribute("loggedInUserID", newUser.getId());
        return "redirect:/";
    }


    // WATCHING GET MAPPING
    @GetMapping("/watching/{id}")
    public String watching(@PathVariable("id")Long id,@ModelAttribute("newComment") Comment comment , Model model, HttpSession session){
        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        if (loggedInUserID != null){
            model.addAttribute("userId" , userService.findOneUser(loggedInUserID));
            if (loggedInUserID == 1){
                return "redirect:/admin";
            }
        }
        Movie movie = movieService.findMovie(id);
        List<Comment> comments = commentService.findAllByMovieId(id);
        model.addAttribute("moviesId" , movie);
        model.addAttribute("comment", comments);
        model.addAttribute("categories", categoriesServices.getall());
        Integer count = movie.getViews();
        if(count == null){
            movie.setViews(1);
            movieService.createMovie(movie);
        }else {
            count++;
            movie.setViews(count);
            movieService.createMovie(movie);
        }
        return "anime-watching.jsp";
    }


    // WATCHING POST MAPPING

    @PostMapping("/watching/{id}")
    public String comment(@PathVariable("id") Long id, @Valid @ModelAttribute("newComment") Comment comment, BindingResult result,
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



    // DETAILS GET MAPPING

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id")Long id,@ModelAttribute("newComment") Comment comment , Model model , HttpSession session){
        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");

        if (loggedInUserID != null){
            model.addAttribute("userId" , userService.findOneUser(loggedInUserID));
            if (loggedInUserID == 1){
                return "redirect:/admin";
            }
        }
        Movie movie = movieService.findMovie(id);
        List<Comment> comments = commentService.findAllByMovieId(id);
        model.addAttribute("moviesId" , movie);
        model.addAttribute("movies" , movieService.allMovies());
        model.addAttribute("categories", categoriesServices.getall());
        model.addAttribute("comment", comments);
        return "anime-details.jsp";
    }



    // DETAILS POST MAPPING

    @PostMapping("/details/{id}")
    public String addComment(@PathVariable("id") Long id, @Valid @ModelAttribute("newComment") Comment comment, BindingResult result,
                             HttpSession session){
        if (result.hasErrors()) {
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




    // CATEGORY GET MAPPING

    @GetMapping("/category/{id}")
    public  String categoryMovie(@PathVariable("id")Long id , Model model, HttpSession session){

        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        if (loggedInUserID != null){
            model.addAttribute("userId" , userService.findOneUser(loggedInUserID));
            if (loggedInUserID == 1){
                return "redirect:/admin";
            }
        }
        Category categories = categoriesServices.getById(id);
        model.addAttribute("category" ,categories );
        model.addAttribute("categories", categoriesServices.getall());
        model.addAttribute("MoviesInCategory" , movieService.assingMovie(categories));
        return "categories.jsp";
    }




    // SEARCH GET MAPPING

    @GetMapping("/search/{AnimeName}")
    public String serarchByName(@PathVariable("AnimeName") String AnimeName, Model model, HttpSession session){

        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        if (loggedInUserID != null){
            model.addAttribute("userId" , userService.findOneUser(loggedInUserID));
            if (loggedInUserID == 1){
                return "redirect:/admin";
            }
        }
        model.addAttribute("serachBar" , movieService.findByName(AnimeName));
        model.addAttribute("categories", categoriesServices.getall());
        return "search.jsp";
    }



    // SEARCH POST MAPPING
    @PostMapping("/search")
    public String searchput(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("movieSerch" , movieService.findByName(name));
        return "redirect:/search/" + name;
    }



    // FAVORITES GET MAPPING ADD

    @GetMapping("/favorites/{id}")
    public String wishlist(@PathVariable("id")Long id, HttpSession session){
        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        User user = userService.findOneUser(loggedInUserID);
        Movie movie = movieService.findMovie(id);

        user.getFavorites().add(movie);
        movie.setFav(true);
        movieService.createMovie(movie);
        return "redirect:/details/{id}";
    }




    // FAVORITES GET MAPPING REMOVE

    @GetMapping("/delete/favorites/{id}")
    public String deleteWishlist(@PathVariable("id")Long id, HttpSession session){
        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        User user = userService.findOneUser(loggedInUserID);
        Movie movie = movieService.findMovie(id);

        user.getFavorites().remove(movie);
        movie.setFav(false);
        movieService.createMovie(movie);
        return "redirect:/details/{id}";
    }



    // FAVORITES GET MAPPING


    @GetMapping("/favorite/{id}")
    public String deleteWishlist(@PathVariable("id")Long id, HttpSession session ,Model model) {
        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        User user = userService.findOneUser(loggedInUserID);
        model.addAttribute("fav" , movieService.findByUser(user));
        model.addAttribute("categories", categoriesServices.getall());
        if (loggedInUserID != null){
            model.addAttribute("userId" , userService.findOneUser(loggedInUserID));
            if (loggedInUserID == 1){
                return "redirect:/admin";
            }
        }
        return "favorites.jsp";
    }




    //  ADMIN






    private static String UPLOADED_FOLDER = "src/main/resources/static/img/";
    private static String UPLOADED_FOLDER_VIDEO = "src/main/resources/static/videos/";


    // CREATE ANIME GET MAPPING


    @GetMapping("/anime/new")
    public String newAnime(@ModelAttribute("anime") Movie anime, Model model , HttpSession session){
        if (session.getAttribute("loggedInUserID") == null){
            return "redirect:/";
        }
        if (!session.getAttribute("loggedInUserID").equals(1)){
            return "redirect:/";
        }
        return "newAnime.jsp";
    }

    // CREATE ANIME POST MAPPING

    @PostMapping("/anime/new")
    public String createAnime(@Valid @ModelAttribute("anime") Movie anime, BindingResult result , @RequestParam("pic") MultipartFile file,  @RequestParam("video") MultipartFile video,HttpSession session , RedirectAttributes redirectAttributes){
        Long loggedInUserID = (Long) session.getAttribute("loggedInUserID");
        User user = userService.findOneUser(loggedInUserID);
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
            anime.setFav(false);
            anime.setUser(user);
            movieService.createMovie(anime);
        } catch (IOException e){
            e.printStackTrace();
        }

        return "redirect:/";
    }






    // CREATE CATEGORY GET MAPPING


    @GetMapping("/category/new")
    public String newCategory(@ModelAttribute("category") Category category , Model model, HttpSession session){

        if (session.getAttribute("loggedInUserID") == null){
            return "redirect:/";
        }
        if (!session.getAttribute("loggedInUserID").equals(1)){
            return "redirect:/";
        }
        return "newCategory.jsp";
    }




    // CREATE CATEGORY POST MAPPING


    @PostMapping("/category/new")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model , HttpSession session){
        if (result.hasErrors()){
            return "newCategory.jsp";
        }
        categoriesServices.createCategory(category);
        return "redirect:/";
    }




    // ADD ANIME TO CATEGORY GET MAPPING


    @GetMapping("/categories/{id}")
    public String showCategori(@PathVariable("id") Long id, Model model , HttpSession session){

        if (session.getAttribute("loggedInUserID") == null){
            return "redirect:/";
        }
        if (!session.getAttribute("loggedInUserID").equals(1)){
            return "redirect:/";
        }

        Category category = categoriesServices.getById(id);
        model.addAttribute("category", category);
        model.addAttribute("assingMovie", movieService.assingMovie( category));
        model.addAttribute("unassingMovie", movieService.unAssingMovie(category));
        return "addMovieToCategory.jsp";
    }

    // ADD ANIME TO CATEGORY POST MAPPING
    @PostMapping("/categories/{id}")
    public String addInProduct(@PathVariable("id") Long id, @RequestParam("productId") Long productId, Model model, HttpSession session){

        if (session.getAttribute("loggedInUserID") == null){
            return "redirect:/";
        }
        if (!session.getAttribute("loggedInUserID").equals(1)){
            return "redirect:/";
        }
        Category category = categoriesServices.getById(id);
        Movie movie = movieService.findMovie(productId);
        category.getMovies().add(movie);
        categoriesServices.updateCategory(category);
        return "redirect:/categories/"+id;
    }


    // Log Out

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
