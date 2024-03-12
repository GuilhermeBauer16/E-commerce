package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.controller.PermissionController;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.user.PermissionVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import com.github.GuilhermeBauer.Ecommerce.repository.PermissionRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PermissionServices implements ServicesDatabaseContract<PermissionVO> {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public PermissionVO create(PermissionVO permissionVO) throws Exception {

        PermissionModel entity = Mapper.parseObject(permissionVO, PermissionModel.class);
        System.out.println(entity.getDescription());
        return Mapper.parseObject(permissionRepository.save(entity), PermissionVO.class);
    }

    @Override
    public Page<EntityModel<PermissionVO>> findAll(Pageable pageable) throws Exception {
        Page<PermissionModel> permissions = permissionRepository.findAll(pageable);
        Mapper.parseObject(permissions.getContent(), PermissionModel.class);

        return null;
    }

    @Override
    public PermissionVO update(PermissionVO permissionVO) throws Exception {
        return null;
    }


    @Override
    public PermissionVO findById(UUID uuid) throws Exception {
        PermissionModel entity = permissionRepository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound("No record found by that ID!"));


        return Mapper.parseObject(entity, PermissionVO.class);

    }

    @Override
    public void delete(UUID uuid) throws Exception {

    }
}
