package io.iansoft.blockchain.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.iansoft.blockchain.domain.NewsLike;
import io.iansoft.blockchain.domain.*; // for static metamodels
import io.iansoft.blockchain.repository.NewsLikeRepository;
import io.iansoft.blockchain.repository.search.NewsLikeSearchRepository;
import io.iansoft.blockchain.service.dto.NewsLikeCriteria;

import io.iansoft.blockchain.service.dto.NewsLikeDTO;
import io.iansoft.blockchain.service.mapper.NewsLikeMapper;

/**
 * Service for executing complex queries for NewsLike entities in the database.
 * The main input is a {@link NewsLikeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link NewsLikeDTO} or a {@link Page} of {%link NewsLikeDTO} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class NewsLikeQueryService extends QueryService<NewsLike> {

    private final Logger log = LoggerFactory.getLogger(NewsLikeQueryService.class);


    private final NewsLikeRepository newsLikeRepository;

//    private final NewsLikeMapper newsLikeMapper;

    private final NewsLikeSearchRepository newsLikeSearchRepository;

    public NewsLikeQueryService(NewsLikeRepository newsLikeRepository, NewsLikeSearchRepository newsLikeSearchRepository) {
        this.newsLikeRepository = newsLikeRepository;
        //this.newsLikeMapper = newsLikeMapper;
        this.newsLikeSearchRepository = newsLikeSearchRepository;
    }

    /**
     * Return a {@link List} of {%link NewsLikeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NewsLikeDTO> findByCriteria(NewsLikeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<NewsLike> specification = createSpecification(criteria);
       // return newsLikeMapper.toDto(newsLikeRepository.findAll(specification));
        return null;
    }

    /**
     * Return a {@link Page} of {%link NewsLikeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NewsLikeDTO> findByCriteria(NewsLikeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<NewsLike> specification = createSpecification(criteria);
        final Page<NewsLike> result = newsLikeRepository.findAll(specification, page);
//        return result.map(newsLikeMapper::toDto);
        return null;
    }

    /**
     * Function to convert NewsLikeCriteria to a {@link Specifications}
     */
    private Specifications<NewsLike> createSpecification(NewsLikeCriteria criteria) {
        Specifications<NewsLike> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
        //        specification = specification.and(buildSpecification(criteria.getId(), NewsLike.));
            }
            if (criteria.getUserId() != null) {
               // specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), NewsLike.user, User.id));
            }
            if (criteria.getNewsBoardId() != null) {
               // specification = specification.and(buildReferringEntitySpecification(criteria.getNewsBoardId(), NewsLike.newsBoard, NewsBoard_.id));
            }
        }
        return specification;
    }

}
