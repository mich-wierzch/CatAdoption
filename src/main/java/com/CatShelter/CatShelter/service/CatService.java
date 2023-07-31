package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.repository.CatRepository;
import com.CatShelter.CatShelter.model.CatModel;
import com.CatShelter.CatShelter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    public CatModel addCat(CatModel request, Long userId){
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        CatModel catModel = CatModel.builder()
                .name(request.getName())
                .sex(request.getSex())
                .age(request.getAge())
                .breed(request.getBreed())
                .imageUrl(request.getImageUrl())
                .location(request.getLocation())
                .description(request.getDescription())
                .userModel(userModel)
                .build();
        postService.createPost(catModel, userModel);
        catRepository.save(catModel);
        return catModel;
    }


}
