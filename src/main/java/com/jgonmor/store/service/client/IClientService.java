package com.jgonmor.store.service.client;

import com.jgonmor.store.dto.ClientDto;

import java.util.List;

public interface IClientService {

    /**
     * Gets all Clients
     *
     * @return A List of ClientDto.
     */
    List<ClientDto> getAllClients();

    /**
     * Finds a client by an id.
     *
     * @param id The id of the Client to be found.
     * @return A ClientDto.
     */
    ClientDto getClientById(Long id);

    /**
     * Creates a new Client.
     *
     * @param client The Client to be created.
     * @return The new Client as DTO.
     */
    ClientDto saveClient(ClientDto client);

    /**
     * Deletes a Client by an id.
     *
     * @param id The id of the Client to be deleted.
     * @return A Boolean indicating if the Client was deleted successfully.
     */
    Boolean deleteClient(Long id);

    /**
     * Updates a Client.
     *
     * @param client The Client as ClientDto to be updated.
     * @return the updated Client as ClientDto.
     */
    ClientDto updateClient(ClientDto client);
}
