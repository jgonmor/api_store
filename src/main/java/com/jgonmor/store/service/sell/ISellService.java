package com.jgonmor.store.service.sell;

import com.jgonmor.store.model.Sell;

import java.util.List;

public interface ISellService {

    List<Sell> getAllSells();

    Sell getSellById(Long id);

    Boolean deleteSell(Long id);

    Sell saveSell(Sell sell);

    Sell updateSell(Sell sell);
}
