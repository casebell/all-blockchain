package io.iansoft.blockchain.service.mapper.mapstruct;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.BitfinexDTO;

import io.iansoft.blockchain.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity Bitfinex and its DTO BitfinexDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BitfinexMastruct extends EntityMapper<BitfinexDTO, Bitfinex> {

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
