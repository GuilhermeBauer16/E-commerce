package com.github.GuilhermeBauer.Ecommerce.model;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
@Entity
@Table(name = "cart_item")
public class CartItemModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;
    private Integer quantity;

    public CartItemModel() {
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
        CartItemModel cartItem = (CartItemModel) o;
        return quantity == cartItem.quantity && Objects.equals(id, cartItem.id) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
