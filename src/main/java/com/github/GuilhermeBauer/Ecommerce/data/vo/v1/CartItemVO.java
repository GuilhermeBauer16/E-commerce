package com.github.GuilhermeBauer.Ecommerce.data.vo.v1;

import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CartItemVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID key;
    private ProductModel product;
    private Integer quantity;

    public CartItemVO() {
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
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
        return quantity == cartItemVO.quantity && Objects.equals(key, cartItemVO.key) && Objects.equals(product, cartItemVO.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, product, quantity);
    }



}
