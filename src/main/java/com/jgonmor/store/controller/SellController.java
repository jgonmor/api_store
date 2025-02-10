package com.jgonmor.store.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.service.sell.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sells")
public class SellController {

    @Autowired
    private ISellService sellService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Gets all Sells
     *
     * @return A Response with a List of Sell.
     */
    @GetMapping
    public ResponseEntity<?> getAllSells() {
        List<SellDto> sells = sellService.getAllSells();
        return ResponseEntity.ok(sells);
    }

    /**
     * Finds a Sell by an id.
     *
     * @param id The id of the Sell to be found.
     * @return A Response with a Sell.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getSellById(@PathVariable Long id) {
        SellDto sell = sellService.getSellById(id);
        if (sell == null) {
            return ResponseEntity.status(404)
                                 .body("Sell not found");
        }
        return ResponseEntity.ok(sell);
    }

    /**
     * Creates a new Sell.
     *
     * @param request The Sell to be created or a List of sells to be created.
     * @return A Response with a Sell or a List of Sells.
     */
    @PostMapping("/new")
    public ResponseEntity<?> saveSell(@RequestBody Object request) {

        SellDto response;
        List<SellDto> responseList;

        if (request instanceof List) {
            List<Sell> sells = objectMapper.convertValue(request,
                                                         new TypeReference<>() {}
            );
            responseList = sellService.saveSells(sells);
            return ResponseEntity.ok(responseList);
        } else if (request instanceof java.util.Map) {
            Sell sell = objectMapper.convertValue(request, Sell.class);
            response = sellService.saveSell(sell);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Request not valid");
        }

    }

    /**
     * Updates a Sell.
     *
     * @param sell The Sell to be updated.
     * @return A Response with the updated Sell.
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateSell(@RequestBody Sell sell) {
        SellDto updatedSell = sellService.updateSell(sell);
        return ResponseEntity.ok(updatedSell);
    }

    /**
     * Removes a Product from a Sell.
     *
     * @param sellId The id of the Sell.
     * @param productId The id of the Product.
     * @return A Response with the updated Sell.
     */
    @PatchMapping("update/remove-product/{sellId}/{productId}")
    public ResponseEntity<?> removeProductFromSell(@PathVariable Long sellId,
                                                   @PathVariable Long productId) {
        SellDto sell = sellService.removeProductFromSell(sellId, productId);
        return ResponseEntity.ok(sell);
    }

    /**
     * Deletes a Sell by an id.
     *
     * @param id The id of the Sell to be deleted.
     * @return A Response indicating if the Sell was deleted successfully.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSell(@PathVariable Long id) {
        Boolean deleted = sellService.deleteSell(id);

        if (deleted) {
            return ResponseEntity.status(200)
                                 .body("Sell removed successfully");
        }

        return ResponseEntity.status(404)
                             .body("Sell not found");
    }

    /**
     * Gets Products from a Sell.
     *
     * @param sellId The id of the Sell.
     * @return A Response with a List of Products.
     */
    @GetMapping("/products/{sellId}")
    public ResponseEntity<?> getSellProducts(@PathVariable Long sellId) {
        List<Product> products = sellService.getProductsFromSell(sellId);
        return ResponseEntity.ok(products);
    }

    /**
     * Gets all the earnings from sells on a day.
     *
     * @param date The date of the sells.
     * @return A Response with the total earnings.
     */
    @GetMapping("/total/{date}")
    public ResponseEntity<?> getTotalFromSellsOnDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Double total = sellService.getTotalFromSellsOnDay(date);
        return ResponseEntity.ok(total);
    }

    /**
     * Finds the biggest Sell.
     *
     * @return A Response with the biggest Sell.
     */
    @GetMapping("/biggest-sell")
    public ResponseEntity<?> getBiggestSell(){
        SellClientNameDto sell = sellService.getBiggestSellWithClientName();
        return ResponseEntity.ok(sell);
    }
}
