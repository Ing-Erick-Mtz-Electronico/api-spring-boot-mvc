package com.products.api.repository.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.products.api.repository.entity.ProductEntity;

public interface IPruductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
        SELECT p FROM ProductEntity p
        WHERE p.isActive = true
        AND (:name IS NULL OR p.name ILIKE CONCAT('%', :name, '%'))
        AND (:category IS NULL OR EXISTS (SELECT c FROM p.categories c WHERE c.name = :category))
    """)
    Page<ProductEntity> findPageWithFilters(
        String name,
        String category,
        Pageable pageable);

    @Query("""
        SELECT p FROM ProductEntity p
        WHERE p.isActive = true
        AND p.id = :id
    """)
    Optional<ProductEntity> findById(Long id);
}
