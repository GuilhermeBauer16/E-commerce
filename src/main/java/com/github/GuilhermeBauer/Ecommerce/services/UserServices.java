package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.UserVO;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import com.github.GuilhermeBauer.Ecommerce.model.UserModel;
import com.github.GuilhermeBauer.Ecommerce.repository.PermissionRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.UserRepository;
import com.github.GuilhermeBauer.Ecommerce.util.CheckIfNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PermissionRepository permissionRepository;


    public UserServices(UserRepository repository) {
        this.userRepository = repository;
    }

    public UserVO create(UserVO userVO) throws Exception {

        if (userVO == null || userVO.getPermission() == null || userVO.getPermission().getDescription() == null) {
            throw new IllegalArgumentException("UserVO or its permission is null");
        }
        userVO.setPassword(encoder.encode(userVO.getPassword()));
        PermissionModel permission = permissionRepository.findById(userVO.getPermission().getId()).orElseThrow(() -> new RuntimeException());
//        PermissionModel permission = permissionRepository.findByDescription(userVO.getPermission().getDescription().toUpperCase());
        System.out.println(permission);
        userVO.setPermission(permission);
        UserModel entity = Mapper.parseObject(userVO, UserModel.class);
        UserVO vo = Mapper.parseObject(userRepository.save(entity), UserVO.class);
//        vo.add(linkTo(methodOn(UserController.class).findById(userVO.getId())).withSelfRel());
        return vo;
    }


    public UserVO update(UserVO userVO) throws Exception {

        UserModel username = userRepository.findByUsername(userVO.getUsername());
        if( username == null){
            throw new UsernameNotFoundException("Not records founds for that username!");
        }
        UserModel updatedUser = CheckIfNotNull.updateIfNotNull(username, userVO);
        UserVO vo = Mapper.parseObject(userRepository.save(updatedUser), UserVO.class);
//        vo.add(linkTo(methodOn(UserController.class).findById(userVO.getId())).withSelfRel());
        return vo;
    }


    public UserVO findByName(String username) throws Exception {

        if (username.isEmpty()) {
            throw new RuntimeException("The username must not be null");
        }
        var entity = userRepository.findByUsername(username);
        return Mapper.parseObject(entity, UserVO.class);
    }

    public void delete(String username) throws Exception {
        UserModel user = userRepository.findByUsername(username);
        userRepository.delete(user);


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            return user;

        } else {

            throw new UsernameNotFoundException("Username: " + username + "not found!");


        }
    }
}
