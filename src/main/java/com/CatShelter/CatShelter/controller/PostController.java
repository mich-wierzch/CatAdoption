package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
    public List<PostDto> getPosts(){
        return postService.findAllPosts();
    }

    @GetMapping(path="/getByUser")
    public List<PostDto> getPostsByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserModel) authentication.getPrincipal()).getUserId();

        return postService.findPostsByUser(userId);
    }

    @GetMapping(path="/getByPostId/{postId}")
    public List<PostDto> getPostByPostId(@PathVariable Long postId){
        return postService.findPostByPostId(postId);
    }

    @DeleteMapping(path="/delete/{postId}")
    public void deletePost(@PathVariable Long postId){
        postRepository.deleteById(postId);
    }
}
