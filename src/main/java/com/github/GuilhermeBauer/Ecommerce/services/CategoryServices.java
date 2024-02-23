package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.controller.CategoryController;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CategoryVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.CategoryNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CategoryRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import com.github.GuilhermeBauer.Ecommerce.util.CheckIfNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class CategoryServices implements ServicesDatabaseContract<CategoryVO> {
    @Autowired
    private CategoryRepository repository;


    @Override
    public CategoryVO create(CategoryVO categoryVO) throws Exception {
        if (categoryVO == null){
            throw new RequiredObjectsNullException();
        }
        categoryVO.setName(categoryVO.getName().toUpperCase());
        CategoryModel entity = Mapper.parseObject(categoryVO, CategoryModel.class);
        CategoryVO vo = Mapper.parseObject(repository.save(entity), CategoryVO.class);
        vo.add(linkTo(methodOn(CategoryController.class).findById(categoryVO.getId())).withSelfRel());
        return vo;
    }

    @Override
    public Page<EntityModel<CategoryVO>> findAll(Pageable pageable) throws Exception {
        Page<CategoryModel> allCategory = repository.findAll(pageable);
        List<CategoryVO> categoryVOS = Mapper.parseObjectList(allCategory.getContent(), CategoryVO.class);
        List<EntityModel<CategoryVO>> categoryEntities = new ArrayList<>();
        for (CategoryVO vo : categoryVOS) {

            Link selfLink = linkTo(
                    methodOn(CategoryController.class)
                            .findById(vo.getId())).withSelfRel();


            EntityModel<CategoryVO> apply = EntityModel.of(vo, selfLink);
            categoryEntities.add(apply);
        }
        return new PageImpl<>(categoryEntities, pageable, allCategory.getTotalElements());

    }

    @Override
    public CategoryVO update(CategoryVO categoryVO) throws Exception {
        if (categoryVO == null){
            throw new RequiredObjectsNullException();
        }
        CategoryModel entity = repository.findById(categoryVO.getId())
                .orElseThrow(() -> new CategoryNotFound("No category was found for that ID!"));

        CategoryModel updatedCategory = CheckIfNotNull.updateIfNotNull(entity, categoryVO);
        CategoryVO vo = Mapper.parseObject(repository.save(updatedCategory), CategoryVO.class);
        vo.add(linkTo(methodOn(CategoryController.class).findById(categoryVO.getId())).withSelfRel());
        return vo;
    }

    @Override
    public CategoryVO findById(UUID uuid) throws Exception {

        CategoryModel entity = repository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFound("No category was found for that ID!"));
        CategoryVO vo = Mapper.parseObject(entity, CategoryVO.class);
        vo.add(linkTo(methodOn(CategoryController.class).findById(uuid)).withSelfRel());
        return vo;

    }

    @Override
    public void delete(UUID uuid) throws Exception {
        CategoryModel entity = repository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFound("No category was found for that ID!"));
        repository.delete(entity);


    }
}
