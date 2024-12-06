package com.jgonmor.store.service;

import com.jgonmor.store.model.Product;
import com.jgonmor.store.repository.IProductRepository;
import com.jgonmor.store.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product product1 = new Product(1L, "product 1", "Brand 1", 10.0, 5);
        Product product2 = new Product(2L, "product 2", "Brand 2", 15.0, 10);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        var products = productService.getAllProducts();

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("product 1", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        // Arrange
        Product product = new Product(1L, "product 1", "Brand 1", 10.0, 5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        var result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("product 1", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        var result = productService.getProductById(1L);

        // Assert
        assertNull(result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveProduct() {
        // Arrange
        Product product = new Product(null,"product 1", "Brand 1", 10.0, 5);
        Product savedProduct = new Product(1L, "product 1", "Brand 1", 10.0, 5);

        when(productRepository.save(product)).thenReturn(savedProduct);

        // Act
        Product result = productService.saveProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals(savedProduct, result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long id = 1L;
        Product product = new Product(id, "product 1", "Brand 1", 10.0, 5);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // Act
        Boolean result = productService.deleteProduct(id);

        // Assert
        assertTrue(result);
        verify(productRepository, times(1)).deleteById(id);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Boolean result = productService.deleteProduct(1L);

        // Assert
        assertFalse(result);
        verify(productRepository, times(0)).deleteById(1L);
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product product = new Product(1L, "product 1", "Brand 1", 10.0, 5);
        Product updatedProduct = new Product(1L, "product 1 updated", "Brand 1 updated", 10.0, 5);

        when(productService.updateProduct(product)).thenReturn(updatedProduct);

        // Act
        Product result = productService.updateProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals(updatedProduct, result);
        verify(productRepository, times(1)).save(product);
    }

}
