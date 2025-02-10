package com.jgonmor.store.controller;

import com.jgonmor.store.dto.ClientDto;
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

    /**
     * Gets all Clients
     *
     * @return A Response with a List of Clients.
     */
    @GetMapping
    public ResponseEntity<?> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Finds a client by an id.
     *
     * @param id The id of the Client to be found.
     * @return A Response with a Client.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        ClientDto client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.status(404)
                                 .body("Client not found");
        }
        return ResponseEntity.ok(client);
    }

    /**
     * Creates a new Client.
     *
     * @param client The Client to be created.
     * @return A Response with a Client.
     */
    @PostMapping("/new")
    public ResponseEntity<?> saveClient(@RequestBody ClientDto client) {
        ClientDto newClient = clientService.saveClient(client);
        return ResponseEntity.ok(newClient);
    }

    /**
     * Updates a Client.
     *
     * @param client The Client to be updated.
     * @return A Response with the updated Client.
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody ClientDto client) {
        ClientDto updatedClient = clientService.updateClient(client);
        return ResponseEntity.ok(updatedClient);
    }

    /**
     * Deletes a Client by an id.
     *
     * @param id The id of the Client to be deleted.
     * @return A Response indicating if the Client was deleted successfully.
     */
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
