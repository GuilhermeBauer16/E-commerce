package com.github.GuilhermeBauer.Ecommerce.controller;

import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabaseContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController implements ControllerDatabaseContract<ProductVO> {


    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductVO> create(ProductVO productVO) {
        return new ResponseEntity<>(productVO,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductVO> update(ProductVO productVO) {
        return null;
    }

    @Override
    public ResponseEntity<ProductVO> findById(UUID uuid) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(UUID uuid) throws Exception {
        return null;
    }
}
