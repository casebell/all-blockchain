package io.iansoft.blockchain.service;

import io.iansoft.blockchain.service.dto.KrakenDTO;
import java.util.List;

/**
 * Service Interface for managing Kraken.
 */
public interface KrakenService {

    /**
     * Save a kraken.
     *
     * @param krakenDTO the entity to save
     * @return the persisted entity
     */
    KrakenDTO save(KrakenDTO krakenDTO);

    /**
     * Get all the krakens.
     *
     * @return the list of entities
     */
    List<KrakenDTO> findAll();

    /**
     * Get the "id" kraken.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KrakenDTO findOne(Long id);

    /**
     * Delete the "id" kraken.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the kraken corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<KrakenDTO> search(String query);
}
