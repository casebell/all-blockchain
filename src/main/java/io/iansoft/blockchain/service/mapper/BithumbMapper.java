package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.Bithumb;
import io.iansoft.blockchain.service.dto.BithumbDataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface BithumbMapper extends EntityMapper <BithumbDataDTO, Bithumb>{


    Bithumb toEntity(BithumbDataDTO dto);
    default Bithumb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bithumb bithumb = new Bithumb();
        bithumb.setId(id);
        return bithumb;
    }
}

