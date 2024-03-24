package com.github.GuilhermeBauer.Ecommerce.controller.security;

import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabasesContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.services.PermissionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/permission")
public class PermissionController implements ControllerDatabasesContract<PermissionVO> {

    @Autowired
    private PermissionServices permissionServices;

    @Override
    @PostMapping
    public ResponseEntity<PermissionVO> create(@RequestBody PermissionVO permissionVO) throws Exception {
        PermissionVO permissionCreated = permissionServices.create(permissionVO);
        return new ResponseEntity<>(permissionCreated, HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<EntityModel<PermissionVO>>> findAll(
            @PageableDefault(page = 0, size = 20, sort = "description") Pageable pageable) throws Exception {
        Page<EntityModel<PermissionVO>> allPermissions = permissionServices.findAll(pageable);
        return ResponseEntity.ok(allPermissions);
    }

    @Override
    @PutMapping
    public ResponseEntity<PermissionVO> update(@RequestBody PermissionVO permissionVO) throws Exception {
        PermissionVO updatedPermission = permissionServices.update(permissionVO);
        return ResponseEntity.ok(updatedPermission);
    }

    @Override
    @GetMapping(value = "/{uuid}")
    public ResponseEntity<PermissionVO> findById(@PathVariable(value = "uuid") UUID uuid) throws Exception {

        PermissionVO permission = permissionServices.findById(uuid);
        return ResponseEntity.ok(permission);
    }

    @Override
    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(@PathVariable(value = "uuid") UUID uuid) throws Exception {
        permissionServices.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
