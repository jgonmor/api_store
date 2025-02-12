package com.jgonmor.store.service.client;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.exceptions.EmptyTableException;
import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.mapper.Mapper;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Client Service Class
 * Implements methods to manage clients.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository clientRepository;

    /**
     * Checks if the client exists.
     *
     * @param id The id of the client to be checked.
     */
    private void existOrException(Long id){
        boolean exists = clientRepository.existsById(id);
        if(!exists){
            throw new ResourceNotFoundException("Client not found");
        }
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        if(clients.isEmpty()){
            throw new EmptyTableException("There are no clients");
        }

        return Mapper.clientsToDtoList(clients);
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return Mapper.clientToDto(client);
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        Client client = Mapper.clientDtoToEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return Mapper.clientToDto(savedClient);
    }


    @Override
    public Boolean deleteClient(Long id) {

        this.existOrException(id);

        clientRepository.deleteById(id);

        return true;
    }

    @Override
    public ClientDto updateClient(ClientDto client) {
        this.existOrException(client.getId());

        return this.saveClient(client);
    }

}
