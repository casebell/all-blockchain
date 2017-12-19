package io.iansoft.blockchain.service;


import io.iansoft.blockchain.service.dto.MarketCoinDTO;

import java.util.List;

/**
 * Service Interface for managing Market Coin.
 */
public interface MarketCoinService {

    /**
     * Save a market.
     *
     * @param marketCoinDTO the entity to save
     * @return the persisted entity
     */
    MarketCoinDTO save(MarketCoinDTO marketCoinDTO);

    /**
     * Get all the markets.
     *
     * @return the list of entities
     */
    List<MarketCoinDTO> findAll();

    /**
     * Get the "id" market.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MarketCoinDTO findOne(Long id);

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
    List<MarketCoinDTO> search(String query);
}
