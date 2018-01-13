package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.CoinBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CoinBoard.
 */
public interface CoinBoardService {

    /**
     * Save a coinBoard.
     *
     * @param coinBoardDTO the entity to save
     * @return the persisted entity
     */
    CoinBoardDTO save(CoinBoardDTO coinBoardDTO);

    /**
     * Get all the coinBoards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CoinBoardDTO> findAll(Pageable pageable);

    /**
     * Get the "id" coinBoard.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CoinBoardDTO findOne(Long id);

    /**
     * Delete the "id" coinBoard.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the coinBoard corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CoinBoardDTO> search(String query, Pageable pageable);
}
