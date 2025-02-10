package com.jgonmor.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.mapper.Mapper;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.model.SellDetail;
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
import java.time.temporal.ChronoUnit;
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

    private final List<SellDetail> sellDetails = List.of(
            new SellDetail(1L, 10.0, 1, 10.0, null, products.get(0)),
            new SellDetail(2L, 20.0, 1, 20.0, null, products.get(1)),
            new SellDetail(3L, 30.0, 1, 30.0, null, products.get(2))
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
                              sellDetails,
                              defaultClient);
        Sell sell2 = new Sell(2L,
                              LocalDateTime.now(),
                              200d,
                              sellDetails,
                              defaultClient);

        List<SellDto> sells = Mapper.sellsToDtoList(Arrays.asList(sell1, sell2));

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
                             sellDetails,
                             defaultClient);
        SellDto sellDto = Mapper.sellToDto(sell);
        Mockito.when(sellService.getSellById(1L)).thenReturn(sellDto);

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
                             sellDetails,
                             defaultClient);
        SellDto sellDto = Mapper.sellToDto(sell);
        when(sellService.getSellById(1L)).thenReturn(sellDto);

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
                             sellDetails,
                              defaultClient);
        SellDto sellDto = Mapper.sellToDto(sell);
        Sell savedSell = new Sell(1L,
                              LocalDateTime.now(),
                              100d,
                                  sellDetails,
                              defaultClient);
        SellDto savedSellDto = Mapper.sellToDto(savedSell);
        Mockito.when(sellService.saveSell(Mockito.any(Sell.class))).thenReturn(savedSellDto);

        // Act & Assert
        mockMvc.perform(post("/sells/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sellDto)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.total", is(100d)));
    }

    @Test
    void updateSell_shouldReturnUpdatedSell() throws Exception {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                             150d,
                             sellDetails,
                             defaultClient);

        SellDto sellDto = Mapper.sellToDto(sell);
        Mockito.when(sellService.updateSell(Mockito.any(Sell.class))).thenReturn(sellDto);

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
        SellClientNameDto sell = new SellClientNameDto(1L, 100.00, sellDetails, "Juan", "González" );
        when(sellService.getBiggestSellWithClientName()).thenReturn(sell);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sells/biggest-sell")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.total", is(100.00)))
               .andExpect(jsonPath("$.sellDetails", hasSize(3)))
               .andExpect(jsonPath("$.sellDetails[0].id", is(1)))
               .andExpect(jsonPath("$.sellDetails[0].unitPrice", is(sellDetails.get(0).getUnitPrice())))
               .andExpect(jsonPath("$.sellDetails[0].quantity", is(sellDetails.get(0).getQuantity())))
               .andExpect(jsonPath("$.sellDetails[0].total", is(sellDetails.get(0).getTotal())))
               .andExpect(jsonPath("$.sellDetails[0].product.id", is(1)))
               .andExpect(jsonPath("$.sellDetails[0].product.name", is(sellDetails.get(0).getProduct().getName())))
               .andExpect(jsonPath("$.sellDetails[0].product.brand", is(sellDetails.get(0).getProduct().getBrand())))
               .andExpect(jsonPath("$.sellDetails[0].product.price", is(sellDetails.get(0).getProduct().getPrice())))
               .andExpect(jsonPath("$.sellDetails[0].product.stock", is(sellDetails.get(0).getProduct().getStock())))
               .andExpect(jsonPath("$.sellDetails[1].id", is(2)))
               .andExpect(jsonPath("$.sellDetails[1].unitPrice", is(sellDetails.get(1).getUnitPrice())))
               .andExpect(jsonPath("$.sellDetails[1].quantity", is(sellDetails.get(1).getQuantity())))
               .andExpect(jsonPath("$.sellDetails[1].total", is(sellDetails.get(1).getTotal())))
               .andExpect(jsonPath("$.sellDetails[1].product.id", is(2)))
               .andExpect(jsonPath("$.sellDetails[1].product.name", is(sellDetails.get(1).getProduct().getName())))
               .andExpect(jsonPath("$.sellDetails[1].product.brand", is(sellDetails.get(1).getProduct().getBrand())))
               .andExpect(jsonPath("$.sellDetails[1].product.price", is(sellDetails.get(1).getProduct().getPrice())))
               .andExpect(jsonPath("$.sellDetails[1].product.stock", is(sellDetails.get(1).getProduct().getStock())))
               .andExpect(jsonPath("$.sellDetails[2].id", is(3)))
               .andExpect(jsonPath("$.sellDetails[2].unitPrice", is(sellDetails.get(2).getUnitPrice())))
               .andExpect(jsonPath("$.sellDetails[2].quantity", is(sellDetails.get(2).getQuantity())))
               .andExpect(jsonPath("$.sellDetails[2].total", is(sellDetails.get(2).getTotal())))
               .andExpect(jsonPath("$.sellDetails[2].product.id", is(3)))
               .andExpect(jsonPath("$.sellDetails[2].product.name", is(sellDetails.get(2).getProduct().getName())))
               .andExpect(jsonPath("$.sellDetails[2].product.brand", is(sellDetails.get(2).getProduct().getBrand())))
               .andExpect(jsonPath("$.sellDetails[2].product.price", is(sellDetails.get(2).getProduct().getPrice())))
               .andExpect(jsonPath("$.sellDetails[2].product.stock", is(sellDetails.get(2).getProduct().getStock())))
               .andExpect(jsonPath("$.name", is("Juan")))
               .andExpect(jsonPath("$.lastName", is("González")));
    }

    @Test
    void removeProductFromSell_shouldReturnStatus200() throws Exception {
        // Arrange
        SellDto sell = new SellDto(1L,
                                   LocalDateTime.now(),
                                   100d,
                                   Mapper.clientToDto(defaultClient),
                                   Mapper.sellDetailtoDtoList(sellDetails));
        when(sellService.removeProductFromSell(1L, 1L)).thenReturn(sell);

        // Act & Assert
        mockMvc.perform(patch("/sells/update/remove-product/1/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.total", is(100d)));
    }


}
