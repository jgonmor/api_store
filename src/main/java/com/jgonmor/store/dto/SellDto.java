package com.jgonmor.store.dto;

import com.jgonmor.store.model.Client;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SellDto {

    private Long id;
    private LocalDateTime date;
    private Double total;
    private ClientDto client;
    private List<Product> products;


    static public SellDto fromEntity(Sell sell) {
        return new SellDto(sell.getId(),
                           sell.getDate(),
                           sell.getTotal(),
                           ClientDto.fromEntity(sell.getClient()),
                           sell.getProducts());
    }

    public Sell toEntity(SellDto sellDto) {
        return new Sell(sellDto.getId(),
                        sellDto.getDate(),
                        sellDto.getTotal(),
                        sellDto.getProducts(),
                        ClientDto.toEntity(sellDto.getClient()));

    }
}
