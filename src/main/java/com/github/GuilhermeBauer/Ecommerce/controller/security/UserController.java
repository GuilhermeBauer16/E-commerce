package com.github.GuilhermeBauer.Ecommerce.controller.security;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.UserVO;
import com.github.GuilhermeBauer.Ecommerce.services.AuthServices;
import com.github.GuilhermeBauer.Ecommerce.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthServices logInServices;

    @PostMapping("/signIn")
    public ResponseEntity<UserVO> create(@RequestBody UserVO userVO) {
        try {
            UserVO createdUser = userServices.create(userVO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<UserVO> update(@RequestBody UserVO userVO) throws Exception {
        UserVO updatedUser = userServices.update(userVO);
        return ResponseEntity.ok(updatedUser);
    }


    public ResponseEntity<UserVO> findById(UUID uuid) throws Exception {
        return null;
    }

    @DeleteMapping(value = "/{user}")
    public ResponseEntity<?> delete(@PathVariable(value="user")String username) throws Exception {
        userServices.delete(username);
        return ResponseEntity.noContent().build();
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
