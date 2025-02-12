package com.jgonmor.store.mapper;

import com.jgonmor.store.dto.ClientDto;
import com.jgonmor.store.dto.SellClientNameDto;
import com.jgonmor.store.dto.SellDetailDto;
import com.jgonmor.store.dto.SellDto;
import com.jgonmor.store.model.Client;
import com.jgonmor.store.model.Sell;
import com.jgonmor.store.model.SellDetail;

import java.util.List;

/**
 * Mapper Class
 * Maps objects to DTOs and vice versa.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
public class Mapper {

    /**
     * Maps a List of Sells to a List of SellsDto.
     *
     * @param sells The List of sells to be mapped.
     * @return A List of SellsDto.
     */
    static public List<SellDto> sellsToDtoList(List<Sell> sells) {
        return sells.stream()
                    .map(Mapper::sellToDto)
                    .toList();
    }

    /**
     * Maps a List of SellDetails to a List of SellDetailsDto.
     *
     * @param sellDetails The List of SellDetails to be mapped.
     * @return A List of SellDetailsDto.
     */
    static public List<SellDetailDto> sellDetailtoDtoList(List<SellDetail> sellDetails) {
        return sellDetails.stream()
                    .map(Mapper::sellDetailToDto)
                    .toList();
    }


    /**
     * Maps a SellDetails to a SellDetailsDto.
     *
     * @param sellDetail SellDetails to be mapped.
     * @return A SellDetails.
     */
    static public SellDetailDto sellDetailToDto(SellDetail sellDetail){
        return new SellDetailDto(sellDetail.getId(),
                                 sellDetail.getUnitPrice(),
                                 sellDetail.getQuantity(),
                                 sellDetail.getTotal(),
                                 sellDetail.getProduct());
    }

    /**
     * Maps a Sell to a SellDto.
     *
     * @param sell Sell to be mapped.
     * @return A SellDto.
     */
    static public SellDto sellToDto(Sell sell) {
        return new SellDto(sell.getId(),
                           sell.getDate(),
                           sell.getTotal(),
                           Mapper.clientToDto(sell.getClient()),
                           Mapper.sellDetailtoDtoList(sell.getSellDetails()));
    }

    /**
     * Maps a Client to a ClientDto.
     *
     * @param client Client to be mapped.
     * @return A ClientDto.
     */
    static public ClientDto clientToDto(Client client){
        return new ClientDto(client.getId(),
                             client.getName(),
                             client.getLastName(),
                             client.getCitizenId());
    }

    /**
     * Maps a List of Clients to a List of ClientsDto.
     *
     * @param clients The List of sells to be mapped.
     * @return A List of ClientsDto.
     */
   static public List<ClientDto> clientsToDtoList(List<Client> clients) {
        return clients.stream()
                      .map(Mapper::clientToDto)
                      .toList();
    }

    /**
     * Maps a ClientDto to a Client.
     *
     * @param client ClientDto to be mapped.
     * @return A Client.
     */
    static public Client clientDtoToEntity(ClientDto client) {
        return new Client(client.getId(),
                          client.getName(),
                          client.getLastName(),
                          client.getCitizenId());
    }

    /**
     * Maps a Sell to a SellClientNameDto.
     *
     * @param sell Sell to be mapped.
     * @return A SellClientNameDto.
     */
    public static SellClientNameDto sellToClientNameDto(Sell sell){
        return new SellClientNameDto( sell.getId(),
                                      sell.getTotal(),
                                      sell.getSellDetails(),
                                      sell.getClient().getName(),
                                      sell.getClient().getLastName());
    }

}
