package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.KrakenService;
import io.iansoft.blockchain.domain.Kraken;
import io.iansoft.blockchain.repository.KrakenRepository;
import io.iansoft.blockchain.repository.search.KrakenSearchRepository;
import io.iansoft.blockchain.service.dto.KrakenDTO;
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
 * Service Implementation for managing Kraken.
 */
@Service
@Transactional
public class KrakenServiceImpl implements KrakenService{

    private final Logger log = LoggerFactory.getLogger(KrakenServiceImpl.class);
    private final KrakenRepository krakenRepository;
    private final ModelMapper modelMapper;

    private final KrakenSearchRepository krakenSearchRepository;
    public KrakenServiceImpl(KrakenRepository krakenRepository, ModelMapper modelMapper, KrakenSearchRepository krakenSearchRepository) {
        this.krakenRepository = krakenRepository;
        this.modelMapper = modelMapper;
        this.krakenSearchRepository = krakenSearchRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<KrakenDTO> getKraken() {
        return krakenRepository.findKrakens().stream()
            .map(kraken -> modelMapper.map(kraken, KrakenDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
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
        Kraken kraken = modelMapper.map(krakenDTO, Kraken.class);
        kraken = krakenRepository.save(kraken);
        KrakenDTO result = modelMapper.map(kraken, KrakenDTO.class);
        krakenSearchRepository.save(kraken);
        return result;
    }

    /**
     *  Get all the krakens.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KrakenDTO> findAll() {
        log.debug("Request to get all Krakens");
        return krakenRepository.findAll().stream()
            .map(kraken -> modelMapper.map(kraken, KrakenDTO.class))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one kraken by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KrakenDTO findOne(Long id) {
        log.debug("Request to get Kraken : {}", id);
        Kraken kraken = krakenRepository.findOne(id);
        return modelMapper.map(kraken,KrakenDTO.class);
    }

    /**
     *  Delete the  kraken by id.
     *
     *  @param id the id of the entity
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
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KrakenDTO> search(String query) {
        log.debug("Request to search Krakens for query {}", query);
        return StreamSupport
            .stream(krakenSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map( kraken -> modelMapper.map(kraken, KrakenDTO.class))
            .collect(Collectors.toList());
    }
}
