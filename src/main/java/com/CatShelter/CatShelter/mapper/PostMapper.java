package com.CatShelter.CatShelter.mapper;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.model.PostImagesModel;
import com.CatShelter.CatShelter.model.PostModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public PostDto convertToDto(PostModel postModel){
//        List<PostImageDto> imageDtos = postModel.getImages().stream()
//                .map(imageModel -> PostImageDto.builder()
//                        .image(imageModel.getImage())
//                        .isFeatured(imageModel.isFeatured())
//                        .build())
//                .collect(Collectors.toList());
//
        List<String> imageUrls = postModel.getImages().stream()
                .map(PostImagesModel::getImage)
                .toList();

        return PostDto.builder()
                .id(postModel.getPostId())
                .name(postModel.getName())
                .gender(postModel.getGender())
                .age(postModel.getAge())
                .breed(postModel.getBreed())
                .images(imageUrls)
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
