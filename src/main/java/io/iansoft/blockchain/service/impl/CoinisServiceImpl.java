package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.CoinisService;
import io.iansoft.blockchain.domain.Coinis;
import io.iansoft.blockchain.repository.CoinisRepository;
import io.iansoft.blockchain.repository.search.CoinisSearchRepository;
import io.iansoft.blockchain.service.dto.CoinisDTO;
import io.iansoft.blockchain.service.mapper.CoinisMapper;
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
 * Service Implementation for managing Coinis.
 */
@Service
@Transactional
public class CoinisServiceImpl implements CoinisService{

    private final Logger log = LoggerFactory.getLogger(CoinisServiceImpl.class);

    private final CoinisRepository coinisRepository;

    private final CoinisMapper coinisMapper;

    private final CoinisSearchRepository coinisSearchRepository;

    public CoinisServiceImpl(CoinisRepository coinisRepository, CoinisMapper coinisMapper, CoinisSearchRepository coinisSearchRepository) {
        this.coinisRepository = coinisRepository;
        this.coinisMapper = coinisMapper;
        this.coinisSearchRepository = coinisSearchRepository;
    }

    /**
     * Save a coinis.
     *
     * @param coinisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoinisDTO save(CoinisDTO coinisDTO) {
        log.debug("Request to save Coinis : {}", coinisDTO);
        Coinis coinis = coinisMapper.toEntity(coinisDTO);
        coinis = coinisRepository.save(coinis);
        CoinisDTO result = coinisMapper.toDto(coinis);
        coinisSearchRepository.save(coinis);
        return result;
    }

    /**
     * Get all the coinis.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoinisDTO> findAll() {
        log.debug("Request to get all Coinis");
        return coinisRepository.findAll().stream()
            .map(coinisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one coinis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoinisDTO findOne(Long id) {
        log.debug("Request to get Coinis : {}", id);
        Coinis coinis = coinisRepository.findOne(id);
        return coinisMapper.toDto(coinis);
    }

    /**
     * Delete the coinis by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coinis : {}", id);
        coinisRepository.delete(id);
        coinisSearchRepository.delete(id);
    }

    /**
     * Search for the coinis corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoinisDTO> search(String query) {
        log.debug("Request to search Coinis for query {}", query);
        return StreamSupport
            .stream(coinisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(coinisMapper::toDto)
            .collect(Collectors.toList());
    }
}
