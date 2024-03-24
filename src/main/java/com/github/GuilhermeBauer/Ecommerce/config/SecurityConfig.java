package com.github.GuilhermeBauer.Ecommerce.config;

import java.util.HashMap;
import java.util.Map;

import com.github.GuilhermeBauer.Ecommerce.security.jwt.JwtTokenFilter;
import com.github.GuilhermeBauer.Ecommerce.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;


    @Bean
    AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);

        //@formatter:off
        return http
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        "api/user/signIn",
                                        "/auth/LogIn",
                                        "/api/permission/**",
                                        "/auth/refresh/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/users").denyAll()
                )
                .cors(cors -> {})
                .build();
        //@formatter:on
    }
}