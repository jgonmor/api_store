package com.jgonmor.store.repository;

import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ISellRepository extends JpaRepository<Sell, Long> {
    @Query("select s.product from SellDetail s where s.sell.id = :sellId")
    List<Product> findProductsBySellId(Long sellId);

    @Query("select sum(s.total) from Sell s where s.date >= :start and s.date <= :end")
    Double getTotalFromSellsOnDay(LocalDateTime start, LocalDateTime end);

    @Query("select s from Sell s order by s.total desc limit 1")
    Sell findBiggestSell();
}
