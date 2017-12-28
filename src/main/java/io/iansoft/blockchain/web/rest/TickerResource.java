package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.iansoft.blockchain.service.TickerService;
import io.iansoft.blockchain.service.dto.MarketCoinDTO;
import io.iansoft.blockchain.service.dto.TickerDTO;
import io.iansoft.blockchain.web.rest.errors.BadRequestAlertException;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ticker.
 */
@RestController
@RequestMapping("/api")
public class TickerResource {

    private final Logger log = LoggerFactory.getLogger(TickerResource.class);

    private static final String ENTITY_NAME = "ticker";

    private final TickerService tickerService;

    public TickerResource(TickerService tickerService) {
        this.tickerService = tickerService;
    }

    /**
     * POST  /tickers : Create a new ticker.
     *
     * @param tickerDTO the tickerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tickerDTO, or with status 400 (Bad Request) if the ticker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tickers")
    @Timed
    public ResponseEntity<TickerDTO> createTicker(@Valid @RequestBody TickerDTO tickerDTO) throws URISyntaxException {
        log.debug("REST request to save Ticker : {}", tickerDTO);
        if (tickerDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TickerDTO result = tickerService.save(tickerDTO);
        return ResponseEntity.created(new URI("/api/tickers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/tickers/{userId}")
    @Timed
    public ResponseEntity<List<TickerDTO>> createTickers(@PathVariable Long userId,@Valid @RequestBody List<MarketCoinDTO> marketCoinDTOS) throws URISyntaxException {


        List<TickerDTO> results = tickerService.saveTickers(userId,marketCoinDTOS);
        return ResponseEntity.created(new URI("/api/tickers/" + results.get(0).getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, results.get(0).getId().toString()))
            .body(results);
    }

    /**
     * PUT  /tickers : Updates an existing ticker.
     *
     * @param tickerDTO the tickerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tickerDTO,
     * or with status 400 (Bad Request) if the tickerDTO is not valid,
     * or with status 500 (Internal Server Error) if the tickerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tickers")
    @Timed
    public ResponseEntity<TickerDTO> updateTicker(@Valid @RequestBody TickerDTO tickerDTO) throws URISyntaxException {
        log.debug("REST request to update Ticker : {}", tickerDTO);
        if (tickerDTO.getId() == null) {
            return createTicker(tickerDTO);
        }
        TickerDTO result = tickerService.save(tickerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tickerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tickers : get all the tickers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tickers in body
     */
    @GetMapping("/tickers")
    @Timed
    public List<TickerDTO> getAllTickers() {
        log.debug("REST request to get all Tickers");
        return tickerService.findAll();
        }

    /**
     * GET  /tickers/:id : get the "id" ticker.
     *
     * @param id the id of the tickerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tickerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tickers/{id}")
    @Timed
    public ResponseEntity<TickerDTO> getTicker(@PathVariable Long id) {
        log.debug("REST request to get Ticker : {}", id);
        TickerDTO tickerDTO = tickerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tickerDTO));
    }

    /**
     * DELETE  /tickers/:id : delete the "id" ticker.
     *
     * @param id the id of the tickerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tickers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicker(@PathVariable Long id) {
        log.debug("REST request to delete Market : {}", id);
        tickerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    /**
     * SEARCH  /_search/tickers?query=:query : search for the market corresponding
     * to the query.
     *
     * @param query the query of the market search
     * @return the result of the search
     */
    @GetMapping("/_search/tickers")
    @Timed
    public List<TickerDTO> searchTickers(@RequestParam String query) {
        log.debug("REST request to search Markets for query {}", query);
        return tickerService.search(query);
    }

}
