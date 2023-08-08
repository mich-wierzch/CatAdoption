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

import java.security.Principal;
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

    public boolean isUserSessionActive(){
        Object user = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        return user != null && !user.equals("anonymousUser");

    }

    public UserDto updateUserInformation(UserDto user, Principal principal){
        try {

            UserModel existingUser = userRepository.findByUsername(principal.getName());

            existingUser.setFirstName(Optional.ofNullable(user.getFirstName()).orElse(existingUser.getFirstName()));
            existingUser.setLastName(Optional.ofNullable(user.getLastName()).orElse(existingUser.getLastName()));
            existingUser.setMobile(Optional.ofNullable(user.getMobile()).orElse(existingUser.getMobile()));

            userRepository.save(existingUser);
            return userMapper.convertUserToDto(existingUser);
        } catch (NullPointerException e){
            throw new IllegalArgumentException("User not found");
        }
    }

    public String updatePassword(String password, Principal principal){
    try {

        UserModel existingUser = userRepository.findByUsername(principal.getName());

        existingUser.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(existingUser);

        return "Password updated";
    } catch (NullPointerException e){
        throw new IllegalArgumentException("User not found");
    }

    }

    public UserDto deleteUser(String password, Principal principal, HttpServletRequest request){

        UserModel user = userRepository.findByUsername(principal.getName());
    try {
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {

            List<PostModel> userPosts = postRepository.findByUserUserId(user.getUserId());
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
