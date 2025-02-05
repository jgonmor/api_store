package com.jgonmor.store.service.sell;

import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.exceptions.EmptyTableException;
import com.jgonmor.store.exceptions.NotEnoughStockException;
import com.jgonmor.store.exceptions.RequiredParamNotFoundException;
import com.jgonmor.store.exceptions.ResourceNotFoundException;
import com.jgonmor.store.mapper.Mapper;
import com.jgonmor.store.model.Product;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.model.SellDetail;
import com.jgonmor.store.repository.ISellRepository;
import com.jgonmor.store.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SellService implements ISellService {

    @Autowired
    ISellRepository sellRepository;

    @Autowired
    private IProductService productService;


    @Override
    public List<SellDto> getAllSells() {

        List<SellDto> sells = sellRepository.findAll()
                                            .stream()
                                            .map(Mapper::sellToDto)
                                            .toList();

        if (sells.isEmpty()) {
            throw new EmptyTableException("There are no sells");
        }
        return sells;
    }

    @Override
    public SellDto getSellById(Long id) {

        Sell sell = sellRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("Sell not found"));
        return Mapper.sellToDto(sell);
    }

    @Override
    public Boolean deleteSell(Long id) {

        this.existOrException(id);

        sellRepository.deleteById(id);

        return true;
    }

    @Override
    public SellDto saveSell(Sell sell) {

        Sell result;

        if (sell.getSellDetails()
                .isEmpty()) {
            throw new RequiredParamNotFoundException("Sell must have products");
        }

        this.fixStock(sell);

        sell.getSellDetails()
            .forEach(sellDetail -> {
                if (sellDetail.getUnitPrice() == 0 && sellDetail.getProduct().getPrice() != null) {
                    sellDetail.setUnitPrice(sellDetail.getProduct().getPrice());
                }
                else if(sellDetail.getUnitPrice() == 0) {
                    throw new RequiredParamNotFoundException("Product or unit price must be provided");
                }
                sellDetail.setTotal(sellDetail.getQuantity() * sellDetail.getUnitPrice());

                sellDetail.setSell(sell);
            });


        double total = sell.getSellDetails()
                           .stream()
                           .mapToDouble(SellDetail::getTotal)
                           .sum();

        sell.setTotal(total);


        result = sellRepository.save(sell);


        return Mapper.sellToDto(result);
    }

    @Override
    public SellDto updateSell(Sell sell) {
        this.existOrException(sell.getId());

        return this.saveSell(sell);
    }

    @Override
    public List<Product> getProductsFromSell(Long sellId) {
        this.existOrException(sellId);
        return sellRepository.findProductsBySellId(sellId);
    }

    @Override
    public Double getTotalFromSellsOnDay(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23,
                                        59,
                                        59,
                                        999999999);

        return sellRepository.getTotalFromSellsOnDay(start,
                                                     end);
    }

    @Override
    public SellClientNameDto getSellWithClientName() {

        Sell sell = sellRepository.findBiggestSell();

        return SellClientNameDto.fromEntity(sell);
    }

    @Override
    public List<SellDto> saveSells(List<Sell> sells) {

        List<SellDto> sellDtos;

        sells.forEach(this::fixStock);

        sellRepository.saveAll(sells);

        sellDtos = Mapper.sellsToDtoList(sells);

        return sellDtos;
    }


    public void existOrException(Long id) {
        boolean exists = sellRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Sell not found");
        }
    }

    private void fixStock(Sell sell){

        List<Product> productList = new ArrayList<>();

        sell.getSellDetails()
            .forEach(sellDetail -> {

                Product product = productService.getProductById(sellDetail.getProduct()
                                                                          .getId());
                if(product.getStock() < sellDetail.getQuantity() ){
                    throw new NotEnoughStockException("Not enough stock for product " + product.getName()
                                                              + " (id " + product.getId() + ")");
                }

                product.setStock(product.getStock() - sellDetail.getQuantity());
                productList.add(product);
            });

        productService.saveProducts(productList);

    }


}
