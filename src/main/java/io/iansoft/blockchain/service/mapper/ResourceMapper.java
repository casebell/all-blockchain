package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.ResourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Resource and its DTO ResourceDTO.
 */
@Mapper(componentModel = "spring", uses = {CoinMapper.class})
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {

    @Mapping(source = "coin.id", target = "coinId")
    ResourceDTO toDto(Resource resource); 

    @Mapping(source = "coinId", target = "coin")
    Resource toEntity(ResourceDTO resourceDTO);

    default Resource fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resource resource = new Resource();
        resource.setId(id);
        return resource;
    }
}
