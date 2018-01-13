package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.KrakenService;
import io.iansoft.blockchain.domain.Kraken;
import io.iansoft.blockchain.repository.KrakenRepository;
import io.iansoft.blockchain.repository.search.KrakenSearchRepository;
import io.iansoft.blockchain.service.dto.KrakenDTO;
import io.iansoft.blockchain.service.mapper.KrakenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Kraken.
 */
@Service
@Transactional
public class KrakenServiceImpl implements KrakenService {

    private final Logger log = LoggerFactory.getLogger(KrakenServiceImpl.class);

    private final KrakenRepository krakenRepository;

    private final KrakenMapper krakenMapper;

    private final KrakenSearchRepository krakenSearchRepository;

    public KrakenServiceImpl(KrakenRepository krakenRepository, KrakenMapper krakenMapper, KrakenSearchRepository krakenSearchRepository) {
        this.krakenRepository = krakenRepository;
        this.krakenMapper = krakenMapper;
        this.krakenSearchRepository = krakenSearchRepository;
    }

    /**
     * Save a kraken.
     *
     * @param krakenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KrakenDTO save(KrakenDTO krakenDTO) {
        log.debug("Request to save Kraken : {}", krakenDTO);
        Kraken kraken = krakenMapper.toEntity(krakenDTO);
        kraken = krakenRepository.save(kraken);
        KrakenDTO result = krakenMapper.toDto(kraken);
        krakenSearchRepository.save(kraken);
        return result;
    }

    /**
     * Get all the krakens.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KrakenDTO> findAll() {
        log.debug("Request to get all Krakens");
        return krakenRepository.findAll().stream()
            .map(krakenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one kraken by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KrakenDTO findOne(Long id) {
        log.debug("Request to get Kraken : {}", id);
        Kraken kraken = krakenRepository.findOne(id);
        return krakenMapper.toDto(kraken);
    }

    /**
     * Delete the kraken by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kraken : {}", id);
        krakenRepository.delete(id);
        krakenSearchRepository.delete(id);
    }

    /**
     * Search for the kraken corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KrakenDTO> search(String query) {
        log.debug("Request to search Krakens for query {}", query);
        return StreamSupport
            .stream(krakenSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(krakenMapper::toDto)
            .collect(Collectors.toList());
    }
}
