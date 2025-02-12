package com.jgonmor.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * EmptyQueryException Class
 * Thrown when a query response is empty.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
public class Sell {

    /**
     * The id of the Sell.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The date of the Sell.
     * Format: yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime date;

    /**
     * The total of the Sell.
     */
    private Double total;

    /**
     * The sell details of the Sell.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sell")
    private List<SellDetail> sellDetails;

    /**
     * The client of the Sell.
     */
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}

