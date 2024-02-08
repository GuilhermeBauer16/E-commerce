package com.github.GuilhermeBauer.Ecommerce.repository;

import com.github.GuilhermeBauer.Ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
