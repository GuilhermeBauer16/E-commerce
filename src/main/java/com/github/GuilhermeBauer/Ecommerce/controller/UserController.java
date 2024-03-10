package com.github.GuilhermeBauer.Ecommerce.controller;

import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabasesContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.UserVO;
import com.github.GuilhermeBauer.Ecommerce.services.AuthServices;
import com.github.GuilhermeBauer.Ecommerce.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController implements ControllerDatabasesContract<UserVO> {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthServices logInServices;

    @Override
    @PostMapping
    public ResponseEntity<UserVO> create(@RequestBody UserVO userVO) {
        try {
            UserVO createdUser = userServices.create(userVO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Page<EntityModel<UserVO>>> findAll(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<UserVO> update(UserVO userVO) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<UserVO> findById(UUID uuid) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(UUID uuid) throws Exception {
        return null;
    }



    @GetMapping(value = "/{username}")
    public ResponseEntity<UserVO> findByName(@PathVariable(value = "username") String username) {
        try {
            UserVO user = userServices.findByName(username);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean checkIfParamsIsNotNull(UserVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
