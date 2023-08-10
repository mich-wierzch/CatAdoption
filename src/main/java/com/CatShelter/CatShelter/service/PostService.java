package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.CreatePostDto;
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

    public CreatePostDto createPost(CreatePostDto request){
    try {

        Long userId = getCurrentUserId();

        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        //TODO: CHECK IF FILE UPLOADING WORKS CORRECTLY
        PostModel postModel = PostModel.builder()
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .breed(request.getBreed())
                .imageFile(request.getImageFile())
                .description(request.getDescription())
                .location(request.getLocation())
                .user(userModel)
                .createdAt(LocalDate.now())
                .build();
        postRepository.save(postModel);
        return request;
    } catch (NullPointerException e) {
        throw new IllegalArgumentException("No user logged in");
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

            post.setName(Optional.ofNullable(postDto.getName()).orElse(post.getName()));
            post.setGender(Optional.ofNullable(postDto.getGender()).orElse(post.getGender()));
            post.setAge(Optional.ofNullable(postDto.getAge()).orElse(post.getAge()));
            post.setBreed(Optional.ofNullable(postDto.getBreed()).orElse(post.getBreed()));
            post.setImageFile(Optional.ofNullable(postDto.getImageFile()).orElse(post.getImageFile()));
            post.setDescription(Optional.ofNullable(postDto.getDescription()).orElse(post.getDescription()));
            post.setLocation(Optional.ofNullable(postDto.getLocation()).orElse(post.getLocation()));

            return postMapper.convertToDto(post);
        } catch (NullPointerException e){
            throw new IllegalArgumentException("No post with id" + postId + " found");
        }

    }
    public Authentication getCurrentAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getCurrentUserId(){
        Authentication authentication = getCurrentAuthentication();
        return ((UserModel) authentication.getPrincipal()).getUserId();
    }


}
