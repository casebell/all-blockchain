package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.MarketService;
import io.iansoft.blockchain.domain.Market;
import io.iansoft.blockchain.repository.MarketRepository;
import io.iansoft.blockchain.repository.search.MarketSearchRepository;
import io.iansoft.blockchain.service.dto.MarketDTO;
import org.modelmapper.ModelMapper;
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
 * Service Implementation for managing Market.
 */
@Service
@Transactional
public class MarketServiceImpl implements MarketService{

    private final Logger log = LoggerFactory.getLogger(MarketServiceImpl.class);

    private final MarketRepository marketRepository;

//    private final MarketMapper marketMapper;
    private final ModelMapper modelMapper;
    private final MarketSearchRepository marketSearchRepository;

    public MarketServiceImpl(MarketRepository marketRepository,  ModelMapper modelMapper, MarketSearchRepository marketSearchRepository) {
        this.marketRepository = marketRepository;
        this.modelMapper = modelMapper;
        this.marketSearchRepository = marketSearchRepository;
    }

    /**
     * Save a market.
     *
     * @param marketDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MarketDTO save(MarketDTO marketDTO) {
        log.debug("Request to save Market : {}", marketDTO);
        Market market = modelMapper.map(marketDTO, Market.class);
        market = marketRepository.save(market);
        MarketDTO result = modelMapper.map(market, MarketDTO.class);
        marketSearchRepository.save(market);
        return result;
    }

    /**
     * Get all the markets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MarketDTO> findAll() {
        log.debug("Request to get all Markets");
        return marketRepository.findAll().stream()
            .map( x -> modelMapper.map(x,MarketDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one market by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MarketDTO findOne(Long id) {
        log.debug("Request to get Market : {}", id);
        Market market = marketRepository.findOne(id);
        return modelMapper.map(market, MarketDTO.class);
    }

    /**
     * Delete the market by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Market : {}", id);
        marketRepository.delete(id);
        marketSearchRepository.delete(id);
    }

    /**
     * Search for the market corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MarketDTO> search(String query) {
        log.debug("Request to search Markets for query {}", query);
        return StreamSupport
            .stream(marketSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(x->modelMapper.map(x,MarketDTO.class))
            .collect(Collectors.toList());
    }
}
