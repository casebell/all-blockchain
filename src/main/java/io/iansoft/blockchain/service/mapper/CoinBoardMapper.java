package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.CoinBoardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CoinBoard and its DTO CoinBoardDTO.
 */
@Mapper(componentModel = "spring", uses = {CoinMapper.class, UserMapper.class})
public interface CoinBoardMapper extends EntityMapper<CoinBoardDTO, CoinBoard> {

    @Mapping(source = "coin.id", target = "coinId")
    @Mapping(source = "user.id", target = "userId")
    CoinBoardDTO toDto(CoinBoard coinBoard);

    @Mapping(source = "coinId", target = "coin")
    @Mapping(target = "coinBoardComments", ignore = true)
    @Mapping(source = "userId", target = "user")
    CoinBoard toEntity(CoinBoardDTO coinBoardDTO);

    default CoinBoard fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoinBoard coinBoard = new CoinBoard();
        coinBoard.setId(id);
        return coinBoard;
    }
}
