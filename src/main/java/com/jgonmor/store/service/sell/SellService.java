package com.jgonmor.store.service.sell;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.exceptions.EmptyTableException;
import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.model.Client;
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

        List<Sell> sells = sellRepository.findAll();

        if(sells.isEmpty()){
            throw new EmptyTableException("There are no sells");
        }
        return sells;
    }

    @Override
    public Sell getSellById(Long id) {
        return sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sell not found"));
    }

    @Override
    public Boolean deleteSell(Long id) {

        this.existOrException(id);

        sellRepository.deleteById(id);

        return true;
    }

    @Override
    public Sell saveSell(Sell sell) {
        return sellRepository.save(sell);
    }

    @Override
    public Sell updateSell(Sell sell) {
        this.existOrException(sell.getId());
        return this.saveSell(sell);
    }

    private void existOrException(Long id){
        boolean exists = sellRepository.existsById(id);
        if(!exists){
            throw new ResourceNotFoundException("Sell not found");
        }
    }

    public List<SellDto> toDtoList(List<Sell> sells) {
        return sells.stream()
                      .map(SellDto::fromEntity)
                      .toList();
    }
}
