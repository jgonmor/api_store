package com.jgonmor.store.repository;

import com.jgonmor.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.stock < 5")
    List<Product> findLowStockProducts();
}
