package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.NewsBoardService;
import io.iansoft.blockchain.web.rest.errors.BadRequestAlertException;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.web.rest.util.PaginationUtil;
import io.iansoft.blockchain.service.dto.NewsBoardDTO;
import io.iansoft.blockchain.service.dto.NewsBoardCriteria;
import io.iansoft.blockchain.service.NewsBoardQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing NewsBoard.
 */
@RestController
@RequestMapping("/api")
public class NewsBoardResource {

    private final Logger log = LoggerFactory.getLogger(NewsBoardResource.class);

    private static final String ENTITY_NAME = "newsBoard";

    private final NewsBoardService newsBoardService;

    private final NewsBoardQueryService newsBoardQueryService;

    public NewsBoardResource(NewsBoardService newsBoardService, NewsBoardQueryService newsBoardQueryService) {
        this.newsBoardService = newsBoardService;
        this.newsBoardQueryService = newsBoardQueryService;
    }

    /**
     * POST  /news-boards : Create a new newsBoard.
     *
     * @param newsBoardDTO the newsBoardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newsBoardDTO, or with status 400 (Bad Request) if the newsBoard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/news-boards")
    @Timed
    public ResponseEntity<NewsBoardDTO> createNewsBoard(@Valid @RequestBody NewsBoardDTO newsBoardDTO) throws URISyntaxException {
        log.debug("REST request to save NewsBoard : {}", newsBoardDTO);
        if (newsBoardDTO.getId() != null) {
            throw new BadRequestAlertException("A new newsBoard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsBoardDTO result = newsBoardService.save(newsBoardDTO);
        return ResponseEntity.created(new URI("/api/news-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /news-boards : Updates an existing newsBoard.
     *
     * @param newsBoardDTO the newsBoardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated newsBoardDTO,
     * or with status 400 (Bad Request) if the newsBoardDTO is not valid,
     * or with status 500 (Internal Server Error) if the newsBoardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/news-boards")
    @Timed
    public ResponseEntity<NewsBoardDTO> updateNewsBoard(@Valid @RequestBody NewsBoardDTO newsBoardDTO) throws URISyntaxException {
        log.debug("REST request to update NewsBoard : {}", newsBoardDTO);
        if (newsBoardDTO.getId() == null) {
            return createNewsBoard(newsBoardDTO);
        }
        NewsBoardDTO result = newsBoardService.save(newsBoardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newsBoardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /news-boards : get all the newsBoards.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of newsBoards in body
     */
    @GetMapping("/news-boards")
    @Timed
    public ResponseEntity<List<NewsBoardDTO>> getAllNewsBoards(NewsBoardCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get NewsBoards by criteria: {}", criteria);
        Page<NewsBoardDTO> page = newsBoardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/news-boards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /news-boards/:id : get the "id" newsBoard.
     *
     * @param id the id of the newsBoardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newsBoardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/news-boards/{id}")
    @Timed
    public ResponseEntity<NewsBoardDTO> getNewsBoard(@PathVariable Long id) {
        log.debug("REST request to get NewsBoard : {}", id);
        NewsBoardDTO newsBoardDTO = newsBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(newsBoardDTO));
    }

    /**
     * DELETE  /news-boards/:id : delete the "id" newsBoard.
     *
     * @param id the id of the newsBoardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/news-boards/{id}")
    @Timed
    public ResponseEntity<Void> deleteNewsBoard(@PathVariable Long id) {
        log.debug("REST request to delete NewsBoard : {}", id);
        newsBoardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/news-boards?query=:query : search for the newsBoard corresponding
     * to the query.
     *
     * @param query the query of the newsBoard search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/news-boards")
    @Timed
    public ResponseEntity<List<NewsBoardDTO>> searchNewsBoards(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of NewsBoards for query {}", query);
        Page<NewsBoardDTO> page = newsBoardService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/news-boards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
