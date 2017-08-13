package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.CoinBoardCommentService;
import io.iansoft.blockchain.domain.CoinBoardComment;
import io.iansoft.blockchain.repository.CoinBoardCommentRepository;
import io.iansoft.blockchain.repository.search.CoinBoardCommentSearchRepository;
import io.iansoft.blockchain.service.dto.CoinBoardCommentDTO;
import io.iansoft.blockchain.service.mapper.CoinBoardCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CoinBoardComment.
 */
@Service
@Transactional
public class CoinBoardCommentServiceImpl implements CoinBoardCommentService{

    private final Logger log = LoggerFactory.getLogger(CoinBoardCommentServiceImpl.class);

    private final CoinBoardCommentRepository coinBoardCommentRepository;

    private final CoinBoardCommentMapper coinBoardCommentMapper;

    private final CoinBoardCommentSearchRepository coinBoardCommentSearchRepository;

    public CoinBoardCommentServiceImpl(CoinBoardCommentRepository coinBoardCommentRepository, CoinBoardCommentMapper coinBoardCommentMapper, CoinBoardCommentSearchRepository coinBoardCommentSearchRepository) {
        this.coinBoardCommentRepository = coinBoardCommentRepository;
        this.coinBoardCommentMapper = coinBoardCommentMapper;
        this.coinBoardCommentSearchRepository = coinBoardCommentSearchRepository;
    }

    /**
     * Save a coinBoardComment.
     *
     * @param coinBoardCommentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoinBoardCommentDTO save(CoinBoardCommentDTO coinBoardCommentDTO) {
        log.debug("Request to save CoinBoardComment : {}", coinBoardCommentDTO);
        CoinBoardComment coinBoardComment = coinBoardCommentMapper.toEntity(coinBoardCommentDTO);
        coinBoardComment = coinBoardCommentRepository.save(coinBoardComment);
        CoinBoardCommentDTO result = coinBoardCommentMapper.toDto(coinBoardComment);
        coinBoardCommentSearchRepository.save(coinBoardComment);
        return result;
    }

    /**
     *  Get all the coinBoardComments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoinBoardCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoinBoardComments");
        return coinBoardCommentRepository.findAll(pageable)
            .map(coinBoardCommentMapper::toDto);
    }

    /**
     *  Get one coinBoardComment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoinBoardCommentDTO findOne(Long id) {
        log.debug("Request to get CoinBoardComment : {}", id);
        CoinBoardComment coinBoardComment = coinBoardCommentRepository.findOne(id);
        return coinBoardCommentMapper.toDto(coinBoardComment);
    }

    /**
     *  Delete the  coinBoardComment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoinBoardComment : {}", id);
        coinBoardCommentRepository.delete(id);
        coinBoardCommentSearchRepository.delete(id);
    }

    /**
     * Search for the coinBoardComment corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoinBoardCommentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CoinBoardComments for query {}", query);
        Page<CoinBoardComment> result = coinBoardCommentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(coinBoardCommentMapper::toDto);
    }
}
