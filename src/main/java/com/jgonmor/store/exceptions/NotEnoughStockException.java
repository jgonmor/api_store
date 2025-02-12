package com.jgonmor.store.exceptions;

/**
 * NotEnoughStockException Class
 * Thrown when a product has not enough stock for sale.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
