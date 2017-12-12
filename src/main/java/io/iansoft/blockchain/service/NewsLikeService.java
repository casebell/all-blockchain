package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.NewsLikeDTO;
import java.util.List;

/**
 * Service Interface for managing NewsLike.
 */
public interface NewsLikeService {

    /**
     * Save a newsLike.
     *
     * @param newsLikeDTO the entity to save
     * @return the persisted entity
     */
    NewsLikeDTO save(NewsLikeDTO newsLikeDTO);

    /**
     *  Get all the newsLikes.
     *
     *  @return the list of entities
     */
    List<NewsLikeDTO> findAll();

    /**
     *  Get the "id" newsLike.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NewsLikeDTO findOne(Long id);

    /**
     *  Delete the "id" newsLike.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the newsLike corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<NewsLikeDTO> search(String query);
}
