package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.model.UserRole;
import com.CatShelter.CatShelter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel addUser(UserModel request){
        boolean userExists = userRepository.findByUsername(request.getUsername()).isPresent();
        boolean userEmailExists = userRepository.findByEmail(request.getEmail()).isPresent();

        if (userExists){
            throw new IllegalStateException("Username taken");
        }
        if(userEmailExists){
            throw new IllegalStateException("Email already in use");
        }
        UserModel userModel = UserModel.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mobile(request.getMobile())
                .userRole(UserRole.USER)
                .build();
        System.out.println(userModel);
        userRepository.save(userModel);
        return userModel;

    }

}
