package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    boolean existsByName(String name);
}
