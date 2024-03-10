package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.UserVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.security.AccountCredentialsVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.security.TokenVO;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import com.github.GuilhermeBauer.Ecommerce.model.UserModel;
import com.github.GuilhermeBauer.Ecommerce.repository.PermissionRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.UserRepository;
import com.github.GuilhermeBauer.Ecommerce.security.jwt.JwtTokenProvider;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    public UserServices(UserRepository repository) {
        this.repository = repository;
    }

    public UserVO create(UserVO userVO) throws Exception {
        encodePassword(userVO);
        UserModel entity = Mapper.parseObject(userVO, UserModel.class);
        return Mapper.parseObject(repository.save(entity), UserVO.class);
    }


    public Page<EntityModel<UserVO>> findAll(Pageable pageable) throws Exception {
        return null;
    }


    public UserVO update(UserVO userVO) throws Exception {
        return null;
    }


    public UserVO findByName(String username) throws Exception {

        if (username.isEmpty()) {
            throw new RuntimeException("The username must not be null");
        }
        var entity = repository.findByUsername(username);
        return Mapper.parseObject(entity, UserVO.class);
    }

    public void delete(UUID uuid) throws Exception {

    }

    private UserVO encodePassword(UserVO userVO) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 18500,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(passwordEncoder);
        String encodedPassword = passwordEncoder.encode(userVO.getPassword());
//        if (encodedPassword.startsWith("{pbkdf2}")) {
//            encodedPassword = encodedPassword.substring("{pbkdf2}".length());
//        }
        userVO.setPassword(encodedPassword);

        return userVO;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        if(user != null) {
            return user;

        }else {

            throw new UsernameNotFoundException("Username: " + username + "not found!");


        }
    }
}
