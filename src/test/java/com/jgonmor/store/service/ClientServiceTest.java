package com.jgonmor.store.service;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.mapper.Mapper;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.repository.IClientRepository;
import com.jgonmor.store.service.client.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private IClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;


    @Test
    void testGetAllClients() {
        // Arrange
        Client client1 = new Client(1L, "client 1", "Last 1", "12345678A");
        Client client2 = new Client(2L, "client 2", "Last 2", "87654321B");
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        // Act
        var clients = clientService.getAllClients();

        // Assert
        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals("client 1", clients.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClientById() {
        // Arrange
        Client client = new Client(1L, "client 1", "Last 1", "12345678A");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Act
        var result = clientService.getClientById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("client 1", result.getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetClientByIdNotFound() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrowsExactly(ResourceNotFoundException.class, () -> clientService.getClientById(1L));
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveClient() {
        // Arrange
        Client client = new Client(null, "client 1", "Last 1", "12345678A");
        Client savedClient = new Client(1L, "client 1", "Last 1", "12345678A");
        ClientDto clientDto = Mapper.clientToDto(client);

        when(clientRepository.save(client)).thenReturn(savedClient);

        // Act
        ClientDto result = clientService.saveClient(clientDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedClient.getName(), result.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testDeleteClient() {
        // Arrange
        Long id = 1L;
        Client client = new Client(1L, "client 1", "Last 1", "12345678A");
        when(clientRepository.existsById(client.getId())).thenReturn(true);

        // Act
        Boolean result = clientService.deleteClient(id);

        // Assert
        assertTrue(result);
        verify(clientRepository, times(1)).deleteById(id);
        verify(clientRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteClientNotFound() {
        // Arrange
        when(clientRepository.existsById(1L)).thenReturn(false);

        // Act

        assertThrowsExactly(ResourceNotFoundException.class, () -> clientService.deleteClient(1L));
        verify(clientRepository, times(0)).deleteById(1L);
    }

    @Test
    void testUpdateClient() {
        // Arrange
        Client client = new Client(1L, "client 1", "Last 1", "12345678A");
        ClientDto clientDto = new ClientDto(1L, "client 1", "Last 1", "12345678A");
        Client updatedEntity = new Client(1L, "client updated", "Last 1", "12345678A");
        ClientDto updatedClient = new ClientDto(1L, "client updated", "Last 1", "12345678A");

        when(clientRepository.existsById(1L)).thenReturn(true);

        when(clientRepository.save(any(Client.class))).thenReturn(updatedEntity);

        // Act
        ClientDto result = clientService.updateClient(clientDto);

        // Assert
        assertNotNull(result);
        assertEquals(updatedClient, result);
        verify(clientRepository, times(1)).save(client);
    }
    
}
