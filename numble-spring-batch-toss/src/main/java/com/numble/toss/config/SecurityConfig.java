package com.numble.toss.config;


import com.numble.toss.jwt.JwtAuthenticationFilter;
import com.numble.toss.jwt.JwtAuthorizationFilter;
import com.numble.toss.jwt.JwtProvider;
import com.numble.toss.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic((basic) -> basic
                    .disable()
            )
            .csrf((c) -> c
                    .disable()
            )
            .cors((c) -> c
                .disable()
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests((authz) -> authz
                    .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                    .anyRequest().authenticated()
            );

        http.apply(new MyCustomDsl());

        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(new JwtAuthenticationFilter(authenticationManager, userRepository, jwtProvider))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository, jwtProvider))
            ;
        }

        public MyCustomDsl customDsl() {
            return new MyCustomDsl();
        }
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        // createDelegatingPasswordEncoder 기본 옵션이 bcrypt임
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
