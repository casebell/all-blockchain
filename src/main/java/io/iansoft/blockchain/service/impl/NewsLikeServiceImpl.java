package io.iansoft.blockchain.service.impl;

import io.iansoft.blockchain.service.NewsLikeService;
import io.iansoft.blockchain.domain.NewsLike;
import io.iansoft.blockchain.repository.NewsLikeRepository;
import io.iansoft.blockchain.repository.search.NewsLikeSearchRepository;
import io.iansoft.blockchain.service.dto.NewsLikeDTO;
import io.iansoft.blockchain.service.mapper.NewsLikeMapper;
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
 * Service Implementation for managing NewsLike.
 */
@Service
@Transactional
public class NewsLikeServiceImpl implements NewsLikeService{

    private final Logger log = LoggerFactory.getLogger(NewsLikeServiceImpl.class);

    private final NewsLikeRepository newsLikeRepository;

    //private final NewsLikeMapper newsLikeMapper;

    private final NewsLikeSearchRepository newsLikeSearchRepository;

    public NewsLikeServiceImpl(NewsLikeRepository newsLikeRepository, NewsLikeSearchRepository newsLikeSearchRepository) {
        this.newsLikeRepository = newsLikeRepository;
//        this.newsLikeMapper = newsLikeMapper;
        this.newsLikeSearchRepository = newsLikeSearchRepository;
    }

    /**
     * Save a newsLike.
     *
     * @param newsLikeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NewsLikeDTO save(NewsLikeDTO newsLikeDTO) {
        log.debug("Request to save NewsLike : {}", newsLikeDTO);
//        NewsLike newsLike = newsLikeMapper.toEntity(newsLikeDTO);
//        newsLike = newsLikeRepository.save(newsLike);
//        NewsLikeDTO result = newsLikeMapper.toDto(newsLike);
//        newsLikeSearchRepository.save(newsLike);
//        return result;
        return null;
    }

    /**
     *  Get all the newsLikes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NewsLikeDTO> findAll() {
        log.debug("Request to get all NewsLikes");
//        return newsLikeRepository.findAll().stream()
//            .map(newsLikeMapper::toDto)
//            .collect(Collectors.toCollection(LinkedList::new));
        return null;
    }

    /**
     *  Get one newsLike by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NewsLikeDTO findOne(Long id) {
        log.debug("Request to get NewsLike : {}", id);
        NewsLike newsLike = newsLikeRepository.findOne(id);
//        return newsLikeMapper.toDto(newsLike);
        return null;
    }


    /**
     *  Delete the  newsLike by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewsLike : {}", id);
        newsLikeRepository.delete(id);
        newsLikeSearchRepository.delete(id);
    }

    /**
     * Search for the newsLike corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NewsLikeDTO> search(String query) {
        log.debug("Request to search NewsLikes for query {}", query);
//        return StreamSupport
//            .stream(newsLikeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .map(newsLikeMapper::toDto)
//            .collect(Collectors.toList());
        return null;
    }
}
