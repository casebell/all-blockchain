package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.CoinDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coin and its DTO CoinDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoinMapper extends EntityMapper<CoinDTO, Coin> {

    

    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "boards", ignore = true)
    Coin toEntity(CoinDTO coinDTO);

    default Coin fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coin coin = new Coin();
        coin.setId(id);
        return coin;
    }
}
