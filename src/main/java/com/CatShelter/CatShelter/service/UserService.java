package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.LoginRequestDto;
import com.CatShelter.CatShelter.dto.RegisterRequestDto;
import com.CatShelter.CatShelter.dto.UserDto;
import com.CatShelter.CatShelter.mapper.UserMapper;
import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.model.UserRole;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PostRepository postRepository;
    private final UserMapper userMapper;

    public LoginRequestDto loginUser(LoginRequestDto loginRequest,
                                     AuthenticationManager authenticationManager){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return loginRequest;
        } catch (NullPointerException | AuthenticationException e){
            throw new IllegalArgumentException("Invalid Credentials");
        }
    }

    public RegisterRequestDto addUser(RegisterRequestDto registerRequest){

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new IllegalStateException("Username taken");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalStateException("Email already in use");
        }
        UserModel userModel = UserModel.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .userRole(UserRole.USER)
                .build();
        System.out.println(userModel);
        userRepository.save(userModel);
        return registerRequest;

    }

    public UserDto fetchUserInformation(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        UserModel user = userRepository.findByEmail(authentication.getName());
        return userMapper.convertUserToDto(user);
    }

    public boolean isUserSessionActive(){
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();

    }

    public UserDto updateUser(UserDto user){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel existingUser = userRepository.findByEmail(email);

        if(user.getFirstName()!=null){
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName()!=null){
            existingUser.setLastName(user.getLastName());
        }
        if (user.getMobile()!=null){
            existingUser.setMobile(user.getMobile());
        }
        userRepository.save(existingUser);
        return userMapper.convertUserToDto(existingUser);
    }

    public UserDto deleteUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

       UserModel user = userRepository.findByEmail(email);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = authentication.getName();

        if(!email.equals(authenticatedEmail)){
            throw new IllegalArgumentException("Not authorized to delete this account.");
        }

       List<PostModel> userPosts = postRepository.findByUserUserId(user.getUserId());
       postRepository.deleteAll(userPosts);
       userRepository.delete(user);
       return userMapper.convertUserToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(userRepository.findByEmail(email)!=null) {
            return userRepository.findByEmail(email);
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
