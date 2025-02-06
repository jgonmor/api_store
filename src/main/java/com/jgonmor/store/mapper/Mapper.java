package com.jgonmor.store.mapper;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.dto.SellDetailDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.model.SellDetail;

import java.util.List;

public class Mapper {

    static public List<SellDto> sellsToDtoList(List<Sell> sells) {
        return sells.stream()
                    .map(Mapper::sellToDto)
                    .toList();
    }

    static public List<SellDetailDto> sellDetailtoDtoList(List<SellDetail> sellDetails) {
        return sellDetails.stream()
                    .map(Mapper::sellDetailToDto)
                    .toList();
    }


    static public SellDetailDto sellDetailToDto(SellDetail sellDetail){
        return new SellDetailDto(sellDetail.getId(),
                                 sellDetail.getUnitPrice(),
                                 sellDetail.getQuantity(),
                                 sellDetail.getTotal(),
                                 sellDetail.getProduct());
    }

    static public SellDto sellToDto(Sell sell) {
        return new SellDto(sell.getId(),
                           sell.getDate(),
                           sell.getTotal(),
                           ClientDto.fromEntity(sell.getClient()),
                           Mapper.sellDetailtoDtoList(sell.getSellDetails()));
    }

    static public ClientDto ClientToDto(Client client){
        return new ClientDto(client.getId(),
                             client.getName(),
                             client.getLastName(),
                             client.getCitizenId());
    }
}
