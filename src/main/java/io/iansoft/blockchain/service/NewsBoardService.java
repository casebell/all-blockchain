package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.NewsBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NewsBoard.
 */
public interface NewsBoardService {

    /**
     * Save a newsBoard.
     *
     * @param newsBoardDTO the entity to save
     * @return the persisted entity
     */
    NewsBoardDTO save(NewsBoardDTO newsBoardDTO);

    /**
     *  Get all the newsBoards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NewsBoardDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" newsBoard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NewsBoardDTO findOne(Long id);

    /**
     *  Delete the "id" newsBoard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the newsBoard corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NewsBoardDTO> search(String query, Pageable pageable);
}
