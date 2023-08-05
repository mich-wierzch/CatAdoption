package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.mapper.PostMapper;
import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostDto createPost(PostDto request){

        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        Long userId = ((UserModel) authentication.getPrincipal()).getUserId();

        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        //TODO: CHECK IF FILE UPLOADING WORKS CORRECTLY
        PostModel postModel = PostModel.builder()
                .catName(request.getCatName())
                .catSex(request.getCatSex())
                .catAge(request.getCatAge())
                .catBreed(request.getCatBreed())
                .imageFile(request.getImageFile())
                .description(request.getDescription())
                .location(request.getLocation())
                .userFirstName(userModel.getFirstName())
                .userLastName(userModel.getLastName())
                .userMobilePhone(userModel.getMobile())
                .user(userModel)
                .createdAt(LocalDate.now())
                .build();
        postRepository.save(postModel);
        return request;
    }

    public List<PostDto> findAllPosts(){
        List<PostModel> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> findPostsByUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserModel) authentication.getPrincipal()).getUserId();

        List<PostModel> posts = postRepository.findByUserUserId(userId);
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public PostDto findPostByPostId(Long postId){
        PostModel post = postRepository.findByPostId(postId);
        return postMapper.convertToDto(post);
    }

    public void deletePost(Long postId){
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " not found"));

        postRepository.deleteById(postId);

    }


}
