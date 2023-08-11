//package com.CatShelter.CatShelter.data;
//
//import com.CatShelter.CatShelter.model.PostModel;
//import com.CatShelter.CatShelter.model.UserModel;
//import com.CatShelter.CatShelter.model.UserRole;
//import com.CatShelter.CatShelter.repository.PostRepository;
//import com.CatShelter.CatShelter.repository.UserRepository;
//import com.github.javafaker.Cat;
//import com.github.javafaker.Faker;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.IntStream;
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
//
//
//
//        List<PostModel> postsUser1 = IntStream.rangeClosed(1, 30)
//                .mapToObj(i -> {
//                            return PostModel.builder()
//                            .name(faker.animal().name())
//                            .gender("Female")
//                            .age((int) (1 + (Math.random() * 10)))
//                            .breed(faker.cat().breed())
//                            .imageFile("Placeholder for Base64 Image File")
//                            .description("This is a test description for a cat. This cat is a very lovely cat.")
//                            .createdAt(LocalDate.now())
//                            .location(faker.address().city())
//                            .user(user1)
//                            .build();
//                }
//                )
//                .toList();
//
//
//        postRepository.saveAll(postsUser1);
//
//        List<PostModel> postsUser2 = IntStream.rangeClosed(1, 30)
//                .mapToObj(i -> {
//                            return PostModel.builder()
//                                    .name(faker.animal().name())
//                                    .gender("Female")
//                                    .age((int) (1 + (Math.random() * 10)))
//                                    .breed(faker.cat().breed())
//                                    .imageFile("Placeholder for Base64 Image File")
//                                    .description("This is a test description for a cat. This cat is a very lovely cat.")
//                                    .createdAt(LocalDate.now())
//                                    .location(faker.address().city())
//                                    .user(user2)
//                                    .build();
//                        }
//                )
//                .toList();
//
//        postRepository.saveAll(postsUser2);
//
//        List<PostModel> postsUser3 = IntStream.rangeClosed(1, 30)
//                .mapToObj(i -> {
//                            return PostModel.builder()
//                                    .name(faker.cat().name())
//                                    .gender("Female")
//                                    .age((int) (1 + (Math.random() * 10)))
//                                    .breed(faker.cat().breed())
//                                    .imageFile("Placeholder for Base64 Image File")
//                                    .description("This is a test description for a cat. This cat is a very lovely cat.")
//                                    .createdAt(LocalDate.now())
//                                    .location(faker.address().city())
//                                    .user(user3)
//                                    .build();
//                        }
//                )
//                .toList();
//
//        postRepository.saveAll(postsUser3);
//    }
//}
