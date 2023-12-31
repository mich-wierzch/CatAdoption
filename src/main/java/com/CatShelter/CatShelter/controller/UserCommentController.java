package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.UserCommentDto;
import com.CatShelter.CatShelter.service.UserCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/comments")
@RequiredArgsConstructor
public class UserCommentController {
    private final UserCommentService userCommentService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addComment(@PathVariable Long userId, String text){
        return userCommentService.addComment(userId, text);
    }

    @GetMapping("/find-all/{userId}")
    public List<UserCommentDto> findAllCommentsForUser(@PathVariable Long userId)
    {
        return userCommentService.findAllCommentsForUser(userId);
    }

    @DeleteMapping("/remove/{commentId}")
    public ResponseEntity<String> removeComment(@PathVariable Long commentId){
        return userCommentService.removeComment(commentId);
    }
    @PatchMapping("/edit/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable Long commentId, @RequestParam String text){
        return userCommentService.editComment(commentId, text);
    }


}
