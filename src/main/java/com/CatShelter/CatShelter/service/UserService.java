package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.model.UserRole;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PostRepository postRepository;

    public UserModel addUser(UserModel request){
        boolean userExists = userRepository.findByUsername(request.getUsername()).isPresent();

        if (userExists){
            throw new IllegalStateException("Username taken");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException("Email already in use");
        }
        UserModel userModel = UserModel.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER)
                .build();
        System.out.println(userModel);
        userRepository.save(userModel);
        return userModel;

    }

    public UserModel updateUser(UserModel userModel, String email){
        UserModel existingUser = userRepository.findByEmail(email);

        if(userModel.getFirstName()!=null){
            existingUser.setFirstName(userModel.getFirstName());
        }
        if (userModel.getLastName()!=null){
            existingUser.setLastName(userModel.getLastName());
        }
        if (userModel.getMobile()!=null){
            existingUser.setMobile(userModel.getMobile());
        }
        userRepository.save(existingUser);
        return existingUser;
    }

    public UserModel deleteUser(String email){
       UserModel user = userRepository.findByEmail(email);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = authentication.getName();

        if(!email.equals(authenticatedEmail)){
            throw new IllegalArgumentException("Not authorized to delete this account.");
        }

       List<PostModel> userPosts = postRepository.findByUserUserId(user.getUserId());
       postRepository.deleteAll(userPosts);
       userRepository.delete(user);
       return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(userRepository.findByEmail(email)!=null) {
            return userRepository.findByEmail(email);
        } else {
            throw new UsernameNotFoundException("User not found");
        }

//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
    }
}
