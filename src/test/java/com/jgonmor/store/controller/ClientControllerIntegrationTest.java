package com.jgonmor.store.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.service.client.IClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllClients_shouldReturnStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clients")
                                              .contentType("application/json"))
               .andExpect(status()
                                               .isOk())
               .andExpect(content()
                                  .contentType("application/json"));
    }

    @Test
    void getAllClients_shouldReturnClients() throws Exception {
        // Arrange
        ClientDto client1 = new ClientDto(1L, "client 1", "Last 1", "12345678A");
        ClientDto client2 = new ClientDto(2L, "client 2", "Last 2", "87654321B");
        Mockito.when(clientService.getAllClients()).thenReturn(Arrays.asList(client1, client2));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/clients")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].id", is(1)))
               .andExpect(jsonPath("$[0].name", is("client 1")))
               .andExpect(jsonPath("$[0].lastName", is("Last 1")))
               .andExpect(jsonPath("$[0].citizenId", is("12345678A")))
               .andExpect(jsonPath("$[1].id", is(2)))
               .andExpect(jsonPath("$[1].name", is("client 2")))
               .andExpect(jsonPath("$[1].lastName", is("Last 2")))
               .andExpect(jsonPath("$[1].citizenId", is("87654321B")));
    }

    @Test
    public void testGetClientById_shouldReturnStatus200() throws Exception {
        ClientDto client = new ClientDto(1L, "client 1", "Last 1", "12345678A");
        Mockito.when(clientService.getClientById(1L)).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/1")
                                              .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json"));
    }

    @Test
    void getClientById_shouldReturnClient() throws Exception {
        // Arrange
        ClientDto client = new ClientDto(1L, "client 1", "Last 1", "12345678A");
        when(clientService.getClientById(1L)).thenReturn(client);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/clients/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("client 1")))
               .andExpect(jsonPath("$.lastName", is("Last 1")))
               .andExpect(jsonPath("$.citizenId", is("12345678A")));
    }

    @Test
    public void testGetClientById_shouldReturnStatus404() throws Exception {
        Mockito.when(clientService.getClientById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/1")
                                              .contentType("application/json"))
               .andExpect(status().isNotFound());
    }

    @Test
    void saveClient_shouldReturnSavedClient() throws Exception {
        // Arrange
        Client client = new Client(null, "client 1", "Last 1", "12345678A");
        ClientDto savedClient = new ClientDto(1L, "client 1", "Last 1", "12345678A");
        when(clientService.saveClient(Mockito.any(ClientDto.class))).thenReturn(savedClient);

        // Act & Assert
        mockMvc.perform(post("/clients/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(client)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("client 1")));
    }

    @Test
    void updateClient_shouldReturnUpdatedClient() throws Exception {
        // Arrange
        ClientDto client = new ClientDto(1L, "client 1", "Last 1", "12345678A");
        Mockito.when(clientService.updateClient(Mockito.any(ClientDto.class))).thenReturn(client);

        // Act & Assert
        mockMvc.perform(put("/clients/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(client)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("client 1")));
    }

    @Test
    void deleteClient_shouldReturnSuccessMessage() throws Exception {
        // Arrange
        when(clientService.deleteClient(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/clients/delete/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().string("Client removed successfully"));
    }

    @Test
    void deleteClient_shouldReturnNotFoundMessage() throws Exception {
        // Arrange
        Mockito.when(clientService.deleteClient(1L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/clients/delete/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Client not found"));
    }
}
