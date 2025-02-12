package com.jgonmor.store.dto;

import com.jgonmor.store.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sell Detail DTO Class.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellDetailDto {

    private long id;
    private double unitPrice;
    private int quantity;
    private double total;

    private Product product;




}