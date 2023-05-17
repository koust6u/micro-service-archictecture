package com.example.catalogservice.repository;

import com.example.catalogservice.jpa.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<CatalogEntity,Long> {
    CatalogEntity findByProductId(String productId);
}

