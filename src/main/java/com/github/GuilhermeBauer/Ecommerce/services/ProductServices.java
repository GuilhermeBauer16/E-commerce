package com.github.GuilhermeBauer.Ecommerce.services;
import com.github.GuilhermeBauer.Ecommerce.controller.ProductsController;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotAvailable;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import com.github.GuilhermeBauer.Ecommerce.repository.ProductRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import com.github.GuilhermeBauer.Ecommerce.util.CheckIfNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.UUID;

@Service
public class ProductServices implements ServicesDatabaseContract<ProductVO> {

    @Autowired
    private ProductRepository repository;


    @Override
    public ProductVO create(ProductVO productVO) {
        productVO.getCategoryModel().setName(
                productVO.getCategoryModel().getName().toUpperCase());
        ProductModel entity = Mapper.parseObject(productVO, ProductModel.class);
        isAvailable(entity);
        return Mapper.parseObject(repository.save(entity), ProductVO.class);
    }

    @Override
    public Page<ProductVO> findAll(Pageable pageable) {

        Page<ProductModel> products = repository.findAll(pageable);
        List<ProductVO> productVOs = Mapper.parseObjectList(products.getContent(), ProductVO.class);
        return new PageImpl<>(productVOs, pageable, products.getTotalElements());
    }

    @Override
    public ProductVO update(ProductVO productVO) {
        productVO.getCategoryModel().setName(
                productVO.getCategoryModel().getName().toUpperCase());
        ProductModel productId = repository.findById(productVO.getKey())
                .orElseThrow(() -> new ProductNotFound("No product was found for that ID!"));

        ProductModel updatedProduct = CheckIfNotNull.updateIfNotNull(productId, productVO);

        ProductModel saveEntity = repository.save(updatedProduct);

        return Mapper.parseObject(saveEntity, ProductVO.class);
    }

    @Override
    public ProductVO findById(UUID uuid) throws Exception {

        ProductModel productId = repository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound("No product was found for that ID!"));

        ProductVO vo = Mapper.parseObject(productId, ProductVO.class);
        vo.add(linkTo(methodOn(ProductsController.class).findById(uuid)).withSelfRel());
        return vo;
    }

    @Override
    public void delete(UUID uuid) throws Exception {
        ProductModel productId = repository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound("No product was found for that ID!"));
        repository.delete(productId);


    }

    private ProductModel isAvailable(ProductModel productModel) {
        if (productModel.getQuantity() <= 0) {
            productModel.setAvailable(false);
            throw new ProductNotAvailable("That product is not available!");
        }
        productModel.setAvailable(true);
        return productModel;
    }

}
