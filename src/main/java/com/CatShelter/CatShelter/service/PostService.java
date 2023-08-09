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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    try {
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
    } catch (ClassCastException e) {
        throw new IllegalArgumentException("Error occured while creating the post");
    }
    }

    public List<PostDto> findAllPosts(){
        List<PostModel> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<PostDto> findPostsByUser(Long userId){

        List<PostModel> posts = postRepository.findByUserUserId(userId);
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public PostDto findPostByPostId(Long postId){
        try {
            PostModel post = postRepository.findByPostId(postId);
            return postMapper.convertToDto(post);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void deletePost(Long postId){
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " not found"));

        postRepository.deleteById(postId);

    }
    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto){

        try {
            PostModel post = postRepository.findByPostId(postId);

            post.setCatName(Optional.ofNullable(postDto.getCatName()).orElse(post.getCatName()));
            post.setCatSex(Optional.ofNullable(postDto.getCatSex()).orElse(post.getCatSex()));
            post.setCatAge(Optional.ofNullable(postDto.getCatAge()).orElse(post.getCatAge()));
            post.setCatBreed(Optional.ofNullable(postDto.getCatBreed()).orElse(post.getCatBreed()));
            post.setImageFile(Optional.ofNullable(postDto.getImageFile()).orElse(post.getImageFile()));
            post.setDescription(Optional.ofNullable(postDto.getDescription()).orElse(post.getDescription()));
            post.setLocation(Optional.ofNullable(postDto.getLocation()).orElse(post.getLocation()));

            return postMapper.convertToDto(post);
        } catch (NullPointerException e){
            throw new IllegalArgumentException("No post with id" + postId + " found");
        }

    }


}
