package com.jgonmor.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.service.sell.ISellService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SellControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISellService sellService;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<Product> products = List.of(
            new Product(1L,"product 1", "Brand 1", 10.0, 5),
            new Product(2L,"product 2", "Brand 2", 20.0, 2),
            new Product(3L,"product 3", "Brand 3", 30.0, 3)
    );

    private final Client defaultClient = new Client(
            1L,
            "Juan",
            "Gonzalez",
            "12345678A"
    );

    @Test
    public void testGetAllSellsStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sells")
                                              .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk())
               .andExpect(MockMvcResultMatchers
                                  .content()
                                  .contentType("application/json"));
    }

    @Test
    void getAllSells_shouldReturnSellList() throws Exception {
        // Arrange
        Sell sell1 = new Sell(1L,
                              LocalDateTime.now(),
                              100d,
                              products,
                              defaultClient);
        Sell sell2 = new Sell(2L,
                              LocalDateTime.now(),
                              200d,
                              products,
                              defaultClient);

        List<SellDto> sells = Arrays.asList(SellDto.fromEntity(sell1), SellDto.fromEntity(sell2));

        Mockito.when(sellService.getAllSells()).thenReturn(sells);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sells")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].id", is(1)))
               .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    public void testGetSellByIdStatus200() throws Exception {
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             products,
                             defaultClient);
        Mockito.when(sellService.getSellById(1L)).thenReturn(sell);

        mockMvc.perform(MockMvcRequestBuilders.get("/sells/1")
                                              .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content()
                                               .contentType("application/json"));
    }

    @Test
    void getSellById_shouldReturnSell() throws Exception {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             products,
                             defaultClient);
        when(sellService.getSellById(1L)).thenReturn(sell);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sells/1")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testGetSellByIdStatus404() throws Exception {
        Mockito.when(sellService.getSellById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/sells/1")
                                              .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void saveSell_shouldReturnSavedSell() throws Exception {
        // Arrange
        Sell sell = new Sell(1L,
                              LocalDateTime.now(),
                              100d,
                              products,
                              defaultClient);
        Sell savedSell = new Sell(1L,
                              LocalDateTime.now(),
                              100d,
                              products,
                              defaultClient);
        Mockito.when(sellService.saveSell(Mockito.any(Sell.class))).thenReturn(savedSell);

        // Act & Assert
        mockMvc.perform(post("/sells/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sell)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.total", is(100d)));
    }

    @Test
    void updateSell_shouldReturnUpdatedSell() throws Exception {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             150d,
                             products,
                             defaultClient);
        Mockito.when(sellService.updateSell(Mockito.any(Sell.class))).thenReturn(sell);

        // Act & Assert
        mockMvc.perform(put("/sells/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sell)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.total", is(150d)));
    }

    @Test
    void deleteSell_shouldReturnSuccessMessage() throws Exception {
        // Arrange
        when(sellService.deleteSell(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/sells/delete/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().string("Sell removed successfully"));
    }

    @Test
    void deleteSell_shouldReturnNotFoundMessage() throws Exception {
        // Arrange
        Mockito.when(sellService.deleteSell(1L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/sells/delete/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Sell not found"));
    }

    @Test
    void getSellProducts_shouldReturnProducts() throws Exception {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             products,
                             defaultClient);
        when(sellService.getProductsFromSell(1L)).thenReturn(products);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sells/products/1")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(3)))
               .andExpect(jsonPath("$[0].id", is(1)))
               .andExpect(jsonPath("$[1].id", is(2)))
               .andExpect(jsonPath("$[2].id", is(3)));
    }

    @Test
    void getTotalFromSellsOnDay_shouldReturnTotal() throws Exception {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             products,
                             defaultClient);
        when(sellService.getTotalFromSellsOnDay(LocalDate.parse("2025-01-01"))).thenReturn(100.00);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sells/total/2025-01-01")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().string("100.0"));
    }

    @Test
    void getBiggestSell_shouldReturnBiggestSell() throws Exception {
        // Arrange
        SellClientNameDto sell = new SellClientNameDto(1L, 100.00, 10, "Juan", "González" );
        when(sellService.getSellWithClientName()).thenReturn(sell);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sells/biggest-sell")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.total", is(100.00)))
               .andExpect(jsonPath("$.quantity", is(10)))
               .andExpect(jsonPath("$.name", is("Juan")))
               .andExpect(jsonPath("$.lastName", is("González")));
    }

}
