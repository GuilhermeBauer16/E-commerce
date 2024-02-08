package com.github.GuilhermeBauer.Ecommerce.controller;

import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabasesContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.services.CartItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CartItemVO>> findAll(
            @PageableDefault(size = 20,page = 0, sort = "quantity") Pageable pageable) {
        Page<CartItemVO> allCartItems = cartItemServices.findAll(pageable);
        return ResponseEntity.ok(allCartItems);
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
    @GetMapping(value = "/{uuid}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItemVO> findById(@PathVariable(value = "uuid") UUID uuid) throws Exception {
        CartItemVO cartItemId = cartItemServices.findById(uuid);
        return ResponseEntity.ok(cartItemId);
    }

    @Override
    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(@PathVariable(value = "uuid") UUID uuid) throws Exception {
        cartItemServices.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
