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
import com.jgonmor.store.repository.ISellDetailRepository;
import com.jgonmor.store.repository.ISellRepository;
import com.jgonmor.store.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Sell Service Class
 * implements methods to manage sells.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Service
public class SellService implements ISellService {

    @Autowired
    ISellRepository sellRepository;

    @Autowired
    ISellDetailRepository sellDetailRepository;

    @Autowired
    private IProductService productService;

    /**
     * Checks if the sell exists.
     *
     * @param id The id of the sell to be checked.
     */
    public void existOrException(Long id) {
        boolean exists = sellRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Sell not found");
        }
    }

    /**
     * Fixes the stock of the sell.
     *
     * @param sell The sell to be fixed.
     */
    private void fixStock(Sell sell) {

        List<Product> productList = new ArrayList<>();

        sell.getSellDetails()
            .forEach(sellDetail -> {

                Product product = productService.getProductById(sellDetail.getProduct()
                                                                          .getId());
                if (product.getStock() < sellDetail.getQuantity()) {
                    throw new NotEnoughStockException("Not enough stock for product " + product.getName()
                                                              + " (id " + product.getId() + ")");
                }

                product.setStock(product.getStock() - sellDetail.getQuantity());
                productList.add(product);
            });

        productService.saveProducts(productList);

    }

    /**
     * Fixes the stock of the sell when it is updated.
     *
     * @param sell The sell to be fixed.
     */
    private void fixStockWhenUpdate(Sell sell) {

        List<Product> productList = new ArrayList<>();

        HashMap<Long, SellDetail> oldDetails = new HashMap<>();
        sell.getSellDetails()
            .forEach(sellDetail ->
                             sellDetailRepository.findBySellAndProduct(sell, sellDetail.getProduct())
                                                 .ifPresent(oldDetail -> oldDetails.put(sellDetail.getProduct().getId(), oldDetail))
            );

        sell.getSellDetails()
            .forEach(sellDetail -> {
                SellDetail oldDetail = oldDetails.get(sellDetail.getProduct().getId());

                int quantity = sellDetail.getQuantity() - oldDetail.getQuantity() ;

                Product product = productService.getProductById(sellDetail.getProduct()
                                                                          .getId());
                if (product.getStock() < quantity) {
                    throw new NotEnoughStockException("Not enough stock for product " + product.getName()
                                                              + " (id " + product.getId() + ")");
                }

                product.setStock(product.getStock() - quantity);
                productList.add(product);
                sellDetail.setId(oldDetail.getId());

                sellDetailRepository.save(sellDetail);
            });

        productService.saveProducts(productList);

    }


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
                if (sellDetail.getUnitPrice() == 0 && sellDetail.getProduct()
                                                                .getPrice() != null) {
                    sellDetail.setUnitPrice(sellDetail.getProduct()
                                                      .getPrice());
                } else if (sellDetail.getUnitPrice() == 0) {
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
        Sell result;

        if (sell.getSellDetails()
                .isEmpty()) {
            throw new RequiredParamNotFoundException("Sell must have products");
        }

        this.fixStockWhenUpdate(sell);

        sell.getSellDetails()
            .forEach(sellDetail -> {
                if (sellDetail.getUnitPrice() == 0 && sellDetail.getProduct()
                                                                .getPrice() != null) {
                    sellDetail.setUnitPrice(sellDetail.getProduct()
                                                      .getPrice());
                } else if (sellDetail.getUnitPrice() == 0) {
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

        return sellRepository.getTotalFromSellsInAPeriod(start,
                                                     end);
    }

    @Override
    public SellClientNameDto getBiggestSellWithClientName() {

        Sell sell = sellRepository.findBiggestSell();

        return Mapper.sellToClientNameDto(sell);
    }

    @Override
    public List<SellDto> saveSells(List<Sell> sells) {

        List<SellDto> sellDtos;

        sells.forEach(this::fixStock);

        sells.forEach(sell -> {
            double total = sell.getSellDetails()
                               .stream()
                               .mapToDouble(SellDetail::getTotal)
                               .sum();

            sell.setTotal(total);
        });


        sellRepository.saveAll(sells);

        sellDtos = Mapper.sellsToDtoList(sells);

        return sellDtos;
    }

    @Override
    public SellDto removeProductFromSell(Long sellId,
                                         Long productId) {

        Sell sell = sellRepository.findById(sellId)
                                  .orElseThrow(() -> new ResourceNotFoundException("Sell not found"));

        Product product = productService.getProductById(productId);

        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }

       SellDetail detailsToRemove = sellDetailRepository.findBySellAndProduct(sell, product)
                                                        .orElseThrow(() -> new ResourceNotFoundException("Product not found on sell"));

        sellDetailRepository.delete(detailsToRemove);

        product.setStock(product.getStock() + detailsToRemove.getQuantity());

        productService.saveProduct(product);

        double total = sell.getSellDetails()
                           .stream()
                           .mapToDouble(SellDetail::getTotal)
                           .sum();

        sell.setTotal(total);

        sellRepository.save(sell);

        return Mapper.sellToDto(sell);
    }


}
