package com.github.GuilhermeBauer.Ecommerce.repository;

import com.github.GuilhermeBauer.Ecommerce.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel,UUID> {
}
