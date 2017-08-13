package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.Bitflyer;
import io.iansoft.blockchain.service.dto.BitflyerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface BitflyerMapper extends EntityMapper<BitflyerDTO,Bitflyer> {

    Bitflyer toEntity(BitflyerDTO dto);
    default Bitflyer fromId(Long id){
        if (id == null) {
            return null;
        }
        Bitflyer bitflyer = new Bitflyer();
        bitflyer.setId(id);
        return bitflyer;
    }
}
