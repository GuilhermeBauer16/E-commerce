package com.github.GuilhermeBauer.Ecommerce.controller.contract;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

public interface ControllerDatabaseContract <T>{

    ResponseEntity<T> create(T t);

    ResponseEntity<Page<T>> findAll(Pageable pageable);

    ResponseEntity<T> update(T t);

    ResponseEntity<T> findById(UUID uuid) throws Exception;

    ResponseEntity<?> delete(UUID uuid) throws Exception;
}
