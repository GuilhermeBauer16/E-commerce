package com.github.GuilhermeBauer.Ecommerce.controller;

import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabasesContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.services.CartItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cartItems")
public class CartItemController implements ControllerDatabasesContract<CartItemVO> {

    @Autowired
    private CartItemServices cartItemServices;

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<CartItemVO> create(@RequestBody CartItemVO cartItemVO) {
        CartItemVO cartItem = cartItemServices.create(cartItemVO);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Page<CartItemVO>> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<CartItemVO> update(@RequestBody CartItemVO cartItemVO) {
        CartItemVO updatedCartItem = cartItemServices.update(cartItemVO);
        return ResponseEntity.ok(updatedCartItem);
    }

    @Override

    public ResponseEntity<CartItemVO> findById(UUID uuid) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(UUID uuid) throws Exception {
        return null;
    }
}
