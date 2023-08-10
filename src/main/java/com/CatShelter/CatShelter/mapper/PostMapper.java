package com.CatShelter.CatShelter.mapper;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.model.PostModel;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto convertToDto(PostModel postModel){
        return PostDto.builder()
                .name(postModel.getName())
                .gender(postModel.getGender())
                .age(postModel.getAge())
                .breed(postModel.getBreed())
                .imageFile(postModel.getImageFile())
                .description(postModel.getDescription())
                .location(postModel.getLocation())
                .createdAt(postModel.getCreatedAt())
                .userFirstName(postModel.getUser().getFirstName())
                .userLastName(postModel.getUser().getLastName())
                .userMobilePhone(postModel.getUser().getMobile())
                .userId(postModel.getUser().getUserId())
                .build();
    }
}
