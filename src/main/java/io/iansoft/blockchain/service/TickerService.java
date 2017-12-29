package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.MarketCoinDTO;
import io.iansoft.blockchain.service.dto.TickerDTO;

import java.util.List;

/**
 * Service Interface for managing Ticker.
 */
public interface TickerService {

    /**
     * Save a market.
     *
     * @param tickerDTO the entity to save
     * @return the persisted entity
     */
    TickerDTO save(TickerDTO tickerDTO);

    /**
     * Get all the markets.
     *
     * @return the list of entities
     */
    List<TickerDTO> findAll();

    /**
     * Get the "id" market.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TickerDTO findOne(Long id);

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
    List<TickerDTO> search(String query);

    List<TickerDTO> saveTickers(Long userId,List<MarketCoinDTO> tickerDTOs);

    List<TickerDTO> findByUserId(Long userId);
}
