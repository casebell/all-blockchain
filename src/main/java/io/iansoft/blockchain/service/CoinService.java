package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.CoinDTO;
import java.util.List;

/**
 * Service Interface for managing Coin.
 */
public interface CoinService {

    /**
     * Save a coin.
     *
     * @param coinDTO the entity to save
     * @return the persisted entity
     */
    CoinDTO save(CoinDTO coinDTO);

    /**
     * Get all the coins.
     *
     * @return the list of entities
     */
    List<CoinDTO> findAll();

    /**
     * Get the "id" coin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CoinDTO findOne(Long id);

    /**
     * Delete the "id" coin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the coin corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CoinDTO> search(String query);
}
