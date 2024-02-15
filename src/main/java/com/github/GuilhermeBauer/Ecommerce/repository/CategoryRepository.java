package com.github.GuilhermeBauer.Ecommerce.repository;

import com.github.GuilhermeBauer.Ecommerce.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {

    CategoryModel findByName(String name);
}
