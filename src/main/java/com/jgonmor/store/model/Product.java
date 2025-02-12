package com.jgonmor.store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Product Model Class.
 * Represents a Product.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@DynamicUpdate
public class Product {

    /**
     * The id of the Product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the Product.
     */
    private String name;

    /**
     * The brand of the Product.
     */
    private String brand;

    /**
     * The price of the Product.
     */
    private Double price;

    /**
     * The stock of the Product.
     */
    private Integer stock;


}
