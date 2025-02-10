package com.jgonmor.store.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClientDto {
    private Long id;
    private String name;
    private String lastName;
    private String citizenId;

}
