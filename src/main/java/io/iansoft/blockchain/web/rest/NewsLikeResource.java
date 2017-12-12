package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.NewsLikeService;
import io.iansoft.blockchain.web.rest.errors.BadRequestAlertException;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.service.dto.NewsLikeDTO;
import io.iansoft.blockchain.service.dto.NewsLikeCriteria;
import io.iansoft.blockchain.service.NewsLikeQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing NewsLike.
 */
@RestController
@RequestMapping("/api")
public class NewsLikeResource {

    private final Logger log = LoggerFactory.getLogger(NewsLikeResource.class);

    private static final String ENTITY_NAME = "newsLike";

    private final NewsLikeService newsLikeService;

    private final NewsLikeQueryService newsLikeQueryService;

    public NewsLikeResource(NewsLikeService newsLikeService, NewsLikeQueryService newsLikeQueryService) {
        this.newsLikeService = newsLikeService;
        this.newsLikeQueryService = newsLikeQueryService;
    }

    /**
     * POST  /news-likes : Create a new newsLike.
     *
     * @param newsLikeDTO the newsLikeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newsLikeDTO, or with status 400 (Bad Request) if the newsLike has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/news-likes")
    @Timed
    public ResponseEntity<NewsLikeDTO> createNewsLike(@RequestBody NewsLikeDTO newsLikeDTO) throws URISyntaxException {
        log.debug("REST request to save NewsLike : {}", newsLikeDTO);
        if (newsLikeDTO.getId() != null) {
            throw new BadRequestAlertException("A new newsLike cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsLikeDTO result = newsLikeService.save(newsLikeDTO);
        return ResponseEntity.created(new URI("/api/news-likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /news-likes : Updates an existing newsLike.
     *
     * @param newsLikeDTO the newsLikeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated newsLikeDTO,
     * or with status 400 (Bad Request) if the newsLikeDTO is not valid,
     * or with status 500 (Internal Server Error) if the newsLikeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/news-likes")
    @Timed
    public ResponseEntity<NewsLikeDTO> updateNewsLike(@RequestBody NewsLikeDTO newsLikeDTO) throws URISyntaxException {
        log.debug("REST request to update NewsLike : {}", newsLikeDTO);
        if (newsLikeDTO.getId() == null) {
            return createNewsLike(newsLikeDTO);
        }
        NewsLikeDTO result = newsLikeService.save(newsLikeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newsLikeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /news-likes : get all the newsLikes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of newsLikes in body
     */
    @GetMapping("/news-likes")
    @Timed
    public ResponseEntity<List<NewsLikeDTO>> getAllNewsLikes(NewsLikeCriteria criteria) {
        log.debug("REST request to get NewsLikes by criteria: {}", criteria);
        List<NewsLikeDTO> entityList = newsLikeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /news-likes/:id : get the "id" newsLike.
     *
     * @param id the id of the newsLikeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newsLikeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/news-likes/{id}")
    @Timed
    public ResponseEntity<NewsLikeDTO> getNewsLike(@PathVariable Long id) {
        log.debug("REST request to get NewsLike : {}", id);
        NewsLikeDTO newsLikeDTO = newsLikeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(newsLikeDTO));
    }

    /**
     * DELETE  /news-likes/:id : delete the "id" newsLike.
     *
     * @param id the id of the newsLikeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/news-likes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNewsLike(@PathVariable Long id) {
        log.debug("REST request to delete NewsLike : {}", id);
        newsLikeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/news-likes?query=:query : search for the newsLike corresponding
     * to the query.
     *
     * @param query the query of the newsLike search
     * @return the result of the search
     */
    @GetMapping("/_search/news-likes")
    @Timed
    public List<NewsLikeDTO> searchNewsLikes(@RequestParam String query) {
        log.debug("REST request to search NewsLikes for query {}", query);
        return newsLikeService.search(query);
    }

}
