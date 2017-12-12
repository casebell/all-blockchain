package io.iansoft.blockchain.service.mapper;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.service.dto.NewsLikeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NewsLike and its DTO NewsLikeDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, NewsBoardMapper.class})
public interface NewsLikeMapper extends EntityMapper<NewsLikeDTO, NewsLike> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "newsBoard.id", target = "newsBoardId")
    NewsLikeDTO toDto(NewsLike newsLike); 

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "newsBoardId", target = "newsBoard")
    NewsLike toEntity(NewsLikeDTO newsLikeDTO);

    default NewsLike fromId(Long id) {
        if (id == null) {
            return null;
        }
        NewsLike newsLike = new NewsLike();
        newsLike.setId(id);
        return newsLike;
    }
}
