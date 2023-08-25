package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.UserCommentDto;
import com.CatShelter.CatShelter.mapper.UserCommentMapper;
import com.CatShelter.CatShelter.model.UserCommentModel;
import com.CatShelter.CatShelter.repository.UserCommentRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserCommentService {
    private final UserCommentRepository userCommentRepository;
    private final UserRepository userRepository;
    private final UserCommentMapper userCommentMapper;
    private final AuthenticationService authenticationService;

    public ResponseEntity<String> addComment(Long userId, String text){
        try {
            UserCommentModel comment = UserCommentModel.builder()
                    .user(userRepository.findByUserId(userId))
                    .commenter(userRepository.findByUserId(authenticationService.getCurrentUserId()))
                    .text(text)
                    .timestamp(LocalDateTime.now())
                    .build();
            userCommentRepository.save(comment);
            return ResponseEntity.ok("Comment added");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user with id " + userId + " found or " +
                    "no user logged in");
        }
    }

    public List<UserCommentDto> findAllCommentsForUser(Long userId){
        List<UserCommentModel> comments = userCommentRepository.findByUserUserIdOrderByTimestampDesc(userId);
        return comments.stream()
                .map(userCommentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> removeComment(Long commentId){
        UserCommentModel comment = userCommentRepository.findByCommentId(commentId);
        try {
            if (comment.getUser().getUserId().equals(authenticationService.getCurrentUserId())
                    || comment.getCommenter().getUserId().equals(authenticationService.getCurrentUserId())) {
                userCommentRepository.delete(comment);
                return ResponseEntity.ok("Comment removed");
            } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized to delete this comment!");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No comment with id " + commentId + " found");
        }

    }

    public ResponseEntity<String> editComment(Long commentId, String text){
        UserCommentModel comment = userCommentRepository.findByCommentId(commentId);
        try {
            if (comment.getCommenter().getUserId().equals(authenticationService.getCurrentUserId())) {
                comment.setText(text);
                userCommentRepository.save(comment);
                return ResponseEntity.ok("Comment edited");
            } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized to edit this post");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No comment with id " + commentId + " found");
        }
    }


}
