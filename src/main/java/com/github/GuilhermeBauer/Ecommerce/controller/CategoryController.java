package com.github.GuilhermeBauer.Ecommerce.controller;

import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabasesContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CategoryVO;
import com.github.GuilhermeBauer.Ecommerce.services.CategoryServices;
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
@RequestMapping("/category")
public class CategoryController implements ControllerDatabasesContract<CategoryVO> {

    @Autowired
    private CategoryServices categoryServices;
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<CategoryVO> create(@RequestBody CategoryVO categoryVO) throws Exception {
        CategoryVO createdCategory = categoryServices.create(categoryVO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<EntityModel<CategoryVO>>> findAll(
            @PageableDefault(size = 10,page = 0, sort = "name") Pageable pageable) {
        Page<EntityModel<CategoryVO>> allCategory = categoryServices.findAll(pageable);

        return ResponseEntity.ok(allCategory);
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<CategoryVO> update(@RequestBody CategoryVO categoryVO) throws Exception {
        CategoryVO updatedCategory = categoryServices.update(categoryVO);
        return ResponseEntity.ok(updatedCategory);
    }

    @Override
    @GetMapping(value = "/{uuid}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryVO> findById(
            @PathVariable(value = "uuid") UUID uuid) throws Exception {
        CategoryVO categoryId = categoryServices.findById(uuid);
        return ResponseEntity.ok(categoryId);
    }

    @Override
    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(
            @PathVariable(value = "uuid") UUID uuid) throws Exception {
        categoryServices.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
