package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.NewsBoardService;
import io.iansoft.blockchain.domain.NewsBoard;
import io.iansoft.blockchain.repository.NewsBoardRepository;
import io.iansoft.blockchain.repository.search.NewsBoardSearchRepository;
import io.iansoft.blockchain.service.dto.NewsBoardDTO;
import io.iansoft.blockchain.service.mapper.NewsBoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing NewsBoard.
 */
@Service
@Transactional
public class NewsBoardServiceImpl implements NewsBoardService{

    private final Logger log = LoggerFactory.getLogger(NewsBoardServiceImpl.class);

    private final NewsBoardRepository newsBoardRepository;

//    private final NewsBoardMapper newsBoardMapper;

    private final NewsBoardSearchRepository newsBoardSearchRepository;

    public NewsBoardServiceImpl(NewsBoardRepository newsBoardRepository, NewsBoardSearchRepository newsBoardSearchRepository) {
        this.newsBoardRepository = newsBoardRepository;
//        this.newsBoardMapper = newsBoardMapper;
        this.newsBoardSearchRepository = newsBoardSearchRepository;
    }

    /**
     * Save a newsBoard.
     *
     * @param newsBoardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NewsBoardDTO save(NewsBoardDTO newsBoardDTO) {
        log.debug("Request to save NewsBoard : {}", newsBoardDTO);
//        NewsBoard newsBoard = newsBoardMapper.toEntity(newsBoardDTO);
//        newsBoard = newsBoardRepository.save(newsBoard);
//        NewsBoardDTO result = newsBoardMapper.toDto(newsBoard);
//        newsBoardSearchRepository.save(newsBoard);
//        return result;
        return null;
    }

    /**
     *  Get all the newsBoards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NewsBoardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NewsBoards");
//        return newsBoardRepository.findAll(pageable)
//            .map(newsBoardMapper::toDto);
        return null;
    }

    /**
     *  Get one newsBoard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NewsBoardDTO findOne(Long id) {
        log.debug("Request to get NewsBoard : {}", id);
        NewsBoard newsBoard = newsBoardRepository.findOne(id);
//        return newsBoardMapper.toDto(newsBoard);
        return null;
    }

    /**
     *  Delete the  newsBoard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewsBoard : {}", id);
        newsBoardRepository.delete(id);
        newsBoardSearchRepository.delete(id);
    }

    /**
     * Search for the newsBoard corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NewsBoardDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of NewsBoards for query {}", query);
        Page<NewsBoard> result = newsBoardSearchRepository.search(queryStringQuery(query), pageable);
      //  return result.map(newsBoardMapper::toDto);
        return null;
    }
}
