package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.model.UserRole;
import com.CatShelter.CatShelter.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    UserModel newUser;
    @BeforeEach
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

    @AfterEach
    void deleteUser(){
        userRepository.delete(newUser);
    }

    @Test
    void shouldSeeIfUserAddedCorrectlyToDatabase() {
        //given createUser()
        //when
        boolean exists = userRepository.existsByUsername(newUser.getUsername());
        //then
        assertTrue(exists);
    }

    @Test
    void shouldFindUserInTheDatabaseAndUpdateUserInformation(){
        //given
        UserModel existingUser = userRepository.findByUsername("TestUser123");
        //when
        String oldFirstName = existingUser.getFirstName();
        existingUser.setFirstName("Arnold");
        userRepository.save(existingUser);
        String newFirstName = existingUser.getFirstName();
        //then
        assertNotEquals(oldFirstName, newFirstName);
    }
    @Test
    void shouldFindUserInTheDatabaseAndDeleteUserFromDatabase(){
        //given
        UserModel existingUser = userRepository.findByUsername("TestUser123");
        //when
        userRepository.delete(existingUser);
        //then
        assertFalse(userRepository.existsByUsername("TestUser123"));
    }


}