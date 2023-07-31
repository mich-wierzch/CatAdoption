package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.PostService;
import com.CatShelter.CatShelter.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@RequestMapping(path="/api/posts")
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping(path="/add")
    public RedirectView add(PostModel postModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserModel) authentication.getPrincipal()).getUserId();

        postService.createPost(postModel, userId);
        return new RedirectView("/main");
    }
    @GetMapping(path="/getAll")
    public List<PostModel> getPosts(){
        return postService.findAllPosts();
    }
}
