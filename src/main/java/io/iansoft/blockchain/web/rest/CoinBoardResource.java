package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.CoinBoardService;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.web.rest.util.PaginationUtil;
import io.iansoft.blockchain.service.dto.CoinBoardDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CoinBoard.
 */
@RestController
@RequestMapping("/api")
public class CoinBoardResource {

    private final Logger log = LoggerFactory.getLogger(CoinBoardResource.class);

    private static final String ENTITY_NAME = "coinBoard";

    private final CoinBoardService coinBoardService;

    public CoinBoardResource(CoinBoardService coinBoardService) {
        this.coinBoardService = coinBoardService;
    }

    /**
     * POST  /coin-boards : Create a new coinBoard.
     *
     * @param coinBoardDTO the coinBoardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coinBoardDTO, or with status 400 (Bad Request) if the coinBoard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coin-boards")
    @Timed
    public ResponseEntity<CoinBoardDTO> createCoinBoard(@RequestBody CoinBoardDTO coinBoardDTO) throws URISyntaxException {
        log.debug("REST request to save CoinBoard : {}", coinBoardDTO);
        if (coinBoardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new coinBoard cannot already have an ID")).body(null);
        }
        CoinBoardDTO result = coinBoardService.save(coinBoardDTO);
        return ResponseEntity.created(new URI("/api/coin-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coin-boards : Updates an existing coinBoard.
     *
     * @param coinBoardDTO the coinBoardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coinBoardDTO,
     * or with status 400 (Bad Request) if the coinBoardDTO is not valid,
     * or with status 500 (Internal Server Error) if the coinBoardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coin-boards")
    @Timed
    public ResponseEntity<CoinBoardDTO> updateCoinBoard(@RequestBody CoinBoardDTO coinBoardDTO) throws URISyntaxException {
        log.debug("REST request to update CoinBoard : {}", coinBoardDTO);
        if (coinBoardDTO.getId() == null) {
            return createCoinBoard(coinBoardDTO);
        }
        CoinBoardDTO result = coinBoardService.save(coinBoardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coinBoardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coin-boards : get all the coinBoards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coinBoards in body
     */
    @GetMapping("/coin-boards")
    @Timed
    public ResponseEntity<List<CoinBoardDTO>> getAllCoinBoards(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CoinBoards");
        Page<CoinBoardDTO> page = coinBoardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coin-boards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /coin-boards/:id : get the "id" coinBoard.
     *
     * @param id the id of the coinBoardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coinBoardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coin-boards/{id}")
    @Timed
    public ResponseEntity<CoinBoardDTO> getCoinBoard(@PathVariable Long id) {
        log.debug("REST request to get CoinBoard : {}", id);
        CoinBoardDTO coinBoardDTO = coinBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coinBoardDTO));
    }

    /**
     * DELETE  /coin-boards/:id : delete the "id" coinBoard.
     *
     * @param id the id of the coinBoardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coin-boards/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoinBoard(@PathVariable Long id) {
        log.debug("REST request to delete CoinBoard : {}", id);
        coinBoardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/coin-boards?query=:query : search for the coinBoard corresponding
     * to the query.
     *
     * @param query the query of the coinBoard search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/coin-boards")
    @Timed
    public ResponseEntity<List<CoinBoardDTO>> searchCoinBoards(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CoinBoards for query {}", query);
        Page<CoinBoardDTO> page = coinBoardService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/coin-boards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
