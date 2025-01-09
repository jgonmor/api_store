package com.jgonmor.store.dto;

import com.jgonmor.store.model.Sell;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SellClientNameDto {

    private long id;
    private double total;
    private int quantity;
    private String name;
    private String lastName;

    public static SellClientNameDto fromEntity(Sell sell){
        return new SellClientNameDto( sell.getId(),
                                      sell.getTotal(),
                                      sell.getProducts().size(),
                                      sell.getClient().getName(),
                                      sell.getClient().getLastName());
    }

}
