package com.jgonmor.store.repository;


import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.model.SellDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * SellDetail Repository Class
 * Manages sellsDetails in the database.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Repository
public interface ISellDetailRepository extends JpaRepository<SellDetail, Long> {

    /**
     * Finds sellDetails by sell and product.
     *
     * @param sell The sell.
     * @param product The product.
     * @return A sellDetail for the given sell.
     */
    Optional<SellDetail> findBySellAndProduct(Sell sell, Product product);

}