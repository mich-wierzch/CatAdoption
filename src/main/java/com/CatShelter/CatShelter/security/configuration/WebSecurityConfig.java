package com.CatShelter.CatShelter.security.configuration;

import com.CatShelter.CatShelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    private static final String[] UNAUTH_WHITELIST = {
            "/",
            "/main",
            "/register",
            "/login",
            "/api/user/register",
            "/api/user/get-username",
            //staticResources
            "/css/**",
            "/images/**",
            "/js/**"
    };
    AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf().disable()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(UNAUTH_WHITELIST).permitAll() //Allows unauthenticated access to URLs
                        .anyRequest().authenticated()) //Require authentication for all others URLs not specified above
                .authenticationManager(authenticationManager)
                .formLogin()
                    .loginPage("/login") //specifies the custom login page
                    .defaultSuccessUrl("/main")//redirect to main page after logged in
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/login?logout") //redirect to login page after user logs out
                    .permitAll();


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


}
