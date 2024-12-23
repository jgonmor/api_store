package com.jgonmor.store.dto;

import com.jgonmor.store.model.Client;
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


    static public ClientDto fromEntity(Client client) {
        return new ClientDto(client.getId(),
                             client.getName(),
                             client.getLastName(),
                             client.getCitizenId());
    }

    static public Client toEntity(ClientDto client) {
        return new Client(client.getId(),
                          client.getName(),
                          client.getLastName(),
                          client.getCitizenId());
    }
}
