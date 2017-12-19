package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.domain.MarketCoin;
import io.iansoft.blockchain.repository.MarketCoinRepository;
import io.iansoft.blockchain.repository.search.MarketCoinSearchRepository;
import io.iansoft.blockchain.service.MarketCoinService;
import io.iansoft.blockchain.service.dto.MarketCoinDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing MarketCoin.
 */
@Service
@Transactional
public class MarketCoinServiceImpl implements MarketCoinService {

    private final Logger log = LoggerFactory.getLogger(MarketCoinServiceImpl.class);

    private final MarketCoinRepository marketCoinRepository;

//    private final MarketMapper marketMapper;
    private final ModelMapper modelMapper;
    private final MarketCoinSearchRepository marketCoinSearchRepository;

    public MarketCoinServiceImpl(MarketCoinRepository marketCoinRepository, ModelMapper modelMapper, MarketCoinSearchRepository marketCoinSearchRepository) {
        this.marketCoinRepository = marketCoinRepository;
        this.modelMapper = modelMapper;
        this.marketCoinSearchRepository = marketCoinSearchRepository;
    }

    /**
     * Save a market.
     *
     * @param marketCoinDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MarketCoinDTO save(MarketCoinDTO marketCoinDTO) {
        log.debug("Request to save MarketCoin : {}", marketCoinDTO);
        MarketCoin marketCoin = modelMapper.map(marketCoinDTO, MarketCoin.class);
        marketCoin = marketCoinRepository.save(marketCoin);
        MarketCoinDTO result = modelMapper.map(marketCoin, MarketCoinDTO.class);
        marketCoinSearchRepository.save(marketCoin);
        return result;
    }

    /**
     * Get all the marketCoins.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MarketCoinDTO> findAll() {
        log.debug("Request to get all MarketCoins");
        return marketCoinRepository.findAll().stream()
            .map( x -> modelMapper.map(x,MarketCoinDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one marketCoin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MarketCoinDTO findOne(Long id) {
        log.debug("Request to get MarketCoin : {}", id);
        MarketCoin marketCoin = marketCoinRepository.findOne(id);
        return modelMapper.map(marketCoin, MarketCoinDTO.class);
    }

    /**
     * Delete the marketCoin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketCoin : {}", id);
        marketCoinRepository.delete(id);
        marketCoinSearchRepository.delete(id);
    }

    /**
     * Search for the marketCoin corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MarketCoinDTO> search(String query) {
        log.debug("Request to search Markets for query {}", query);
        return StreamSupport
            .stream(marketCoinSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(x->modelMapper.map(x,MarketCoinDTO.class))
            .collect(Collectors.toList());
    }
}
