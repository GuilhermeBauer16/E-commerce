package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.controller.ProductsController;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.CategoryNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotAvailable;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CategoryRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.ProductRepository;
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
public class ProductServices implements ServicesDatabaseContract<ProductVO> {
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "No product was found for that ID!";
    private static final String PRODUCT_NOT_AVAILABLE_MESSAGE = "That product is not available!";
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "No category was found for that Product!";
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public ProductVO create(ProductVO productVO) throws Exception {
        if (productVO == null) {
            throw new RequiredObjectsNullException();
        }
        CategoryModel categoryName = categoryRepository.findByName(productVO.getCategoryModel().getName().toUpperCase());
        productVO.setCategoryModel(categoryName);
        ProductModel entity = Mapper.parseObject(productVO, ProductModel.class);
        isAvailable(entity);
        ProductVO vo = Mapper.parseObject(repository.save(entity), ProductVO.class);
        vo.add(linkTo(methodOn(ProductsController.class).findById(productVO.getId())).withSelfRel());
        return vo;
    }

    @Override
    public Page<EntityModel<ProductVO>> findAll(Pageable pageable) throws Exception {
        Page<ProductModel> products = repository.findAll(pageable);
        List<ProductVO> productVos = Mapper.parseObjectList(products.getContent(), ProductVO.class);

        List<EntityModel<ProductVO>> productEntities = new ArrayList<>();
        for (ProductVO productVo : productVos) {

            Link selfLink = linkTo(
                    methodOn(ProductsController.class)
                            .findById(productVo.getId()))
                    .withSelfRel();

            EntityModel<ProductVO> apply = EntityModel.of(productVo, selfLink);
            productEntities.add(apply);
        }

        return new PageImpl<>(productEntities, pageable, products.getTotalElements());
    }

    @Override
    public ProductVO update(ProductVO productVO) throws Exception {

        if (productVO == null) {
            throw new RequiredObjectsNullException();
        }

        if (productVO.getCategoryModel() == null) {
            throw new CategoryNotFound(CATEGORY_NOT_FOUND_MESSAGE);
        }

        CategoryModel categoryName = categoryRepository.findByName(productVO.getCategoryModel().getName().toUpperCase());
        productVO.setCategoryModel(categoryName);

        ProductModel productId = repository.findById(productVO.getId())
                .orElseThrow(() -> new ProductNotFound(PRODUCT_NOT_FOUND_MESSAGE));

        ProductModel updatedProduct = CheckIfNotNull.updateIfNotNull(productId, productVO);

        ProductModel saveEntity = repository.save(updatedProduct);

        ProductVO vo = Mapper.parseObject(saveEntity, ProductVO.class);
        vo.add(linkTo(methodOn(ProductsController.class).findById(productVO.getId())).withSelfRel());
        return vo;
    }


    @Override
    public ProductVO findById(UUID uuid) throws Exception {

        ProductModel productId = repository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound(PRODUCT_NOT_FOUND_MESSAGE));

        ProductVO vo = Mapper.parseObject(productId, ProductVO.class);
        vo.add(linkTo(methodOn(ProductsController.class).findById(uuid)).withSelfRel());
        return vo;
    }

    @Override
    public void delete(UUID uuid) throws Exception {
        ProductModel productId = repository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound(PRODUCT_NOT_FOUND_MESSAGE));
        repository.delete(productId);


    }

    public void isAvailable(ProductModel productModel) {
        if (productModel.getQuantity() <= 0) {
            productModel.setAvailable(false);
            throw new ProductNotAvailable(PRODUCT_NOT_AVAILABLE_MESSAGE);
        }
        productModel.setAvailable(true);
    }

}
