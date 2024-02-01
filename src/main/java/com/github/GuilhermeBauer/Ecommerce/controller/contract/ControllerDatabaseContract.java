package com.github.GuilhermeBauer.Ecommerce.controller.contract;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ControllerDatabaseContract <T>{

    ResponseEntity<T> create(T t);

//    ResponseEntity<Page<T>> findAll(Pageable pageable);

    ResponseEntity<T> update(T t);

    ResponseEntity<T> findById(UUID uuid) throws Exception;

    ResponseEntity<?> delete(UUID uuid) throws Exception;
}
