package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.CreatePostDto;
import com.CatShelter.CatShelter.dto.PostDto;
import com.CatShelter.CatShelter.dto.UpdatePostDto;
import com.CatShelter.CatShelter.mapper.PostMapper;
import com.CatShelter.CatShelter.model.PostImagesModel;
import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.repository.PostImagesRepository;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final PostImagesRepository postImagesRepository;
    private final PostMapper postMapper;
    private final AuthenticationService authenticationService;

    public ResponseEntity<String> createPost(CreatePostDto request) {

        try {

            Long userId = authenticationService.getCurrentUserId();

            UserModel userModel = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

            List<String> imageUrls = request.getImages();

            PostModel postModel = PostModel.builder()
                    .name(request.getName())
                    .gender(request.getGender())
                    .age(request.getAge())
                    .breed(request.getBreed())
                    .description(request.getDescription())
                    .location(request.getLocation())
                    .user(userModel)
                    .createdAt(LocalDate.now())
                    .build();
            postRepository.save(postModel);

            boolean isFirstImage = true;
            for (String imageUrl : imageUrls){
                PostImagesModel imageModel = PostImagesModel.builder()
                        .image(imageUrl)
                        .isFeatured(isFirstImage)
                        .post(postModel)
                        .build();
                postImagesRepository.save(imageModel);
                isFirstImage = false;
            }

            return ResponseEntity.ok("Post added successfully");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user logged in");
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

    public ResponseEntity<String> deletePost(Long postId){
        try {
            PostModel post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
            if (!post.getUser().getUserId().equals(authenticationService.getCurrentUserId())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unable to delete post that doesn't belong" +
                        " to user");
            }
            postRepository.deleteById(postId);
            return ResponseEntity.ok("Post deleted");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with id " + postId + " not found");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

    }
    @Transactional
    public ResponseEntity<String> updatePost(Long postId, UpdatePostDto updatePostDto){

        try {
            PostModel post = postRepository.findByPostId(postId);

            if(!authenticationService.getCurrentUserId().equals(post.getUser().getUserId()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Post does not belong to the user");

            post.setName(Optional.ofNullable(updatePostDto.getName()).orElse(post.getName()));
            post.setGender(Optional.ofNullable(updatePostDto.getGender()).orElse(post.getGender()));
            post.setAge(Optional.ofNullable(updatePostDto.getAge()).orElse(post.getAge()));
            post.setBreed(Optional.ofNullable(updatePostDto.getBreed()).orElse(post.getBreed()));
            post.setDescription(Optional.ofNullable(updatePostDto.getDescription()).orElse(post.getDescription()));
            post.setLocation(Optional.ofNullable(updatePostDto.getLocation()).orElse(post.getLocation()));

            List<String> newImageUrls = updatePostDto.getImages();
            List<PostImagesModel> existingImages = postImagesRepository.findAllByPostPostIdOrderByImageIdAsc(postId);

            int index = 0;
            for(PostImagesModel existingImage : existingImages){
                if(index < newImageUrls.size()) {
                    if (!existingImage.getImage().equals(newImageUrls.get(index))) {
                        existingImage.setImage(newImageUrls.get(index));
                    }
                } else {
                    break;
                }
                index++;
            }

            for (; index < newImageUrls.size(); index++){
                PostImagesModel newImage = PostImagesModel.builder()
                        .image(newImageUrls.get(index))
                        .post(post)
                        .isFeatured(false)
                        .build();
                postImagesRepository.save(newImage);
            }


            postRepository.save(post);



            return ResponseEntity.ok("Post updated");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No post with id " + postId + " found");
        }

    }



}
