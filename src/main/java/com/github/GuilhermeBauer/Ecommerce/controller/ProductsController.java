package com.github.GuilhermeBauer.Ecommerce.controller;


import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabasesContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product/v1")
public class ProductsController implements ControllerDatabasesContract<ProductVO> {

    @Autowired
    private ProductServices productServices;

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ProductVO> create(@RequestBody ProductVO productVO) throws Exception {
        ProductVO createdProduct = productServices.create(productVO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<EntityModel<ProductVO>>> findAll(
            @PageableDefault(page = 0, size = 20, sort = "name") Pageable pageable) {
        Page<EntityModel<ProductVO>> products = productServices.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @Override
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductVO> update(@RequestBody ProductVO productVO) throws Exception {
        ProductVO updatedProduct = productServices.update(productVO);
        return ResponseEntity.ok(updatedProduct);
    }

    @Override
    @GetMapping(value = "/{uuid}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductVO> findById(@PathVariable(value = "uuid") UUID uuid) throws Exception {
        ProductVO productId = productServices.findById(uuid);
        return ResponseEntity.ok(productId);
    }

    @Override
    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(@PathVariable(value="uuid")UUID uuid) throws Exception {

        productServices.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
