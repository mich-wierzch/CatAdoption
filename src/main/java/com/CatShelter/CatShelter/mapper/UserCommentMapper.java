package com.CatShelter.CatShelter.mapper;

import com.CatShelter.CatShelter.dto.UserCommentDto;
import com.CatShelter.CatShelter.model.UserCommentModel;
import org.springframework.stereotype.Component;

@Component
public class UserCommentMapper {

    public UserCommentDto convertToDto(UserCommentModel userCommentModel){
        return UserCommentDto.builder()
                .user(userCommentModel.getUser().getUsername())
                .commenter(userCommentModel.getCommenter().getUsername())
                .text(userCommentModel.getText())
                .timestamp(userCommentModel.getTimestamp())
                .build();
    }
}
