package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.service.PostService;
import com.CatShelter.CatShelter.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping(path="/api/posts")
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    @GetMapping(path="/getAll")
    public List<PostModel> getPosts(){
        return postService.findAllPosts();
    }
}
