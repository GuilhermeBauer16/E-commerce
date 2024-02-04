package com.github.GuilhermeBauer.Ecommerce.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private ProductModel product;
    private int quantity;

    public CartItem() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && Objects.equals(id, cartItem.id) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
