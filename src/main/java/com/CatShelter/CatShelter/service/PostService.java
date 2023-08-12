package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.CreatePostDto;
import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.mapper.PostMapper;
import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.io.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final Cloudinary cloudinary;

    public CreatePostDto createPost(CreatePostDto request) {

//        TODO: CHECK IF CLOUDINARY WORKS
//        byte[] imageBytes = Base64.getDecoder().decode(request.getImageFile());
//        File tempFile = null;
//        String cloudinaryImageUrl = null;
//        try {
//            tempFile = File.createTempFile("temp", ".jpg");
//            try (OutputStream os = new FileOutputStream(tempFile)) {
//                os.write(imageBytes);
//            }
//            Map<?, ?> cloudinaryReponse = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
//        cloudinaryImageUrl = cloudinaryReponse.get("url").toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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
//                    .imageFile(cloudinaryImageUrl)
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

    public List<PostDto> findAllPosts(PageRequest pageable){
        Page<PostModel> page = postRepository.findAll(pageable);
        List<PostModel> posts = page.getContent();
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<PostDto> findPostsByUser(Long userId, PageRequest pageable){
        Page<PostModel> page = postRepository.findByUserUserId(userId, pageable);
        List<PostModel> posts = page.getContent();
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
        //TODO: CHECK IF POST BEING DELETED BELONGS TO THE USER
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " not found"));

        postRepository.deleteById(postId);

    }
    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto){
    //TODO: IMPLEMENT CHECKING IF POST BEING UPDATED BELONGS TO THE USER
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
