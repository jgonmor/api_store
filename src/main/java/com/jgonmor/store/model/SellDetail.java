package com.jgonmor.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
