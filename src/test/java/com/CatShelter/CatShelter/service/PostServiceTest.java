package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.CreatePostDto;
import com.CatShelter.CatShelter.model.*;
import com.CatShelter.CatShelter.repository.PostImagesRepository;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostImagesRepository postImagesRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    UserModel newUser;

    void createUser(){
        this.newUser = UserModel.builder()
                .username("TestUser123")
                .email("TestEmail123@gmail.com")
                .password(bCryptPasswordEncoder.encode("TestPassword123"))
                .firstName("Tester")
                .lastName("Testowski")
                .mobile("958483948")
                .userRole(UserRole.USER)
                .build();
        userRepository.save(newUser);
    }
    @Test
    void shouldCreateNewPost(){
        //given
        createUser();
        List<String> imageUrls = List.of("https://res.cloudinary.com/dvuvqeolh/image/upload/v1692124900/cat_ouxyx7.webp",
                "https://res.cloudinary.com/dvuvqeolh/image/upload/v1692124952/catweird_o1qxxy.jpg");
        PostLocation postLocation = new PostLocation("Warsaw", 30.50F, 30.40F);
        CreatePostDto request = new CreatePostDto(
                "Emilia",
                "Female",
                3,
                "British Shorthair",
                imageUrls,
                "Very feisty cat",
                postLocation,
                LocalDate.now()
        );
        //when
        PostModel postModel = PostModel.builder()
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .breed(request.getBreed())
                .description(request.getDescription())
                .location(request.getLocation())
                .user(newUser)
                .createdAt(request.getCreatedAt())
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
        boolean postSaved = postRepository.existsById(postModel.getPostId());
        //then
        assertTrue(postSaved);


    }
}
