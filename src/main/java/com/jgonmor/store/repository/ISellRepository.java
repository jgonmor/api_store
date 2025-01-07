package com.jgonmor.store.repository;

import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISellRepository extends JpaRepository<Sell, Long> {
    @Query("select s.products from Sell s where s.id = :sellId")
    List<Product> findProductsBySellId(Long sellId);
}
