package com.jgonmor.store.service.client;

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
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
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
    public Client updateClient(Client client) {
        return this.saveClient(client);
    }


}
