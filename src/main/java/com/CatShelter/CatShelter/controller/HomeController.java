package com.CatShelter.CatShelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(){
        return "landing_page";
    }

    @GetMapping(path="/main")
    public String mainPage(){
        return "index";
    }

    @GetMapping(path="/register")
    public String registerPage(){
        return "registration";
    }

    @GetMapping(path="/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(path="/addCat")
    public String addCatPage(){
        return "addCat";
    }

    @GetMapping(path="/myposts")
    public String myPostsPage() {
        return "my_posts";
    }

}
