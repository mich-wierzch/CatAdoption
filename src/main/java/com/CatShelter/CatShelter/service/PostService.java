package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.model.CatModel;
import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostModel createPost(CatModel cat, UserModel user){
        PostModel postModel = PostModel.builder()
                .catName(cat.getName())
                .catSex(cat.getSex())
                .catAge(cat.getAge())
                .catBreed(cat.getBreed())
                .imageUrl(cat.getImageUrl())
                .description(cat.getDescription())
                .location(cat.getLocation())
                .userFirstName(user.getFirstName())
                .userLastName(user.getLastName())
                .userMobilePhone(user.getMobile())
                .userId(user.getUserId())
                .build();
        postRepository.save(postModel);
        return postModel;
    }

    public List<PostModel> findAllPosts(){
        return postRepository.findAll();
    }
}
