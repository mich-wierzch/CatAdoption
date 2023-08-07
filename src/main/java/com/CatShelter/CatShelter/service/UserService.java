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
import java.util.Optional;

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
        userRepository.save(userModel);
        return registerRequest;

    }

    public UserDto fetchUserInformation(Long userId){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()){
//            return null;
//        }
        UserModel user = userRepository.findByUserId(userId);
        return userMapper.convertUserToDto(user);
    }

    public boolean isUserSessionActive(){
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) return false;

        return authentication.isAuthenticated();

    }

    public UserDto updateUserInformation(UserDto user){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel existingUser = userRepository.findByEmail(email);

        existingUser.setFirstName(Optional.ofNullable(user.getFirstName()).orElse(existingUser.getFirstName()));
        existingUser.setLastName(Optional.ofNullable(user.getLastName()).orElse(existingUser.getLastName()));
        existingUser.setMobile(Optional.ofNullable(user.getMobile()).orElse(existingUser.getMobile()));

        userRepository.save(existingUser);
        return userMapper.convertUserToDto(existingUser);
    }

    public String updatePassword(String password){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel existingUser = userRepository.findByEmail(email);

        existingUser.setPassword(bCryptPasswordEncoder.encode(password));

        return "Password updated";

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
