//package com.CatShelter.CatShelter.data;
//
//import com.CatShelter.CatShelter.model.*;
//import com.CatShelter.CatShelter.repository.PostImagesRepository;
//import com.CatShelter.CatShelter.repository.PostRepository;
//import com.CatShelter.CatShelter.repository.UserCommentRepository;
//import com.CatShelter.CatShelter.repository.UserRepository;
//import com.github.javafaker.Faker;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Component
//@AllArgsConstructor
//public class TestDataLoader implements CommandLineRunner {
//
//    private final Logger logger = LoggerFactory.getLogger(TestDataLoader.class);
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final Faker faker;
//    private final PostRepository postRepository;
//    private final PostImagesRepository postImagesRepository;
//    private final UserCommentRepository userCommentRepository;
//    @Override
//    public void run(String... args) throws Exception {
//
//        logger.info("Creating user test data...");
//        UserModel user1 = UserModel.builder()
//                .username("Zeke123")
//                .email("Zeke123@gmail.com")
//                .password(bCryptPasswordEncoder.encode("Zeke123"))
//                .userRole(UserRole.USER)
//                .firstName("Zeke")
//                .lastName("Zekowski")
//                .mobile("356432456")
//                .build();
//        userRepository.save(user1);
//
//        UserModel user2 = UserModel.builder()
//                .username("Luther123")
//                .email("Luther123@gmail.com")
//                .password(bCryptPasswordEncoder.encode("Luther123"))
//                .userRole(UserRole.USER)
//                .firstName("Luther")
//                .lastName("Lutherwoski")
//                .mobile("958495859")
//                .build();
//        userRepository.save(user2);
//
//        UserModel user3 = UserModel.builder()
//                .username("Kojo123")
//                .email("Kojo123@gmail.com")
//                .password(bCryptPasswordEncoder.encode("Kojo123"))
//                .userRole(UserRole.USER)
//                .firstName("Kojo")
//                .lastName("Kojowski")
//                .mobile("948372938")
//                .build();
//        userRepository.save(user3);
//
//        logger.info("Creating sample posts...");
//
//        List<String> imageUrls = new ArrayList<>();
//        imageUrls.add("https://res.cloudinary.com/dvuvqeolh/image/upload/v1692124900/cat_ouxyx7.webp");
//        imageUrls.add("https://res.cloudinary.com/dvuvqeolh/image/upload/v1692124952/catweird_o1qxxy.jpg");
//
//
//
//
//        for (int i = 0; i<30 ;i++) {
//            PostLocation location = new PostLocation();
//            location.setCity(faker.address().city());
//            location.setLatitude(Float.parseFloat(faker.address().latitude().replace(",", ".")));
//            location.setLongitude(Float.parseFloat(faker.address().longitude().replace(",", ".")));
//
//
//            PostModel post = PostModel.builder()
//                    .name(faker.animal().name())
//                    .gender(randomizeGender((int) (Math.random() * 2 + 1)))
//                    .age((int) (1 + (Math.random() * 10)))
//                    .breed(faker.cat().breed())
//                    .description("This is a test description for a cat. This cat is a very lovely cat.")
//                    .createdAt(LocalDate.ofYearDay(ThreadLocalRandom.current().nextInt(LocalDate.now().getYear(),
//                                    LocalDate.now().plusYears(1).getYear()),
//                            ThreadLocalRandom.current().nextInt(1, 366)))
//                    .location(location)
//                    .user(user1)
//                    .build();
//
//            postRepository.save(post);
//
//            boolean isFirstImage = true;
//            for (String imageUrl : imageUrls) {
//                PostImagesModel imageModel = PostImagesModel.builder()
//                        .image(imageUrl)
//                        .isFeatured(isFirstImage)
//                        .post(post)
//                        .build();
//                postImagesRepository.save(imageModel);
//                isFirstImage = false;
//            }
//
//            UserCommentModel comment = UserCommentModel.builder()
//                    .user(user2)
//                    .commenter(user1)
//                    .text("New Comment " + i)
//                    .timestamp(LocalDateTime.now())
//                    .build();
//            userCommentRepository.save(comment);
//
//        }
//        for (int i = 0; i<30 ;i++) {
//            PostLocation location = new PostLocation();
//            location.setCity(faker.address().city());
//            location.setLatitude(Float.parseFloat(faker.address().latitude().replace(",", ".")));
//            location.setLongitude(Float.parseFloat(faker.address().longitude().replace(",", ".")));
//
//
//            PostModel post = PostModel.builder()
//                    .name(faker.animal().name())
//                    .gender(randomizeGender((int) (Math.random() * 2 + 1)))
//                    .age((int) (1 + (Math.random() * 10)))
//                    .breed(faker.cat().breed())
//                    .description("This is a test description for a cat. This cat is a very lovely cat.")
//                    .createdAt(LocalDate.ofYearDay(ThreadLocalRandom.current().nextInt(LocalDate.now().getYear(),
//                                    LocalDate.now().plusYears(1).getYear()),
//                            ThreadLocalRandom.current().nextInt(1, 366)))
//                    .location(location)
//                    .user(user2)
//                    .build();
//
//            postRepository.save(post);
//
//            boolean isFirstImage = true;
//            for (String imageUrl : imageUrls) {
//                PostImagesModel imageModel = PostImagesModel.builder()
//                        .image(imageUrl)
//                        .isFeatured(isFirstImage)
//                        .post(post)
//                        .build();
//                postImagesRepository.save(imageModel);
//                isFirstImage = false;
//            }
//            UserCommentModel comment = UserCommentModel.builder()
//                    .user(user1)
//                    .commenter(user2)
//                    .text("New Comment " + i)
//                    .timestamp(LocalDateTime.now())
//                    .build();
//            userCommentRepository.save(comment);
//
//        }
//        for (int i = 0; i<30 ;i++) {
//            PostLocation location = new PostLocation();
//            location.setCity(faker.address().city());
//            location.setLatitude(Float.parseFloat(faker.address().latitude().replace(",", ".")));
//            location.setLongitude(Float.parseFloat(faker.address().longitude().replace(",", ".")));
//
//
//            PostModel post = PostModel.builder()
//                    .name(faker.animal().name())
//                    .gender(randomizeGender((int) (Math.random() * 2 + 1)))
//                    .age((int) (1 + (Math.random() * 10)))
//                    .breed(faker.cat().breed())
//                    .description("This is a test description for a cat. This cat is a very lovely cat.")
//                    .createdAt(LocalDate.ofYearDay(ThreadLocalRandom.current().nextInt(LocalDate.now().getYear(),
//                                    LocalDate.now().plusYears(1).getYear()),
//                            ThreadLocalRandom.current().nextInt(1, 366)))
//                    .location(location)
//                    .user(user3)
//                    .build();
//
//            postRepository.save(post);
//
//            boolean isFirstImage = true;
//            for (String imageUrl : imageUrls) {
//                PostImagesModel imageModel = PostImagesModel.builder()
//                        .image(imageUrl)
//                        .isFeatured(isFirstImage)
//                        .post(post)
//                        .build();
//                postImagesRepository.save(imageModel);
//                isFirstImage = false;
//            }
//            UserCommentModel comment = UserCommentModel.builder()
//                    .user(user3)
//                    .commenter(user2)
//                    .text("New Comment " + i)
//                    .timestamp(LocalDateTime.now())
//                    .build();
//            userCommentRepository.save(comment);
//
//        }
//
//
//    }
//    public static String randomizeGender(int x){
//        return x == 1 ? "Male" : "Female";
//    }
//}
