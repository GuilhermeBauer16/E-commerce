package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.security.AccountCredentialsVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.security.TokenVO;
import com.github.GuilhermeBauer.Ecommerce.model.UserModel;
import com.github.GuilhermeBauer.Ecommerce.repository.UserRepository;
import com.github.GuilhermeBauer.Ecommerce.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    private static final String USERNAME_NOT_FOUND_MESSAGE = "Not records founds for that username!";
    private static final String BAD_CREDENTIALS_MESSAGE = "Invalid username/password supplied";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(UserModel data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            var user = userRepository.findByUsername(username);
            passwordEncoder.matches(password, user.getPassword());
            var tokenResponse = new TokenVO();
            checkIfUserIsnull(user.getUsername());
            tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e) {
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {


        var user = userRepository.findByUsername(username);
        var tokenResponse = new TokenVO();
        if (user != null) {
            tokenResponse = jwtTokenProvider.createRefreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE);
        }
        return ResponseEntity.ok(tokenResponse);
    }

    public void checkIfUserIsnull(String username){
        UserModel user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE);
        }


    }
}