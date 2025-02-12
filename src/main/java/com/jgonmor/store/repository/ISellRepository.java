package com.jgonmor.store.repository;

import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Sell Repository Class
 * Manages sells in the database.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Repository
public interface ISellRepository extends JpaRepository<Sell, Long> {
    /**
     * Finds all the products from a sell.
     *
     * @param sellId The sell.
     * @return A list of products.
     */
    @Query("select s.product from SellDetail s where s.sell.id = :sellId")
    List<Product> findProductsBySellId(Long sellId);

    /**
     * Gets the total of earnings in a period.
     *
     * @param start The beginning of the period.
     * @param end The end of the period.
     * @return The total of earnings in that period.
     */
    @Query("select sum(s.total) from Sell s where s.date >= :start and s.date <= :end")
    Double getTotalFromSellsInAPeriod(LocalDateTime start, LocalDateTime end);

    /**
     * Gets the biggest sell.
     *
     * @return The biggest Sell.
     */
    @Query("select s from Sell s order by s.total desc limit 1")
    Sell findBiggestSell();
}
