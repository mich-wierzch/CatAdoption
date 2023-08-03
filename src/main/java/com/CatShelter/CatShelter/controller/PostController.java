package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping(path="/api/posts")
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping(path="/add")
    public PostDto add(PostDto postDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserModel) authentication.getPrincipal()).getUserId();

        return postService.createPost(postDto, userId);

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
    public PostDto getPostByPostId(@PathVariable Long postId){
        return postService.findPostByPostId(postId);
    }

    @DeleteMapping(path="/delete/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }
}
