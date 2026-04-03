package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.producer")
    List<Product> findAllWithProducer();

    List<Product> findByProducerId(Long producerId);
}