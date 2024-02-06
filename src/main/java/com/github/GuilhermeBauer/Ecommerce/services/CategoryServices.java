package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CategoryVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.CategoryNotFound;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CategoryRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import com.github.GuilhermeBauer.Ecommerce.util.CheckIfNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServices implements ServicesDatabaseContract<CategoryVO> {
    @Autowired
    private CategoryRepository repository;

    @Override
    public CategoryVO create(CategoryVO categoryVO) {
        categoryVO.setName(categoryVO.getName().toUpperCase());
        CategoryModel entity = Mapper.parseObject(categoryVO, CategoryModel.class);
        return Mapper.parseObject(repository.save(entity), CategoryVO.class);
    }

    @Override
    public Page<CategoryVO> findAll(Pageable pageable) {
        Page<CategoryModel> allCategory = repository.findAll(pageable);
        List<CategoryVO> categoryVOS = Mapper.parseObjectList(allCategory.getContent(), CategoryVO.class);
        return new PageImpl<>(categoryVOS, pageable, allCategory.getTotalElements());

    }

    @Override
    public CategoryVO update(CategoryVO categoryVO) {
        CategoryModel entity = repository.findById(categoryVO.getId())
                .orElseThrow(() -> new CategoryNotFound("No category was found for that ID!"));

        CategoryModel updatedCategory = CheckIfNotNull.updateIfNotNull(entity, categoryVO);
        return Mapper.parseObject(repository.save(updatedCategory), CategoryVO.class);
    }

    @Override
    public CategoryVO findById(UUID uuid) throws Exception {

        CategoryModel entity = repository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFound("No category was found for that ID!"));
        return Mapper.parseObject(entity, CategoryVO.class);
    }

    @Override
    public void delete(UUID uuid) throws Exception {
        CategoryModel entity = repository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFound("No category was found for that ID!"));
        repository.delete(entity);


    }
}
