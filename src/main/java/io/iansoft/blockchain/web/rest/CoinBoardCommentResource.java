package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.CoinBoardCommentService;
import io.iansoft.blockchain.web.rest.errors.BadRequestAlertException;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.web.rest.util.PaginationUtil;
import io.iansoft.blockchain.service.dto.CoinBoardCommentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CoinBoardComment.
 */
@RestController
@RequestMapping("/api")
public class CoinBoardCommentResource {

    private final Logger log = LoggerFactory.getLogger(CoinBoardCommentResource.class);

    private static final String ENTITY_NAME = "coinBoardComment";

    private final CoinBoardCommentService coinBoardCommentService;

    public CoinBoardCommentResource(CoinBoardCommentService coinBoardCommentService) {
        this.coinBoardCommentService = coinBoardCommentService;
    }

    /**
     * POST  /coin-board-comments : Create a new coinBoardComment.
     *
     * @param coinBoardCommentDTO the coinBoardCommentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coinBoardCommentDTO, or with status 400 (Bad Request) if the coinBoardComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coin-board-comments")
    @Timed
    public ResponseEntity<CoinBoardCommentDTO> createCoinBoardComment(@RequestBody CoinBoardCommentDTO coinBoardCommentDTO) throws URISyntaxException {
        log.debug("REST request to save CoinBoardComment : {}", coinBoardCommentDTO);
        if (coinBoardCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new coinBoardComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoinBoardCommentDTO result = coinBoardCommentService.save(coinBoardCommentDTO);
        return ResponseEntity.created(new URI("/api/coin-board-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coin-board-comments : Updates an existing coinBoardComment.
     *
     * @param coinBoardCommentDTO the coinBoardCommentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coinBoardCommentDTO,
     * or with status 400 (Bad Request) if the coinBoardCommentDTO is not valid,
     * or with status 500 (Internal Server Error) if the coinBoardCommentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coin-board-comments")
    @Timed
    public ResponseEntity<CoinBoardCommentDTO> updateCoinBoardComment(@RequestBody CoinBoardCommentDTO coinBoardCommentDTO) throws URISyntaxException {
        log.debug("REST request to update CoinBoardComment : {}", coinBoardCommentDTO);
        if (coinBoardCommentDTO.getId() == null) {
            return createCoinBoardComment(coinBoardCommentDTO);
        }
        CoinBoardCommentDTO result = coinBoardCommentService.save(coinBoardCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coinBoardCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coin-board-comments : get all the coinBoardComments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coinBoardComments in body
     */
    @GetMapping("/coin-board-comments")
    @Timed
    public ResponseEntity<List<CoinBoardCommentDTO>> getAllCoinBoardComments(Pageable pageable) {
        log.debug("REST request to get a page of CoinBoardComments");
        Page<CoinBoardCommentDTO> page = coinBoardCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coin-board-comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /coin-board-comments/:id : get the "id" coinBoardComment.
     *
     * @param id the id of the coinBoardCommentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coinBoardCommentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coin-board-comments/{id}")
    @Timed
    public ResponseEntity<CoinBoardCommentDTO> getCoinBoardComment(@PathVariable Long id) {
        log.debug("REST request to get CoinBoardComment : {}", id);
        CoinBoardCommentDTO coinBoardCommentDTO = coinBoardCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coinBoardCommentDTO));
    }

    /**
     * DELETE  /coin-board-comments/:id : delete the "id" coinBoardComment.
     *
     * @param id the id of the coinBoardCommentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coin-board-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoinBoardComment(@PathVariable Long id) {
        log.debug("REST request to delete CoinBoardComment : {}", id);
        coinBoardCommentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/coin-board-comments?query=:query : search for the coinBoardComment corresponding
     * to the query.
     *
     * @param query the query of the coinBoardComment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/coin-board-comments")
    @Timed
    public ResponseEntity<List<CoinBoardCommentDTO>> searchCoinBoardComments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CoinBoardComments for query {}", query);
        Page<CoinBoardCommentDTO> page = coinBoardCommentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/coin-board-comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
