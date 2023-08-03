package com.CatShelter.CatShelter.mapper;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.model.PostModel;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto convertToDto(PostModel postModel){
        return PostDto.builder()
                .catName(postModel.getCatName())
                .catSex(postModel.getCatSex())
                .catAge(postModel.getCatAge())
                .catBreed(postModel.getCatBreed())
                .imageUrl(postModel.getImageUrl())
                .description(postModel.getDescription())
                .location(postModel.getLocation())
                .createdAt(postModel.getCreatedAt())
                .userFirstName(postModel.getUserFirstName())
                .userLastName(postModel.getUserLastName())
                .userMobilePhone(postModel.getUserMobilePhone())
                .build();
    }
}
