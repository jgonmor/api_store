package com.jgonmor.store.dto;

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
    private List<SellDetailDto> sellDetails;


}
