package com.jgonmor.store.service;

import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.mapper.Mapper;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.model.SellDetail;
import com.jgonmor.store.repository.ISellDetailRepository;
import com.jgonmor.store.repository.ISellRepository;
import com.jgonmor.store.service.product.IProductService;
import com.jgonmor.store.service.sell.SellService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellServiceTest {

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

    @Mock
    private ISellRepository sellRepository;

    @Mock
    private IProductService productService;

    @Mock
    private ISellDetailRepository sellDetailRepository;

    @InjectMocks
    private SellService sellService;

    @Test
    void testGetAllSells() {
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

        when(sellRepository.findAll()).thenReturn(Arrays.asList(sell1, sell2));

        // Act
        var sells = sellService.getAllSells();

        // Assert
        assertNotNull(sells);
        assertEquals(2, sells.size());
        assertEquals(1L, sells.get(0).getId());
        verify(sellRepository, times(1)).findAll();
    }

    @Test
    void testGetSellById() {
        // Arrange
        Sell sell = new Sell(1L,
                LocalDateTime.now(),
                100d,
                sellDetails,
                defaultClient);
        when(sellRepository.findById(1L)).thenReturn(Optional.of(sell));

        // Act
        var result = sellService.getSellById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(sellRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSellByIdNotFound() {
        // Arrange
        when(sellRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            sellService.getSellById(1L);
        } catch (ResourceNotFoundException e) {
            // Assert
            assertEquals("Sell not found", e.getMessage());
        } finally {
            verify(sellRepository, times(1)).findById(1L);
        }

    }

    @Test
    void testSaveSell() {
        // Arrange
        Sell sell = new Sell(null,
                LocalDateTime.now(),
                100d,
                sellDetails,
                defaultClient);
        Sell savedSell = new Sell(1L,
                LocalDateTime.now(),
                100d,
                sellDetails,
                defaultClient);

        when(productService.getProductById(1L)).thenReturn(products.get(0));
        when(productService.getProductById(2L)).thenReturn(products.get(1));
        when(productService.getProductById(3L)).thenReturn(products.get(2));

        when(productService.saveProducts(anyList())).thenReturn(products);
        when(sellRepository.save(any(Sell.class))).thenReturn(savedSell);


        // Act
        SellDto result = sellService.saveSell(sell);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());

        products.forEach(product -> verify(productService).getProductById(product.getId()));

        verify(productService, times(1)).saveProducts(anyList());

        verify(sellRepository, times(1)).save(sell);
    }

    @Test
    void testDeleteSell() {
        // Arrange
        Long id = 1L;
        when(sellRepository.existsById(id)).thenReturn(true);

        // Act
        Boolean result = sellService.deleteSell(id);

        // Assert
        assertTrue(result);
        verify(sellRepository, times(1)).deleteById(id);
        verify(sellRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteSellNotFound() {
        // Arrange
        when(sellRepository.existsById(1L)).thenReturn(false);

        // Act
        try {
            sellService.deleteSell(1L);
        } catch (ResourceNotFoundException e) {
            // Assert
            assertEquals("Sell not found", e.getMessage());
            verify(sellRepository, times(0)).deleteById(1L);
        } finally {
            verify(sellRepository, times(1)).existsById(1L);
        }

    }

    @Test
    void testUpdateSell() {
        // Arrange

        Sell sell = new Sell(1L,
                LocalDateTime.now(),
                100d,
                sellDetails,
                defaultClient);
        Sell updatedSell = new Sell(1L,
                LocalDateTime.now(),
                200d,
                sellDetails,
                defaultClient);

        SellDto updatedSellDto = Mapper.sellToDto(updatedSell);

        for ( int i = 0; i < sellDetails.size(); i++) {
            when(sellDetailRepository.findBySellAndProduct(sell, sellDetails.get(i).getProduct())).thenReturn(Optional.of(sellDetails.get(i)));
            when(productService.getProductById(1L + i)).thenReturn(products.get(i));
        }
        when(productService.saveProducts(anyList())).thenReturn(products);

        when(sellRepository.existsById(1L)).thenReturn(true);
        when(sellRepository.save(any(Sell.class))).thenReturn(updatedSell);

        // Act
        SellDto result = sellService.updateSell(sell);

        // Assert
        assertNotNull(result);
        assertEquals(updatedSellDto.getId(), result.getId());
        verify(sellRepository, times(1)).save(sell);
    }

    @Test
    void testGetProductsBySellId() {
        // Arrange
        when(sellRepository.existsById(1L)).thenReturn(true);
        when(sellRepository.findProductsBySellId(1L)).thenReturn(products);

        // Act
        List<Product> result = sellService.getProductsFromSell(1L);

        // Assert
        assertNotNull(result);
        assertEquals(products, result);
        verify(sellRepository, times(1)).findProductsBySellId(1L);
    }

    @Test
    void testGetTotalFromSellsOnDay() {
        LocalDate date = LocalDate.now();

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59, 999999999);

        when(sellRepository.getTotalFromSellsInAPeriod(start, end)).thenReturn(100.00);

        // Act
        Double result = sellService.getTotalFromSellsOnDay(date);

        // Assert
        assertNotNull(result);
        verify(sellRepository, times(1)).getTotalFromSellsInAPeriod(start, end);
        assertEquals(100.00, result);  // Assert the expected value

    }

    @Test
    void testGetBiggestSell() {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             sellDetails,
                             defaultClient);
        when(sellRepository.findBiggestSell()).thenReturn(sell);

        // Act
        SellClientNameDto result = sellService.getBiggestSellWithClientName();

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100.00, result.getTotal());
        assertEquals("Juan", result.getName());
        assertEquals("Gonzalez", result.getLastName());
        verify(sellRepository, times(1)).findBiggestSell();
    }

    @Test
    void testRemoveProductFromSell() {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             sellDetails,
                             defaultClient);
        when(sellRepository.findById(1L)).thenReturn(Optional.of(sell));
        when(productService.getProductById(1L)).thenReturn(products.get(0));
        when(sellDetailRepository.findBySellAndProduct(sell, products.get(0))).thenReturn(Optional.of(sellDetails.get(0)));

        // Act
        SellDto result = sellService.removeProductFromSell(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(sellRepository, times(1)).findById(1L);
        verify(sellRepository, times(1)).save(sell);
        verify(productService, times(1)).getProductById(1L);
        verify(productService, times(1)).saveProduct(products.get(0));
    }

    @Test
    void testRemoveProductFromSellNotFound() {
        // Arrange
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             sellDetails,
                             defaultClient);
        when(sellRepository.findById(1L)).thenReturn(Optional.of(sell));
        when(productService.getProductById(2L)).thenReturn(null);

        // Act
        try {
            sellService.removeProductFromSell(1L, 2L);
        } catch (ResourceNotFoundException e) {
            // Assert
            assertEquals("Product not found", e.getMessage());

        } finally {
            verify(sellRepository, times(1)).findById(1L);
            verify(sellRepository, times(0)).save(sell);
            verify(productService, times(0)).saveProduct(products.get(1));
            verify(sellDetailRepository, times(0)).findBySellAndProduct(sell, products.get(1));
            verify(sellDetailRepository, times(0)).delete(sellDetails.get(1));

        }

    }

}
