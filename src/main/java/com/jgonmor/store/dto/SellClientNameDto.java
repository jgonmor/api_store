package com.jgonmor.store.dto;

import com.jgonmor.store.model.SellDetail;
import lombok.*;

import java.util.List;

/**
 * A Sell DTO class with the client name.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
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
