package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.CoinBoardCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CoinBoardComment.
 */
public interface CoinBoardCommentService {

    /**
     * Save a coinBoardComment.
     *
     * @param coinBoardCommentDTO the entity to save
     * @return the persisted entity
     */
    CoinBoardCommentDTO save(CoinBoardCommentDTO coinBoardCommentDTO);

    /**
     * Get all the coinBoardComments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CoinBoardCommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" coinBoardComment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CoinBoardCommentDTO findOne(Long id);

    /**
     * Delete the "id" coinBoardComment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the coinBoardComment corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CoinBoardCommentDTO> search(String query, Pageable pageable);
}
