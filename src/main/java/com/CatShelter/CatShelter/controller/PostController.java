package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path="/getByUser/{userId}")
    public List<PostDto> getPostsByUser(@PathVariable Long userId){
        return postService.findPostsByUser(userId);
    }

    @GetMapping(path="/getByPostId/{postId}")
    public PostDto getPostByPostId(@PathVariable Long postId){
        return postService.findPostByPostId(postId);
    }

    @DeleteMapping(path="/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "Post deleted";
    }

    @PatchMapping(path="/update/{postId}")
    public PostDto updatePost(@PathVariable Long postId,
                              @RequestBody PostDto postDto){
        return postService.updatePost(postId, postDto);
    }
}
