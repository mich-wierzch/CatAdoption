package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RequestMapping(path="/api/posts")
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping(path="/add")
    public PostDto add(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);

    }
    @GetMapping(path="/getAll")
    public List<PostDto> getPosts(){
        return postService.findAllPosts();
    }

    @GetMapping(path="/getByUser")
    public List<PostDto> getPostsByUser(){
        return postService.findPostsByUser();
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
