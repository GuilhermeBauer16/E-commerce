package com.github.GuilhermeBauer.Ecommerce.controller;

import com.github.GuilhermeBauer.Ecommerce.controller.contract.ControllerDatabasesContract;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.services.PermissionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(permissionCreated);
    }

    @Override
    public ResponseEntity<Page<EntityModel<PermissionVO>>> findAll(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<PermissionVO> update(PermissionVO permissionVO) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<PermissionVO> findById(UUID uuid) throws Exception {

        PermissionVO permission = permissionServices.findById(uuid);
        return ResponseEntity.ok(permission);
    }

    @Override
    public ResponseEntity<?> delete(UUID uuid) throws Exception {
        return null;
    }
}
