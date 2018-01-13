package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.CoinBoardCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CoinBoardComment and its DTO CoinBoardCommentDTO.
 */
@Mapper(componentModel = "spring", uses = {CoinBoardMapper.class, UserMapper.class})
public interface CoinBoardCommentMapper extends EntityMapper<CoinBoardCommentDTO, CoinBoardComment> {

    @Mapping(source = "coinBoard.id", target = "coinBoardId")
    @Mapping(source = "user.id", target = "userId")
    CoinBoardCommentDTO toDto(CoinBoardComment coinBoardComment);

    @Mapping(source = "coinBoardId", target = "coinBoard")
    @Mapping(source = "userId", target = "user")
    CoinBoardComment toEntity(CoinBoardCommentDTO coinBoardCommentDTO);

    default CoinBoardComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoinBoardComment coinBoardComment = new CoinBoardComment();
        coinBoardComment.setId(id);
        return coinBoardComment;
    }
}
