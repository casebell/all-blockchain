package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.NewsBoardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NewsBoard and its DTO NewsBoardDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NewsBoardMapper extends EntityMapper<NewsBoardDTO, NewsBoard> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    NewsBoardDTO toDto(NewsBoard newsBoard); 

    @Mapping(source = "userId", target = "user")
    NewsBoard toEntity(NewsBoardDTO newsBoardDTO);

    default NewsBoard fromId(Long id) {
        if (id == null) {
            return null;
        }
        NewsBoard newsBoard = new NewsBoard();
        newsBoard.setId(id);
        return newsBoard;
    }
}
