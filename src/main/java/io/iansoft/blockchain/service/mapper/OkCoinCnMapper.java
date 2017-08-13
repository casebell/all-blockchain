package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.OkCoinCn;
import io.iansoft.blockchain.service.dto.OkCoinCnDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface OkCoinCnMapper extends EntityMapper<OkCoinCnDTO,OkCoinCn> {

    OkCoinCn toEntity(OkCoinCnDTO dto);
    default OkCoinCn fromId(Long id){
        if (id == null) {
            return null;
        }
        OkCoinCn okCoinCn = new OkCoinCn();
        okCoinCn.setId(id);
        return okCoinCn;
    }
}
