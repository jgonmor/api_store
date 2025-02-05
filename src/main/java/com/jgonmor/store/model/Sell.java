package com.jgonmor.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime date;
    private Double total;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sell")
    private List<SellDetail> sellDetails;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}

