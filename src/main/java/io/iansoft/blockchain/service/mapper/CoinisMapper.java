package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.CoinisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coinis and its DTO CoinisDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoinisMapper extends EntityMapper <CoinisDTO, Coinis> {
    
    
    default Coinis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coinis coinis = new Coinis();
        coinis.setId(id);
        return coinis;
    }
}
