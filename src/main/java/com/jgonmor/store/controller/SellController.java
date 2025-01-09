package com.jgonmor.store.controller;

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
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sells")
public class SellController {

    @Autowired
    private ISellService sellService;

    @GetMapping("/")
    public ResponseEntity<?> getAllSells() {
        List<SellDto> sells = sellService.getAllSells();
        return ResponseEntity.ok(sells);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSellById(@PathVariable Long id) {
        Sell sell = sellService.getSellById(id);
        if (sell == null) {
            return ResponseEntity.status(404)
                                 .body("Sell not found");
        }
        return ResponseEntity.ok(sell);
    }

    @PostMapping("/new")
    public ResponseEntity<?> saveSell(@RequestBody Sell sell) {
        Sell newSell = sellService.saveSell(sell);
        return ResponseEntity.ok(newSell);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSell(@RequestBody Sell sell) {
        Sell updatedSell = sellService.updateSell(sell);
        return ResponseEntity.ok(updatedSell);
    }

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

    @GetMapping("/products/{sellId}")
    public ResponseEntity<?> getSellProducts(@PathVariable Long sellId) {
        List<Product> products = sellService.getProductsFromSell(sellId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/total/{date}")
    public ResponseEntity<?> getTotalFromSellsOnDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Double total = sellService.getTotalFromSellsOnDay(date);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/biggest-sell")
    public ResponseEntity<?> getBiggestSell(){
        SellClientNameDto sell = sellService.getSellWithClientName();
        return ResponseEntity.ok(sell);
    }
}
