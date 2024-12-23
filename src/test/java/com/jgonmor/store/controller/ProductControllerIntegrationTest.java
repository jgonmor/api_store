package com.jgonmor.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgonmor.store.exceptions.EmptyQueryException;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.service.product.IProductService;
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

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProductsStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/")
                                              .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk())
               .andExpect(MockMvcResultMatchers
                                  .content()
                                  .contentType("application/json"));
    }

    @Test
    void getAllProducts_shouldReturnProductList() throws Exception {
        // Arrange
        Product product1 = new Product(1L, "Product 1","brand 1" ,100.0, 10);
        Product product2 = new Product(2L, "Product 2", "brand 2" ,200.0, 20);
        Mockito.when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].id", is(1)))
               .andExpect(jsonPath("$[0].name", is("Product 1")))
               .andExpect(jsonPath("$[0].brand", is("brand 1")))
               .andExpect(jsonPath("$[0].price", is(100.0)))
               .andExpect(jsonPath("$[0].stock", is(10)))
               .andExpect(jsonPath("$[1].id", is(2)))
               .andExpect(jsonPath("$[1].name", is("Product 2")))
               .andExpect(jsonPath("$[1].brand", is("brand 2")))
               .andExpect(jsonPath("$[1].price", is(200.0)))
               .andExpect(jsonPath("$[1].stock", is(20)));
    }

    @Test
    public void testGetProductByIdStatus200() throws Exception {
        Product product = new Product(1L, "Product 1","brand 1" ,100.0, 10);
        Mockito.when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                                              .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content()
                                               .contentType("application/json"));
    }

    @Test
    void getProductById_shouldReturnProduct() throws Exception {
        // Arrange
        Product product = new Product(1L, "Product 1","brand 1" ,100.0, 10);
        when(productService.getProductById(1L)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("Product 1")))
               .andExpect(jsonPath("$.brand", is("brand 1")))
               .andExpect(jsonPath("$.price", is(100.0)))
               .andExpect(jsonPath("$.stock", is(10)));
    }

    @Test
    public void testGetProductByIdStatus404() throws Exception {
        Mockito.when(productService.getProductById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                                              .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void saveProduct_shouldReturnSavedProduct() throws Exception {
        // Arrange
        Product product = new Product(null, "New Product","brand 1" ,100.0, 10);
        Product savedProduct = new Product(1L, "New Product","brand 1" ,100.0, 10);
        Mockito.when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(savedProduct);

        // Act & Assert
        mockMvc.perform(post("/products/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("New Product")));
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        // Arrange
        Product product = new Product(1L, "Updated Product","brand 1" ,100.0, 10);
        Mockito.when(productService.updateProduct(Mockito.any(Product.class))).thenReturn(product);

        // Act & Assert
        mockMvc.perform(put("/products/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("Updated Product")));
    }

    @Test
    void deleteProduct_shouldReturnSuccessMessage() throws Exception {
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/products/delete/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().string("Product removed successfully"));
    }

    @Test
    void deleteProduct_shouldReturnNotFoundMessage() throws Exception {
        // Arrange
        Mockito.when(productService.deleteProduct(1L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/products/delete/1")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Product not found"));
    }

    @Test
    void getLowStockProducts_shouldReturnStatus200() throws Exception {
        // Arrange
        Product product1 = new Product(1L, "Product 1","brand 1" ,100.0, 10);
        Product product2 = new Product(2L, "Product 2", "brand 2" ,200.0, 20);
        Mockito.when(productService.getLowStockProducts()).thenReturn(Arrays.asList(product1, product2));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/low_stock")
                                              .contentType("application/json"))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk())
               .andExpect(MockMvcResultMatchers
                                  .content()
                                  .contentType("application/json"));
    }

    @Test
    void getLowStockProducts_shouldReturnStatus404() throws Exception {
        // Arrange

        Mockito.when(productService.getLowStockProducts()).thenThrow(new EmptyQueryException("There are no products with low stock"));

        // Act & Assert
            mockMvc.perform(MockMvcRequestBuilders.get("/products/low_stock")
                                                  .contentType("application/json"))
                   .andExpect(MockMvcResultMatchers.status()
                                                   .isNotFound());
            // Assert
            verify(productService, times(1)).getLowStockProducts();


    }

}
