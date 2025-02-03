package com.jgonmor.store.service;

import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.repository.IProductRepository;
import com.jgonmor.store.service.product.ProductService;
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
public class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


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
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
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

        when(productRepository.existsById(id)).thenReturn(true);

        // Act
        Boolean result = productService.deleteProduct(id);

        // Assert
        assertTrue(result);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProductNotFound() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(false);

        // Act
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(0)).deleteById(1L);
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product product = new Product(1L, "product 1", "Brand 1", 10.0, 15);
        Product updatedProduct = new Product(1L, "product 1 updated", "Brand 1 updated", 15.0, 10);

        when(productRepository.existsById(product.getId())).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product result = productService.updateProduct(updatedProduct);

        // Assert
        assertNotNull(result);
        assertEquals("product 1 updated", result.getName());
        assertEquals(15.0, result.getPrice());
        assertEquals(10, result.getStock());
        verify(productRepository, times(1)).save(updatedProduct);
    }

}
