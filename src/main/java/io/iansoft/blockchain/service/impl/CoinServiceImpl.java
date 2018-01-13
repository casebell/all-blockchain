package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.CoinService;
import io.iansoft.blockchain.domain.Coin;
import io.iansoft.blockchain.repository.CoinRepository;
import io.iansoft.blockchain.repository.search.CoinSearchRepository;
import io.iansoft.blockchain.service.dto.CoinDTO;
import io.iansoft.blockchain.service.mapper.CoinMapper;
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
 * Service Implementation for managing Coin.
 */
@Service
@Transactional
public class CoinServiceImpl implements CoinService {

    private final Logger log = LoggerFactory.getLogger(CoinServiceImpl.class);

    private final CoinRepository coinRepository;

    private final CoinMapper coinMapper;

    private final CoinSearchRepository coinSearchRepository;

    public CoinServiceImpl(CoinRepository coinRepository, CoinMapper coinMapper, CoinSearchRepository coinSearchRepository) {
        this.coinRepository = coinRepository;
        this.coinMapper = coinMapper;
        this.coinSearchRepository = coinSearchRepository;
    }

    /**
     * Save a coin.
     *
     * @param coinDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoinDTO save(CoinDTO coinDTO) {
        log.debug("Request to save Coin : {}", coinDTO);
        Coin coin = coinMapper.toEntity(coinDTO);
        coin = coinRepository.save(coin);
        CoinDTO result = coinMapper.toDto(coin);
        coinSearchRepository.save(coin);
        return result;
    }

    /**
     * Get all the coins.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoinDTO> findAll() {
        log.debug("Request to get all Coins");
        return coinRepository.findAll().stream()
            .map(coinMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one coin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoinDTO findOne(Long id) {
        log.debug("Request to get Coin : {}", id);
        Coin coin = coinRepository.findOne(id);
        return coinMapper.toDto(coin);
    }

    /**
     * Delete the coin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coin : {}", id);
        coinRepository.delete(id);
        coinSearchRepository.delete(id);
    }

    /**
     * Search for the coin corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoinDTO> search(String query) {
        log.debug("Request to search Coins for query {}", query);
        return StreamSupport
            .stream(coinSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(coinMapper::toDto)
            .collect(Collectors.toList());
    }
}
