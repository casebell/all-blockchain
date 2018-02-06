package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.MarketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Market and its DTO MarketDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarketMapper extends EntityMapper<MarketDTO, Market> {



    @Mapping(target = "marketCoins", ignore = true)
    Market toEntity(MarketDTO marketDTO);

    default Market fromId(Long id) {
        if (id == null) {
            return null;
        }
        Market market = new Market();
        market.setId(id);
        return market;
    }
}
