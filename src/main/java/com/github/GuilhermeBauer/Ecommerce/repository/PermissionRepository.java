package com.github.GuilhermeBauer.Ecommerce.repository;

import com.github.GuilhermeBauer.Ecommerce.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<PermissionModel, UUID> {

    PermissionModel findByDescription(String description);
}
