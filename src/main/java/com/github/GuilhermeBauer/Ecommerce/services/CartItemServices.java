package com.github.GuilhermeBauer.Ecommerce.services;

import com.github.GuilhermeBauer.Ecommerce.controller.CartItemController;
import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.CartItemVO;
import com.github.GuilhermeBauer.Ecommerce.exceptions.CartItemNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.InsufficientQuantityAvailable;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.RequiredObjectsNullException;
import com.github.GuilhermeBauer.Ecommerce.mapper.Mapper;
import com.github.GuilhermeBauer.Ecommerce.model.CartItemModel;
import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import com.github.GuilhermeBauer.Ecommerce.repository.CartItemRepository;
import com.github.GuilhermeBauer.Ecommerce.repository.ProductRepository;
import com.github.GuilhermeBauer.Ecommerce.services.contract.ServicesDatabaseContract;
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
public class CartItemServices implements ServicesDatabaseContract<CartItemVO> {

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "No product was found for that ID!";
    private static final String CART_ITEM_NOT_FOUND_MESSAGE = "No cart item was found for that ID!";
    private static final String INSUFFICIENT_AVAILABLE_QUANTITY_MESSAGE = "Don't have enough quantity available";
    private static final String QUANTITY_MESSAGE = "Please type a quantity plus to 0!";

    @Autowired
    private CartItemRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItemVO create(CartItemVO cartItemVO) throws Exception {

        if (cartItemVO == null) {
            throw new RequiredObjectsNullException();
        }

        cartItemHadQuantityAvailable(cartItemVO);

        CartItemModel entity = Mapper.parseObject(cartItemVO, CartItemModel.class);
        ProductModel productModel = productRepository.findById(cartItemVO.getProduct().getId())
                .orElseThrow(() -> new ProductNotFound(PRODUCT_NOT_FOUND_MESSAGE));


        entity.setProduct(productModel);

        checkIfQuantityIsLess(cartItemVO, entity);

        CartItemVO vo = Mapper.parseObject(repository.save(entity), CartItemVO.class);
        vo.add(linkTo(methodOn(CartItemController.class).findById(cartItemVO.getId())).withSelfRel());
        return vo;


    }


    @Override

    public Page<EntityModel<CartItemVO>> findAll(Pageable pageable) throws Exception {
        Page<CartItemModel> allCartItems = repository.findAll(pageable);
        List<CartItemVO> cartItems = Mapper.parseObjectList(allCartItems.getContent(), CartItemVO.class);
        List<EntityModel<CartItemVO>> cartItemEntities = new ArrayList<>();
        for (CartItemVO cartItemVO : cartItems) {
            Link selfLink = linkTo(
                    methodOn(CartItemController.class)
                            .findById(cartItemVO.getId())).withSelfRel();
            EntityModel<CartItemVO> apply = EntityModel.of(cartItemVO, selfLink);
            cartItemEntities.add(apply);
        }


        return new PageImpl<>(cartItemEntities, pageable, allCartItems.getTotalElements());
    }

    @Override
    public CartItemVO update(CartItemVO cartItemVO) throws Exception {
        if (cartItemVO == null) {
            throw new RequiredObjectsNullException();
        }
        CartItemModel entity = repository.findById(cartItemVO.getId())
                .orElseThrow(() -> new CartItemNotFound(CART_ITEM_NOT_FOUND_MESSAGE));

        checkIfQuantityIsLess(cartItemVO, entity);

        if (cartItemVO.getQuantity() > 1) {
            entity.setQuantity(cartItemVO.getQuantity());
        }
        CartItemVO vo = Mapper.parseObject(repository.save(entity), CartItemVO.class);
        vo.add(linkTo(methodOn(CartItemController.class).findById(cartItemVO.getId())).withSelfRel());
        return vo;
    }

    @Override
    public CartItemVO findById(UUID uuid) throws Exception {
        CartItemModel entity = repository.findById(uuid)
                .orElseThrow(() -> new CartItemNotFound(CART_ITEM_NOT_FOUND_MESSAGE));
        CartItemVO vo = Mapper.parseObject(entity, CartItemVO.class);
        Link selfLink = linkTo(methodOn(CartItemController.class).findById(uuid)).withSelfRel();
        vo.add(selfLink);
        return vo;
    }

    @Override
    public void delete(UUID uuid) throws Exception {

        CartItemModel entity = repository.findById(uuid)
                .orElseThrow(() -> new ProductNotFound(PRODUCT_NOT_FOUND_MESSAGE));

        repository.delete(entity);

    }

    public void cartItemHadQuantityAvailable(CartItemVO cartItemVO) {
        if (cartItemVO.getQuantity() <= 0) {
            throw new InsufficientQuantityAvailable(QUANTITY_MESSAGE);
        }
    }

    public void checkIfQuantityIsLess(CartItemVO cartItemVO, CartItemModel cartItem) {
        if (cartItemVO == null || cartItemVO.getQuantity() == null
                || cartItem == null || cartItem.getProduct() == null
                || cartItem.getProduct().getQuantity() == null) {
            throw new RequiredObjectsNullException();
        }
        if (cartItemVO.getQuantity() > cartItem.getProduct().getQuantity()) {
            throw new InsufficientQuantityAvailable(INSUFFICIENT_AVAILABLE_QUANTITY_MESSAGE);
        }
    }

}
