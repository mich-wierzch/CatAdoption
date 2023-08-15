package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.UserCommentDto;
import com.CatShelter.CatShelter.mapper.UserCommentMapper;
import com.CatShelter.CatShelter.model.UserCommentModel;
import com.CatShelter.CatShelter.repository.UserCommentRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserCommentService {
    private final UserCommentRepository userCommentRepository;
    private final UserRepository userRepository;
    private final UserCommentMapper userCommentMapper;
    private final AuthenticationService authenticationService;

    public String addComment(Long userId, String text){
        try {
            UserCommentModel comment = UserCommentModel.builder()
                    .user(userRepository.findByUserId(userId))
                    .commenter(userRepository.findByUserId(authenticationService.getCurrentUserId()))
                    .text(text)
                    .timestamp(LocalDateTime.now())
                    .build();
            userCommentRepository.save(comment);
            return "Comment added";
        } catch (NullPointerException e){
            return null;
        }
    }

    public List<UserCommentDto> findAllCommentsForUser(Long userId){
        List<UserCommentModel> comment = userCommentRepository.findByUserUserId(userId);
        return comment.stream()
                .map(userCommentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UserCommentDto removeComment(Long commentId){
        UserCommentModel comment = userCommentRepository.findByCommentId(commentId);
        try {
            if (comment.getUser().getUserId().equals(authenticationService.getCurrentUserId())
                    || comment.getCommenter().getUserId().equals(authenticationService.getCurrentUserId())) {
                userCommentRepository.delete(comment);
                return userCommentMapper.convertToDto(comment);
            } else return null;
        } catch (NullPointerException e){
            return null;
        }

    }


}
