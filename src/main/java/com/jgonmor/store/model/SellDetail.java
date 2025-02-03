package com.jgonmor.store.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
public class SellDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double unitPrice;
    private int quantity;
    private double total;

    @ManyToOne
    private Sell sell;

    @ManyToOne
    private Product product;

}
