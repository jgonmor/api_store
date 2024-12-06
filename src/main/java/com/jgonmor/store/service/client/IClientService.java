package com.jgonmor.store.service.client;

import com.jgonmor.store.dto.ClientDto;

import java.util.List;

public interface IClientService {

    List<ClientDto> getAllClients();

    ClientDto getClientById(Long id);

    ClientDto saveClient(ClientDto client);

    Boolean deleteClient(Long id);

    ClientDto updateClient(ClientDto client);
}
