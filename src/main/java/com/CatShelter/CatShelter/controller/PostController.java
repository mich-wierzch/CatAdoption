package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.CreatePostDto;
import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.awt.print.Pageable;
import java.util.List;
@RequestMapping(path="/api/posts")
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping(path="/add")
    public CreatePostDto add(@RequestBody CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);

    }
    @GetMapping(path="/getAll")
    public List<PostDto> getPosts(@RequestParam int page, @RequestParam int size){
        PageRequest pageable = PageRequest.of(page, size);
        return postService.findAllPosts(pageable);
    }

    @GetMapping(path="/getByUser/{userId}")
    public List<PostDto> getPostsByUser(@PathVariable Long userId, @RequestParam int page, @RequestParam int size){
        PageRequest pageable = PageRequest.of(page, size);
        return postService.findPostsByUser(userId, pageable);
    }

    @GetMapping(path="/getByPostId/{postId}")
    public PostDto getPostByPostId(@PathVariable Long postId){
        return postService.findPostByPostId(postId);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public PostDto handleTypeMismatchException(){
        return null;
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
