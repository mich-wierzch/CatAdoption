package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.CreatePostDto;
import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.dto.UpdatePostDto;
import com.CatShelter.CatShelter.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
@RequestMapping(path="/api/posts")
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping(path="/add")
    public ResponseEntity<String> add(@RequestBody CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);

    }
    @GetMapping(path="/all")
    public List<PostDto> getPosts(@RequestParam int page, @RequestParam int size){
        PageRequest pageable = PageRequest.of(page, size);
        return postService.findAllPosts(pageable);
    }

    @GetMapping(path="/by-user/{userId}")
    public List<PostDto> getPostsByUser(@PathVariable Long userId, @RequestParam int page, @RequestParam int size){
        PageRequest pageable = PageRequest.of(page, size);
        return postService.findPostsByUser(userId, pageable);
    }

    @GetMapping(path="/{postId}")
    public PostDto getPostByPostId(@PathVariable Long postId){
        return postService.findPostByPostId(postId);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public PostDto handleTypeMismatchException(){
        return null;
    }

    @DeleteMapping(path="/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);

    }

    @PatchMapping(path="/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId,
                                    @RequestBody UpdatePostDto updatePostDto){
        return postService.updatePost(postId, updatePostDto);
    }

}
