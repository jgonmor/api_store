package com.jgonmor.store.dto;

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

}
