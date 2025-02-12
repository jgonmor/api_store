package com.jgonmor.store.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Sell Detail Model Class.
 * Represents a Sell Detail for a Product in a Sell.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
public class SellDetail {

    /**
     * The id of the Sell Detail.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The unit price of the product.
     */
    private double unitPrice;

    /**
     * The quantity of the product.
     */
    private int quantity;

    /**
     * The total of the product.
     */
    private double total;

    /**
     * The sell of the product.
     */
    @ManyToOne
    private Sell sell;

    /**
     * The product of the sell detail.
     */
    @ManyToOne
    private Product product;

}
