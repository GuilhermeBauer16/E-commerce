package com.github.GuilhermeBauer.Ecommerce.data.vo.v1;

import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CartItemVO extends RepresentationModel<CartItemVO> implements Serializable {

    private UUID id;
    private ProductModel product;
    private Integer quantity;

    public CartItemVO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemVO cartItemVO = (CartItemVO) o;
        return quantity == cartItemVO.quantity && Objects.equals(id, cartItemVO.id) && Objects.equals(product, cartItemVO.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }



}
