package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.MarketDTO;
import java.util.List;

/**
 * Service Interface for managing Market.
 */
public interface MarketService {

    /**
     * Save a market.
     *
     * @param marketDTO the entity to save
     * @return the persisted entity
     */
    MarketDTO save(MarketDTO marketDTO);

    /**
     * Get all the markets.
     *
     * @return the list of entities
     */
    List<MarketDTO> findAll();

    /**
     * Get the "id" market.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MarketDTO findOne(Long id);

    /**
     * Delete the "id" market.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the market corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<MarketDTO> search(String query);
}
