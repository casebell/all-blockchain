package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.BitfinexService;
import io.iansoft.blockchain.domain.Bitfinex;
import io.iansoft.blockchain.repository.BitfinexRepository;
import io.iansoft.blockchain.repository.search.BitfinexSearchRepository;
import io.iansoft.blockchain.service.dto.BitfinexDTO;
import io.iansoft.blockchain.service.mapper.BitfinexMapper;
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
 * Service Implementation for managing Bitfinex.
 */
@Service
@Transactional
public class BitfinexServiceImpl implements BitfinexService{

    private final Logger log = LoggerFactory.getLogger(BitfinexServiceImpl.class);

    private final BitfinexRepository bitfinexRepository;

    private final BitfinexMapper bitfinexMapper;

    private final BitfinexSearchRepository bitfinexSearchRepository;
    public BitfinexServiceImpl(BitfinexRepository bitfinexRepository, BitfinexMapper bitfinexMapper, BitfinexSearchRepository bitfinexSearchRepository) {
        this.bitfinexRepository = bitfinexRepository;
        this.bitfinexMapper = bitfinexMapper;
        this.bitfinexSearchRepository = bitfinexSearchRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitfinexDTO> getBitfinex() {
        return bitfinexRepository.findBitfinex().stream()
            .map(bitfinexMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Save a bitfinex.
     *
     * @param bitfinexDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BitfinexDTO save(BitfinexDTO bitfinexDTO) {
        log.debug("Request to save Bitfinex : {}", bitfinexDTO);
        Bitfinex bitfinex = bitfinexMapper.toEntity(bitfinexDTO);
        bitfinex = bitfinexRepository.save(bitfinex);
        BitfinexDTO result = bitfinexMapper.toDto(bitfinex);
        bitfinexSearchRepository.save(bitfinex);
        return result;
    }

    /**
     *  Get all the bitfinexes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BitfinexDTO> findAll() {
        log.debug("Request to get all Bitfinexes");
        return bitfinexRepository.findAll().stream()
            .map(bitfinexMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the bitfinexes where Coin is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BitfinexDTO> findAllWhereCoinIsNull() {
        log.debug("Request to get all bitfinexes where Coin is null");
        return StreamSupport
            .stream(bitfinexRepository.findAll().spliterator(), false)
            .filter(bitfinex -> bitfinex.getCoin() == null)
            .map(bitfinexMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one bitfinex by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BitfinexDTO findOne(Long id) {
        log.debug("Request to get Bitfinex : {}", id);
        Bitfinex bitfinex = bitfinexRepository.findOne(id);
        return bitfinexMapper.toDto(bitfinex);
    }

    /**
     *  Delete the  bitfinex by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bitfinex : {}", id);
        bitfinexRepository.delete(id);
        bitfinexSearchRepository.delete(id);
    }

    /**
     * Search for the bitfinex corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BitfinexDTO> search(String query) {
        log.debug("Request to search Bitfinexes for query {}", query);
        return StreamSupport
            .stream(bitfinexSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(bitfinexMapper::toDto)
            .collect(Collectors.toList());
    }
}
