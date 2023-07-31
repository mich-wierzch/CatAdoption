package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostModel createPost(PostModel request, Long userId){
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        PostModel postModel = PostModel.builder()
                .catName(request.getCatName())
                .catSex(request.getCatSex())
                .catAge(request.getCatAge())
                .catBreed(request.getCatBreed())
                .imageUrl(request.getImageUrl())
                .description(request.getDescription())
                .location(request.getLocation())
                .userFirstName(userModel.getFirstName())
                .userLastName(userModel.getLastName())
                .userMobilePhone(userModel.getMobile())
                .userModel(userModel)
                .createdAt(LocalDate.now())
                .build();
        postRepository.save(postModel);
        return postModel;
    }

    public List<PostModel> findAllPosts(){
        return postRepository.findAll();
    }
}
