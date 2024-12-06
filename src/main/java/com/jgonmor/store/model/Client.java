package com.jgonmor.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String citizenId;

    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Sell> sells = new ArrayList<>();

    public Client(Long id, String name, String lastName, String citizenId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.citizenId = citizenId;
    }

    public void addSell(Sell sell) {
        sells.add(sell);
        sell.setClient(this);
    }

    public void removeSell(Sell sell) {
        sells.remove(sell);
        sell.setClient(null);
    }

}
