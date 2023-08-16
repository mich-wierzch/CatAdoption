package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.LoginRequestDto;
import com.CatShelter.CatShelter.dto.RegisterRequestDto;
import com.CatShelter.CatShelter.dto.UserDto;
import com.CatShelter.CatShelter.dto.UserSessionDto;
import com.CatShelter.CatShelter.mapper.UserMapper;
import com.CatShelter.CatShelter.mapper.UserSessionMapper;
import com.CatShelter.CatShelter.model.PostModel;
import com.CatShelter.CatShelter.model.UserModel;
import com.CatShelter.CatShelter.model.UserRole;
import com.CatShelter.CatShelter.repository.PostRepository;
import com.CatShelter.CatShelter.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PostRepository postRepository;
    private final UserMapper userMapper;
    private final UserSessionMapper userSessionMapper;
    private final AuthenticationService authenticationService;


    public String loginUser(LoginRequestDto loginRequest,
                            AuthenticationManager authenticationManager, HttpServletRequest request){

        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext );
            System.out.println(authentication.getName());
            return "Logged in as " + authentication.getName();
        } catch (NullPointerException | AuthenticationException e){
            throw new IllegalArgumentException("Invalid Credentials");

        }
    }

    public RegisterRequestDto addUser(RegisterRequestDto registerRequest){

        if (userRepository.findByUsername(registerRequest.getUsername()) != null &&
                !Objects.equals(registerRequest.getUsername(), "anonymousUser")){
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
    try {
        UserModel user = userRepository.findByUserId(userId);
        return userMapper.convertUserToDto(user);
    } catch (NullPointerException e) {
        throw new IllegalArgumentException("User with id " + userId + " not found");
    }
    }

    public UserSessionDto isUserSessionActive(){
        try {
            UserModel user = userRepository.findByUserId(authenticationService.getCurrentUserId());
            return userSessionMapper.convertUserSessionToDto(user);
        } catch (NullPointerException e){
            return null;
        }

    }

    public UserDto updateUserInformation(UserDto user){
        try {

            UserModel existingUser = userRepository.findByUserId(authenticationService.getCurrentUserId());

            boolean usernameExists = userRepository.existsByUsername(user.getUsername());
            if (!usernameExists) {
                existingUser.setUsername(Optional.ofNullable(user.getUsername()).orElse(existingUser.getUsername()));
            } else {
                throw new IllegalArgumentException("Username already taken!");
            }
            existingUser.setFirstName(Optional.ofNullable(user.getFirstName()).orElse(existingUser.getFirstName()));
            existingUser.setLastName(Optional.ofNullable(user.getLastName()).orElse(existingUser.getLastName()));
            existingUser.setMobile(Optional.ofNullable(user.getMobile()).orElse(existingUser.getMobile()));

            userRepository.save(existingUser);
            return userMapper.convertUserToDto(existingUser);
        } catch (NullPointerException e){
            return null;
        }
    }

    public String updatePassword(String oldPassword, String newPassword){
    try {
        UserModel existingUser = userRepository.findByUserId(authenticationService.getCurrentUserId());

        if (bCryptPasswordEncoder.matches(oldPassword, existingUser.getPassword())) {

            existingUser.setPassword(bCryptPasswordEncoder.encode(newPassword));

            userRepository.save(existingUser);

            return "Password updated";
        } else {
            return "Wrong password";
        }
    } catch (NullPointerException e){
        return null;
    }

    }

    public UserDto deleteUser(String password,HttpServletRequest request){

        Long userId = authenticationService.getCurrentUserId();

        UserModel user = userRepository.findByUserId(userId);
    try {
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {

            List<PostModel> userPosts = postRepository.findByUserUserId(userId);
            postRepository.deleteAll(userPosts);
            userRepository.delete(user);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return userMapper.convertUserToDto(user);
        } else {
            throw new IllegalArgumentException("Incorrect password!");
        }
    } catch (NullPointerException e){
        throw new IllegalArgumentException("User is not logged in");
    }
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
