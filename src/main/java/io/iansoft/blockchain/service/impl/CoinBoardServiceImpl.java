package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.CoinBoardService;
import io.iansoft.blockchain.domain.CoinBoard;
import io.iansoft.blockchain.repository.CoinBoardRepository;
import io.iansoft.blockchain.repository.search.CoinBoardSearchRepository;
import io.iansoft.blockchain.service.dto.CoinBoardDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CoinBoard.
 */
@Service
@Transactional
public class CoinBoardServiceImpl implements CoinBoardService {

    private final Logger log = LoggerFactory.getLogger(CoinBoardServiceImpl.class);

    private final CoinBoardRepository coinBoardRepository;

    private final CoinBoardSearchRepository coinBoardSearchRepository;

    private final ModelMapper modelMapper;

    public CoinBoardServiceImpl(CoinBoardRepository coinBoardRepository, CoinBoardSearchRepository coinBoardSearchRepository, ModelMapper modelMapper) {
        this.coinBoardRepository = coinBoardRepository;
        this.coinBoardSearchRepository = coinBoardSearchRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Save a coinBoard.
     *
     * @param coinBoardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoinBoardDTO save(CoinBoardDTO coinBoardDTO) {
        log.debug("Request to save CoinBoard : {}", coinBoardDTO);
        CoinBoard coinBoard = modelMapper.map(coinBoardDTO, CoinBoard.class);
        coinBoard = coinBoardRepository.save(coinBoard);
        CoinBoardDTO result = modelMapper.map(coinBoard,CoinBoardDTO.class );
        coinBoardSearchRepository.save(coinBoard);
        return result;
    }

    /**
     *  Get all the coinBoards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoinBoardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoinBoards");
        return coinBoardRepository.findAll(pageable)
            .map( coinBoard -> modelMapper.map(coinBoard,CoinBoardDTO.class));
    }

    /**
     *  Get one coinBoard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoinBoardDTO findOne(Long id) {
        log.debug("Request to get CoinBoard : {}", id);
        CoinBoard coinBoard = coinBoardRepository.findOne(id);
        return modelMapper.map(coinBoard, CoinBoardDTO.class);
    }

    /**
     *  Delete the  coinBoard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoinBoard : {}", id);
        coinBoardRepository.delete(id);
        coinBoardSearchRepository.delete(id);
    }

    /**
     * Search for the coinBoard corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoinBoardDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CoinBoards for query {}", query);
        Page<CoinBoard> result = coinBoardSearchRepository.search(queryStringQuery(query), pageable);
        return result.map( coinBoard -> modelMapper.map(coinBoard, CoinBoardDTO.class));
    }
}
