package com.jgonmor.store.dto;

import com.jgonmor.store.model.Sell;
import com.jgonmor.store.model.SellDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SellClientNameDto {

    private long id;
    private double total;
    private List<SellDetail> sellDetails;
    private String name;
    private String lastName;

    public static SellClientNameDto fromEntity(Sell sell){
        return new SellClientNameDto( sell.getId(),
                                      sell.getTotal(),
                                      sell.getSellDetails(),
                                      sell.getClient().getName(),
                                      sell.getClient().getLastName());
    }

}
