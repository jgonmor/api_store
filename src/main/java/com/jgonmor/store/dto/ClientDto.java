package com.jgonmor.store.dto;

import lombok.*;


/**
 * Client DTO Class.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
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
