package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.ProductVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.CartItem;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CartItemRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.ProductRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class CartItemServices implements ServicesDatabaseContract<CartItemVO> {
    @Autowired
    private CartItemRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItemVO create(CartItemVO cartItemVO) {

        CartItem entity = Mapper.parseObject(cartItemVO, CartItem.class);
        ProductModel productModel = productRepository.findById(cartItemVO.getProduct().getId())
                .orElseThrow(() -> new ProductNotFound("No product found for that ID!"));

        entity.setProduct(productModel);

        return Mapper.parseObject(repository.save(entity), CartItemVO.class);

    }

    @Override
    public Page<CartItemVO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CartItemVO update(CartItemVO cartItemVO) {

        CartItem entity = repository.findById(cartItemVO.getKey())
                .orElseThrow(() -> new RuntimeException("No records founds for that ID!"));

        if(cartItemVO.getQuantity() != null && cartItemVO.getQuantity() > 1){
            entity.setQuantity(cartItemVO.getQuantity());
        }


        return Mapper.parseObject(repository.save(entity),CartItemVO.class);
    }

    @Override
    public CartItemVO findById(UUID uuid) throws Exception {
        return null;
    }

    @Override
    public void delete(UUID uuid) throws Exception {

    }
}
