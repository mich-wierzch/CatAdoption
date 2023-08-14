package com.CatShelter.CatShelter.controller;

import com.CatShelter.CatShelter.dto.UserCommentDto;
import com.CatShelter.CatShelter.service.UserCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/comment")
@RequiredArgsConstructor
public class UserCommentController {
    private final UserCommentService userCommentService;

    @PostMapping("/add/{userId}")
    public String addComment(@PathVariable Long userId, String text){
        return userCommentService.addComment(userId, text);
    }

    @GetMapping("/findAll/{userId}")
    public List<UserCommentDto> findAllCommentsForUser(@PathVariable Long userId)
    {
        return userCommentService.findAllCommentsForUser(userId);
    }
}
