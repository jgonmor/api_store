package com.jgonmor.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime date;
    private Double total;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sell")
    private List<SellDetail> sellDetails;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}

