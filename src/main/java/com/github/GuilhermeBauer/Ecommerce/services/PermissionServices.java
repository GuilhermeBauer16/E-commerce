package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.controller.ProductsController;
import com.github.GuilhermeBauer.Ecommerce.controller.security.PermissionController;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.PermissionNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import com.github.GuilhermeBauer.Ecommerce.repository.PermissionRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import com.github.GuilhermeBauer.Ecommerce.util.CheckIfNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PermissionServices implements ServicesDatabaseContract<PermissionVO> {

    private static final String PERMISSION_NOT_FOUND_MESSAGE = "No record found by that ID!";
    @Autowired
    private PermissionRepository permissionRepository;

    @Override

    public PermissionVO create(PermissionVO permissionVO) throws Exception {

        if (permissionVO == null) {
            throw new RequiredObjectsNullException();
        }
        String upperCasePermission = permissionVO.getDescription().toUpperCase();
        permissionVO.setDescription(upperCasePermission);
        PermissionModel entity = Mapper.parseObject(permissionVO, PermissionModel.class);
        PermissionVO vo = Mapper.parseObject(permissionRepository.save(entity), PermissionVO.class);
        vo.add(linkTo(methodOn(PermissionController.class).findById(permissionVO.getId())).withSelfRel());
        return vo;
    }

    @Override
    public Page<EntityModel<PermissionVO>> findAll(Pageable pageable) throws Exception {
        Page<PermissionModel> permissions = permissionRepository.findAll(pageable);
        List<PermissionVO> permissionVos = Mapper.parseObjectList(permissions.getContent(), PermissionVO.class);
        List<EntityModel<PermissionVO>> permissionEntities = new ArrayList<>();

        for (PermissionVO permissionVO : permissionVos) {
            Link link = linkTo(methodOn(ProductsController.class)
                    .findById(permissionVO.getId()))
                    .withSelfRel();
            EntityModel<PermissionVO> permissionVOEntityModel = EntityModel.of(permissionVO, link);
            permissionEntities.add(permissionVOEntityModel);
        }

        return new PageImpl<>(permissionEntities, pageable, permissions.getTotalElements());
    }


    @Override
    public PermissionVO update(PermissionVO permissionVO) throws Exception {

        if (permissionVO == null) {
            throw new RequiredObjectsNullException();
        }
        PermissionModel permissionId = permissionRepository.findById(permissionVO.getId())
                .orElseThrow(() -> new PermissionNotFound(PERMISSION_NOT_FOUND_MESSAGE));

        PermissionModel updatedPermission = CheckIfNotNull.updateIfNotNull(permissionId, permissionVO);
        PermissionVO vo = Mapper.parseObject(permissionRepository.save(updatedPermission), PermissionVO.class);
        vo.add(linkTo(methodOn(PermissionController.class).findById(permissionVO.getId())).withSelfRel());
        return vo;
    }


    @Override
    public PermissionVO findById(UUID uuid) throws Exception {
        PermissionModel entity = permissionRepository.findById(uuid)
                .orElseThrow(() -> new PermissionNotFound(PERMISSION_NOT_FOUND_MESSAGE));


        PermissionVO vo = Mapper.parseObject(entity, PermissionVO.class);
        vo.add(linkTo(methodOn(PermissionController.class).findById(uuid)).withSelfRel());

        return vo;

    }

    @Override
    public void delete(UUID uuid) throws Exception {

        PermissionModel entity = permissionRepository.findById(uuid)
                .orElseThrow(() -> new PermissionNotFound(PERMISSION_NOT_FOUND_MESSAGE));

        permissionRepository.delete(entity);

    }
}
