package com.jgonmor.store.service.client;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public List<ClientDto> getAllClients() {
        return this.toDtoList(clientRepository.findAll());
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return null;
        }
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

        if(this.getClientById(id) == null){
            return false;
        }

        clientRepository.deleteById(id);

        return true;
    }

    @Override
    public ClientDto updateClient(ClientDto client) {
        return this.saveClient(client);
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
