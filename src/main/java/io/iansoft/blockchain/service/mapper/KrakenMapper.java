package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.KrakenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Kraken and its DTO KrakenDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KrakenMapper extends EntityMapper <KrakenDTO, Kraken> {
    
    
    default Kraken fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kraken kraken = new Kraken();
        kraken.setId(id);
        return kraken;
    }
}
