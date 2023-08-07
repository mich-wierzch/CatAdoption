package com.CatShelter.CatShelter.security.configuration;

import com.CatShelter.CatShelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.CatShelter.CatShelter.security.configuration.EndpointWhitelist.authWhitelist;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    private static final String[] authWhitelist = EndpointWhitelist.authWhitelist;
    AuthenticationManager authenticationManager;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        authenticationManager = authenticationManagerBuilder.build();

        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(authWhitelist).hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll())
                .authenticationManager(authenticationManager)
                .logout();
    //TODO: EDIT SECURITY IMPLEMENTATION


        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Set the allowed origin(s) here. "*" allows all origins.
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedHeader("*"); // Allow all headers.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}
