package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.mapper.PostMapper;
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
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

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
                .user(userModel)
                .createdAt(LocalDate.now())
                .build();
        postRepository.save(postModel);
        return postModel;
    }

    public List<PostDto> findAllPosts(){
        List<PostModel> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> findPostsByUser(Long userId){
        List<PostModel> posts = postRepository.findByUserUserId(userId);
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> findPostByPostId(Long postId){
        List<PostModel> posts = postRepository.findByPostId(postId);
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }


}
