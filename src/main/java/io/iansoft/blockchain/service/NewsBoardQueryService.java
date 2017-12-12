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

import io.iansoft.blockchain.domain.NewsBoard;
import io.iansoft.blockchain.domain.*; // for static metamodels
import io.iansoft.blockchain.repository.NewsBoardRepository;
import io.iansoft.blockchain.repository.search.NewsBoardSearchRepository;
import io.iansoft.blockchain.service.dto.NewsBoardCriteria;

import io.iansoft.blockchain.service.dto.NewsBoardDTO;
import io.iansoft.blockchain.service.mapper.NewsBoardMapper;

/**
 * Service for executing complex queries for NewsBoard entities in the database.
 * The main input is a {@link NewsBoardCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link NewsBoardDTO} or a {@link Page} of {%link NewsBoardDTO} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class NewsBoardQueryService extends QueryService<NewsBoard> {

    private final Logger log = LoggerFactory.getLogger(NewsBoardQueryService.class);


    private final NewsBoardRepository newsBoardRepository;

    //private final NewsBoardMapper newsBoardMapper;

    private final NewsBoardSearchRepository newsBoardSearchRepository;

    public NewsBoardQueryService(NewsBoardRepository newsBoardRepository, NewsBoardSearchRepository newsBoardSearchRepository) {
        this.newsBoardRepository = newsBoardRepository;
      //  this.newsBoardMapper = newsBoardMapper;
        this.newsBoardSearchRepository = newsBoardSearchRepository;
    }

    /**
     * Return a {@link List} of {%link NewsBoardDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NewsBoardDTO> findByCriteria(NewsBoardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<NewsBoard> specification = createSpecification(criteria);
      //  return newsBoardMapper.toDto(newsBoardRepository.findAll(specification));
        return null;
    }

    /**
     * Return a {@link Page} of {%link NewsBoardDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NewsBoardDTO> findByCriteria(NewsBoardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<NewsBoard> specification = createSpecification(criteria);
        final Page<NewsBoard> result = newsBoardRepository.findAll(specification, page);
       // return result.map(newsBoardMapper::toDto);
        return null;
    }

    /**
     * Function to convert NewsBoardCriteria to a {@link Specifications}
     */
    private Specifications<NewsBoard> createSpecification(NewsBoardCriteria criteria) {
        Specifications<NewsBoard> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
               // specification = specification.and(buildSpecification(criteria.getId(), NewsBoard_.id));
            }
            if (criteria.getTitle() != null) {
            //    specification = specification.and(buildStringSpecification(criteria.getTitle(), NewsBoard_.title));
            }
            if (criteria.getUrl() != null) {
             //   specification = specification.and(buildStringSpecification(criteria.getUrl(), NewsBoard_.url));
            }
            if (criteria.getContext() != null) {
             //   specification = specification.and(buildStringSpecification(criteria.getContext(), NewsBoard_.context));
            }
            if (criteria.getHit() != null) {
            //    specification = specification.and(buildRangeSpecification(criteria.getHit(), NewsBoard_.hit));
            }
            if (criteria.getUserId() != null) {
              //  specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), NewsBoard_.user, User_.id));
            }
        }
        return specification;
    }

}
