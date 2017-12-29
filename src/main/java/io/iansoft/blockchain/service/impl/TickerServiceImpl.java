package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.domain.*;
import io.iansoft.blockchain.repository.TickerRepository;
import io.iansoft.blockchain.repository.search.TickerSearchRepository;
import io.iansoft.blockchain.service.TickerService;
import io.iansoft.blockchain.service.dto.MarketCoinDTO;
import io.iansoft.blockchain.service.dto.TickerDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Ticker.
 */
@Service
@Transactional
public class TickerServiceImpl implements TickerService{

    private final Logger log = LoggerFactory.getLogger(TickerServiceImpl.class);

    private final TickerRepository tickerRepository;

    private final ModelMapper modelMapper;
    private final TickerSearchRepository tickerSearchRepository;

    public TickerServiceImpl(TickerRepository tickerRepository, ModelMapper modelMapper, TickerSearchRepository tickerSearchRepository) {
        this.tickerRepository = tickerRepository;
        this.modelMapper = modelMapper;
        this.tickerSearchRepository = tickerSearchRepository;
    }

    /**
     * Save a ticker.
     *
     * @param tickerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TickerDTO save(TickerDTO tickerDTO) {
        log.debug("Request to save Ticker : {}", tickerDTO);
        Ticker ticker = modelMapper.map(tickerDTO, Ticker.class);
        ticker = tickerRepository.save(ticker);
        TickerDTO result = modelMapper.map(ticker, TickerDTO.class);
        tickerSearchRepository.save(ticker);
        return result;
    }

    /**
     * Get all the tickers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TickerDTO> findAll() {
        log.debug("Request to get all Tickers");
        return tickerRepository.findAll().stream()
            .map( x -> modelMapper.map(x,TickerDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ticker by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TickerDTO findOne(Long id) {
        log.debug("Request to get Ticker : {}", id);
        Ticker ticker = tickerRepository.findOne(id);
        return modelMapper.map(ticker, TickerDTO.class);
    }

    /**
     * Delete the ticker by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ticker : {}", id);
        tickerRepository.delete(id);
        tickerSearchRepository.delete(id);
    }

    /**
     * Search for the ticker corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TickerDTO> search(String query) {
        log.debug("Request to search Tickers for query {}", query);
        return StreamSupport
            .stream(tickerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(x->modelMapper.map(x,TickerDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<TickerDTO> saveTickers(Long userId,List<MarketCoinDTO> marketCoinDTOs) {
        List<MarketCoin> marketCoins = new ArrayList<>();

        marketCoinDTOs.forEach(x->{
            MarketCoin marketCoin = new MarketCoin();
            marketCoin.setId(x.getId());
            marketCoins.add(marketCoin);
        });
        //List<MarketCoin> marketCoins = marketCoinDTOs.stream().map(x->modelMapper.map(x, MarketCoin.class)).collect(Collectors.toList());


        List<Ticker> tickers = new ArrayList<>();
        User user = new User();
        user.setId(userId);
        marketCoins.forEach(x->{
            Ticker ticker = new Ticker();
            ticker.setUser(user);
            ticker.setMarketCoin(x);
            tickers.add(ticker);
        });
        List<Ticker> resultTickers = tickerRepository.save(tickers);

        return resultTickers.stream().map(x->modelMapper.map(x,TickerDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TickerDTO> findByUserId(Long userId) {
        List<Ticker> tickers = tickerRepository.findAllByUserId(userId);
        return tickers.stream().map(x-> modelMapper.map(x,TickerDTO.class)).collect(Collectors.toList());
    }
}
