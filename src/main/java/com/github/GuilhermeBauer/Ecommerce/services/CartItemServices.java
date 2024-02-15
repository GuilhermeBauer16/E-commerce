package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.controller.CartItemController;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.InsufficientQuantityAvailable;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.CartItem;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CartItemRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.ProductRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartItemServices implements ServicesDatabaseContract<CartItemVO> {
    @Autowired
    private CartItemRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItemVO create(CartItemVO cartItemVO) throws Exception {

        CartItem entity = Mapper.parseObject(cartItemVO, CartItem.class);
        ProductModel productModel = productRepository.findById(cartItemVO.getProduct().getId())
                .orElseThrow(() -> new ProductNotFound("No product found for that ID!"));

        if (cartItemVO.getQuantity() == null || cartItemVO.getQuantity() <= 0) {
            System.out.println("inside of IF");
            throw new InsufficientQuantityAvailable("Please type a quantity plus to 0!");
        }

        entity.setProduct(productModel);

        checkIfQuantityIsLess(cartItemVO, entity);

        CartItemVO vo = Mapper.parseObject(repository.save(entity), CartItemVO.class);
        vo.add(linkTo(methodOn(CartItemController.class).findById(cartItemVO.getId())).withSelfRel());
        return vo;


    }

    @Override

    public Page<EntityModel<CartItemVO>> findAll(Pageable pageable) {
        Page<CartItem> allCartItems = repository.findAll(pageable);
        List<CartItemVO> cartItems = Mapper.parseObjectList(allCartItems.getContent(), CartItemVO.class);
        List<EntityModel<CartItemVO>> collect = cartItems.stream()
                .map(cartItemVO -> {
                    try {
                        Link selfLink = linkTo(
                                methodOn(CartItemController.class).findById(cartItemVO.getId())).withSelfRel();
                        return EntityModel.of(cartItemVO, selfLink);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, allCartItems.getTotalElements());
    }

    @Override
    public CartItemVO update(CartItemVO cartItemVO) throws Exception {

        CartItem entity = repository.findById(cartItemVO.getId())
                .orElseThrow(() -> new ProductNotFound("No records founds for that ID!"));

        checkIfQuantityIsLess(cartItemVO, entity);

        if (cartItemVO.getQuantity() != null && cartItemVO.getQuantity() > 1) {
            entity.setQuantity(cartItemVO.getQuantity());
        }
        CartItemVO vo = Mapper.parseObject(repository.save(entity), CartItemVO.class);
        vo.add(linkTo(methodOn(CartItemController.class).findById(cartItemVO.getId())).withSelfRel());
        return vo;
    }

    @Override
    public CartItemVO findById(UUID uuid) throws Exception {
        CartItem entity = repository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound("No records founds for that Id!"));
        CartItemVO vo = Mapper.parseObject(entity, CartItemVO.class);
        vo.add(linkTo(methodOn(CartItemController.class).findById(uuid)).withSelfRel());
        return vo;
    }

    @Override
    public void delete(UUID uuid) throws Exception {

        CartItem entity = repository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound("No records founds for that Id!"));

        repository.delete(entity);

    }

    public void checkIfQuantityIsLess(CartItemVO cartItemVO, CartItem cartItem) {
        if (cartItemVO != null
                && cartItemVO.getQuantity() != null
                && cartItem.getProduct() != null
                && cartItem.getProduct().getQuantity() != null
                && cartItemVO.getQuantity() > cartItem.getProduct().getQuantity()) {
            throw new InsufficientQuantityAvailable("Don't have enough quantity available");
        }
    }
}
