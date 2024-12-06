package com.jgonmor.store.service.sell;

import com.jgonmor.store.model.Sell;
import com.jgonmor.store.repository.ISellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellService implements ISellService{

    @Autowired
    ISellRepository sellRepository;

    @Override
    public List<Sell> getAllSells() {
        return sellRepository.findAll();
    }

    @Override
    public Sell getSellById(Long id) {
        return sellRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteSell(Long id) {

        if (this.getSellById(id) == null)
        {
            return false;
        }

        sellRepository.deleteById(id);

        return true;
    }

    @Override
    public Sell saveSell(Sell sell) {
        return sellRepository.save(sell);
    }

    @Override
    public Sell updateSell(Sell sell) {
        return this.saveSell(sell);
    }
}
