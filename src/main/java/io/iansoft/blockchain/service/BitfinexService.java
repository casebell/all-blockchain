package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.BitfinexDTO;
import java.util.List;

/**
 * Service Interface for managing Bitfinex.
 */
public interface BitfinexService {

    /**
     * Save a bitfinex.
     *
     * @param bitfinexDTO the entity to save
     * @return the persisted entity
     */
    BitfinexDTO save(BitfinexDTO bitfinexDTO);

    /**
     *  Get all the bitfinexes.
     *
     *  @return the list of entities
     */
    List<BitfinexDTO> findAll();
    /**
     *  Get all the BitfinexDTO where Coin is null.
     *
     *  @return the list of entities
     */
    List<BitfinexDTO> findAllWhereCoinIsNull();

    /**
     *  Get the "id" bitfinex.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BitfinexDTO findOne(Long id);

    /**
     *  Delete the "id" bitfinex.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bitfinex corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<BitfinexDTO> search(String query);

    List<BitfinexDTO> getBitfinex();
}
