package com.github.GuilhermeBauer.Ecommerce.repository;

import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<PermissionModel, UUID> {

    @Query("SELECT p FROM PermissionModel p WHERE p.description =:description")
    PermissionModel findByDescription(@Param("description")String description);
}
