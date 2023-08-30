package com.CatShelter.CatShelter.service;

import com.CatShelter.CatShelter.dto.*;
import com.CatShelter.CatShelter.exceptions.EmailTakenException;
import com.CatShelter.CatShelter.exceptions.UsernameTakenException;
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
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

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


    public ResponseEntity<String> loginUser(LoginRequestDto loginRequest,
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
            return ResponseEntity.ok("Logged in as " + authentication.getName());
        } catch (NullPointerException | AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed:\n" + e.getMessage());
        }
    }

    public ResponseEntity<String> registerUser(RegisterRequestDto registerRequest){
    try {

        if (userRepository.findByUsername(registerRequest.getUsername()) != null &&
                !Objects.equals(registerRequest.getUsername(), "anonymousUser")) {
            throw new UsernameTakenException("Username taken");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailTakenException("Email already in use");
        }
        if (!isValidEmail(registerRequest.getEmail()))
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide valid email address");

        UserModel userModel = UserModel.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .userRole(UserRole.USER)
                .build();
        userRepository.save(userModel);
        return ResponseEntity.ok("Registered successfully as " + registerRequest.getUsername());
    } catch (UsernameTakenException | EmailTakenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Registration failed: \n" + e.getMessage());
    }

    }

    public UserDto fetchUserInformation(Long userId){
    try {
        UserModel user = userRepository.findByUserId(userId);
        return userMapper.convertUserToDto(user);
    } catch (NullPointerException e) {
        return null;
    }
    }

    public List<UserDto> findAllUsers(){
        List<UserModel> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::convertUserToDto)
                .collect(Collectors.toList());
    }

    public UserSessionDto isUserSessionActive(){
        try {
            UserModel user = userRepository.findByUserId(authenticationService.getCurrentUserId());
            return userSessionMapper.convertUserSessionToDto(user);
        } catch (NullPointerException e){
            return null;
        }

    }

    public ResponseEntity<String> updateUserInformation(UpdateUserDetailsDto userDetailsDto){
        try {
            UserModel existingUser = userRepository.findByUserId(authenticationService.getCurrentUserId());

            boolean usernameExists = userRepository.existsByUsername(userDetailsDto.getUsername());
            if (!usernameExists) {
                existingUser.setUsername(Optional.ofNullable(userDetailsDto.getUsername()).orElse(existingUser.getUsername()));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Username already taken!");
            }
            existingUser.setFirstName(Optional.ofNullable(userDetailsDto.getFirstName()).orElse(existingUser.getFirstName()));
            existingUser.setLastName(Optional.ofNullable(userDetailsDto.getLastName()).orElse(existingUser.getLastName()));
            existingUser.setMobile(Optional.ofNullable(userDetailsDto.getMobile()).orElse(existingUser.getMobile()));

            userRepository.save(existingUser);
            return ResponseEntity.ok("Details updated successfully");
        } catch (NullPointerException e){
            return null;
        }
    }

    public ResponseEntity<String> updatePassword(String oldPassword, String newPassword){
    try {
        UserModel existingUser = userRepository.findByUserId(authenticationService.getCurrentUserId());

        if (bCryptPasswordEncoder.matches(oldPassword, existingUser.getPassword())) {

            existingUser.setPassword(bCryptPasswordEncoder.encode(newPassword));

            userRepository.save(existingUser);

            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
        }
    } catch (NullPointerException e){
        return null;
    }

    }

    public ResponseEntity<String> deleteUser(String password,HttpServletRequest request){
        try {
        Long userId = authenticationService.getCurrentUserId();

        UserModel user = userRepository.findByUserId(userId);

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {

            List<PostModel> userPosts = postRepository.findByUserUserId(userId);
            postRepository.deleteAll(userPosts);
            userRepository.delete(user);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return ResponseEntity.ok("User " + user.getUsername() + "deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password!");
        }
    } catch (NullPointerException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not logged in");
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

    public boolean isValidEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }


}
