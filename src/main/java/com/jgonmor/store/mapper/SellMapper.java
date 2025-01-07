package com.jgonmor.store.mapper;

import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.model.Sell;

import java.util.List;

public class SellMapper {
    public List<SellDto> toDtoList(List<Sell> sells) {
        return sells.stream()
                    .map(SellDto::fromEntity)
                    .toList();
    }
}
