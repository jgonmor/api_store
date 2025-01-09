package com.jgonmor.store.service.sell;

import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ISellService {

    List<SellDto> getAllSells();

    Sell getSellById(Long id);

    Boolean deleteSell(Long id);

    Sell saveSell(Sell sell);

    Sell updateSell(Sell sell);

    List<Product> getProductsFromSell(Long sellId);

    Double getTotalFromSellsOnDay(LocalDate date);

    SellClientNameDto getSellWithClientName();

}
