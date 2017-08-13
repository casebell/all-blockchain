package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.Korbit;
import io.iansoft.blockchain.service.dto.KorbitDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface KorbitMapper extends EntityMapper<KorbitDTO,Korbit> {

    Korbit toEntity(KorbitDTO dto);
    default Korbit fromId(Long id){
        if (id == null) {
            return null;
        }
        Korbit korbit = new Korbit();
        korbit.setId(id);
        return korbit;
    }
}
