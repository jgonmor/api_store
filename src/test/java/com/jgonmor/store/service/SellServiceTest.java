package com.jgonmor.store.service;

import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.repository.ISellRepository;
import com.jgonmor.store.service.sell.SellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SellServiceTest {

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



    @Mock
    private ISellRepository sellRepository;

    @InjectMocks
    private SellService sellService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllSells() {
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
                products,
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
                products,
                defaultClient);
        Sell savedSell = new Sell(1L,
                LocalDateTime.now(),
                100d,
                products,
                defaultClient);

        when(sellRepository.save(sell)).thenReturn(savedSell);

        // Act
        Sell result = sellService.saveSell(sell);

        // Assert
        assertNotNull(result);
        assertEquals(savedSell, result);
        verify(sellRepository, times(1)).save(sell);
    }

    @Test
    void testDeleteSell() {
        // Arrange
        Long id = 1L;
        Sell sell = new Sell(1L,
                LocalDateTime.now(),
                100d,
                products,
                defaultClient);
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
                products,
                defaultClient);
        Sell updatedSell = new Sell(1L,
                LocalDateTime.now(),
                200d,
                products,
                defaultClient);

        when(sellRepository.existsById(1L)).thenReturn(true);
        when(sellRepository.save(any(Sell.class))).thenReturn(updatedSell);

        // Act
        Sell result = sellService.updateSell(sell);

        // Assert
        assertNotNull(result);
        assertEquals(updatedSell, result);
        verify(sellRepository, times(1)).save(sell);
    }

    @Test
    void testGetProductsBySellId() {
        // Arrange
        Sell sell = new Sell(1L,
                             LocalDateTime.now(),
                             100d,
                             products,
                             defaultClient);
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

        when(sellRepository.getTotalFromSellsOnDay(start, end)).thenReturn(100.00);

        // Act
        Double result = sellService.getTotalFromSellsOnDay(date);

        // Assert
        assertNotNull(result);
        verify(sellRepository, times(1)).getTotalFromSellsOnDay(start, end);
        assertEquals(100.00, result);  // Assert the expected value

    }
}
