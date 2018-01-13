package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.BitfinexDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bitfinex and its DTO BitfinexDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BitfinexMapper extends EntityMapper<BitfinexDTO, Bitfinex> {


    @Mapping(target = "coin", ignore = true)
    Bitfinex toEntity(BitfinexDTO bitfinexDTO);

    default Bitfinex fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bitfinex bitfinex = new Bitfinex();
        bitfinex.setId(id);
        return bitfinex;
    }
}
