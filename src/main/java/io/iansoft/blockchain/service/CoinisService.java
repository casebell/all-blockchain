package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.CoinisDTO;
import java.util.List;

/**
 * Service Interface for managing Coinis.
 */
public interface CoinisService {

    /**
     * Save a coinis.
     *
     * @param coinisDTO the entity to save
     * @return the persisted entity
     */
    CoinisDTO save(CoinisDTO coinisDTO);

    /**
     * Get all the coinis.
     *
     * @return the list of entities
     */
    List<CoinisDTO> findAll();

    /**
     * Get the "id" coinis.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CoinisDTO findOne(Long id);

    /**
     * Delete the "id" coinis.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the coinis corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CoinisDTO> search(String query);
}
