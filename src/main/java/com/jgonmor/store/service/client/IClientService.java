package com.jgonmor.store.service.client;

import com.jgonmor.store.model.Client;

import java.util.List;

public interface IClientService {

    List<Client> getAllClients();

    Client getClientById(Long id);

    Client saveClient(Client client);

    Boolean deleteClient(Long id);

    Client updateClient(Client client);
}
