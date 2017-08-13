package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.Bittrex;
import io.iansoft.blockchain.service.dto.BittrexDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface BittrexMapper extends EntityMapper<BittrexDTO,Bittrex> {

    Bittrex toEntity(BittrexDTO dto);
    default Bittrex fromId(Long id){
        if (id == null) {
            return null;
        }
        Bittrex bittrex = new Bittrex();
        bittrex.setId(id);
        return bittrex;
    }
}
