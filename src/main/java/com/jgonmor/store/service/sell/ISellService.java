package com.jgonmor.store.service.sell;

import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;

import java.time.LocalDate;
import java.util.List;

public interface ISellService {

    /**
     * Gets all Sells
     *
     * @return A List of SellDto.
     */
    List<SellDto> getAllSells();

    /**
     * Finds a Sell by an id.
     *
     * @param id The id of the Sell to be found.
     * @return A SellDto.
     */
    SellDto getSellById(Long id);

    /**
     * Deletes a Sell by an id.
     *
     * @param id The id of the Sell to be deleted.
     * @return A Boolean indicating if the Sell was deleted successfully.
     */
    Boolean deleteSell(Long id);

    /**
     * Creates a new Sell.
     *
     * @param sell The Sell to be created.
     * @return The new Sell as SellDto.
     */
    SellDto saveSell(Sell sell);

    /**
     * Updates a Sell.
     *
     * @param sell The Sell as SellDto to be updated.
     * @return the updated Sell as SellDto.
     */
    SellDto updateSell(Sell sell);

    /**
     * Gets Products from a Sell.
     *
     * @param sellId The id of the Sell.
     * @return A List of Products.
     */
    List<Product> getProductsFromSell(Long sellId);

    /**
     * Gets the total of earnings on a day.
     *
     * @param date The date of the sells.
     * @return The total of earnings on that day.
     */
    Double getTotalFromSellsOnDay(LocalDate date);

    /**
     * Gets the biggest Sell.
     *
     * @return The biggest Sell.
     */
    SellClientNameDto getBiggestSellWithClientName();

    /**
     * Creates a new Sell for each sell in the list.
     *
     * @param sells The List of Sells to be created.
     * @return A List of the new Sells.
     */
    List<SellDto> saveSells(List<Sell> sells);

    /**
     * Removes a Product from a Sell.
     *
     * @param sellId The id of the Sell.
     * @param productId The id of the Product.
     * @return The updated Sell.
     */
    SellDto removeProductFromSell(Long sellId,
                                  Long productId);
}
