package com.jgonmor.store.service.client;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.exceptions.EmptyTableException;
import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        if(clients.isEmpty()){
            throw new EmptyTableException("There are no clients");
        }

        return this.toDtoList(clients);
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = this.existOrException(id);
        return this.toDto(client);
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        Client client = this.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return this.toDto(savedClient);
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

    private Client existOrException(Long id){
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    public ClientDto toDto(Client client) {
        return new ClientDto(client.getId(),
                             client.getName(),
                             client.getLastName(),
                             client.getCitizenId());
    }

    public Client toEntity(ClientDto client) {
        return new Client(client.getId(),
                          client.getName(),
                          client.getLastName(),
                          client.getCitizenId());
    }

    public List<ClientDto> toDtoList(List<Client> clients) {
        return clients.stream()
                      .map(this::toDto)
                      .toList();
    }

}
