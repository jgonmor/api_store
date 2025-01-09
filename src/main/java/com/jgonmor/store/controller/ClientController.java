package com.jgonmor.store.controller;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.service.client.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/")
    public ResponseEntity<?> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        ClientDto client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.status(404)
                                 .body("Client not found");
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping("/new")
    public ResponseEntity<?> saveClient(@RequestBody ClientDto client) {
        ClientDto newClient = clientService.saveClient(client);
        return ResponseEntity.ok(newClient);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody ClientDto client) {
        ClientDto updatedClient = clientService.updateClient(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        Boolean deleted = clientService.deleteClient(id);

        if (deleted) {
            return ResponseEntity.status(200)
                                 .body("Client removed successfully");
        }

        return ResponseEntity.status(404)
                             .body("Client not found");
    }
}
