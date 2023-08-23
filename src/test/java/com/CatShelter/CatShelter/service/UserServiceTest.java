package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.RegisterRequestDto;
import com.CatShelter.CatShelter.dto.UserDto;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.model.UserRole;
import com.CatShelter.CatShelter.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    void deleteUser(){
        userRepository.delete(newUser);
    }


    @Test
    void shouldRegisterNewUserAndAddUserToDatabase(){
        //given
        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setUsername("TestUser123");
        requestDto.setEmail("TestEmail123@gmail.com");
        requestDto.setPassword("TestPassword123@gmail.com");
        //when
        userService.addUser(requestDto);
        //then
        assertTrue(userRepository.existsByUsername(requestDto.getUsername()));
        userRepository.delete(userRepository.findByUsername(requestDto.getUsername()));
    }

    @Test
    void shouldFetchUserInformation(){
        //given
        createUser();
        Long userId = newUser.getUserId();
        //when
        UserDto userDto = userService.fetchUserInformation(userId);
        //then
        assertNotNull(userDto);

        deleteUser();

    }




}