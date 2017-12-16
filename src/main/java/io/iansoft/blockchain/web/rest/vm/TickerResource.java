package io.iansoft.blockchain.web.rest.vm;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.iansoft.blockchain.service.MarketService;
import io.iansoft.blockchain.service.dto.MarketDTO;
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
 * REST controller for managing Market.
 */
@RestController
@RequestMapping("/api")
public class TickerResource {

    private final Logger log = LoggerFactory.getLogger(TickerResource.class);

    private static final String ENTITY_NAME = "ticker";

    private final MarketService marketService;

    public TickerResource(MarketService marketService) {
        this.marketService = marketService;
    }

    /**
     * POST  /markets : Create a new market.
     *
     * @param marketDTO the marketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketDTO, or with status 400 (Bad Request) if the market has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/markets")
    @Timed
    public ResponseEntity<MarketDTO> createMarket(@Valid @RequestBody MarketDTO marketDTO) throws URISyntaxException {
        log.debug("REST request to save Market : {}", marketDTO);
        if (marketDTO.getId() != null) {
            throw new BadRequestAlertException("A new market cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketDTO result = marketService.save(marketDTO);
        return ResponseEntity.created(new URI("/api/markets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /markets : Updates an existing market.
     *
     * @param marketDTO the marketDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketDTO,
     * or with status 400 (Bad Request) if the marketDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/markets")
    @Timed
    public ResponseEntity<MarketDTO> updateMarket(@Valid @RequestBody MarketDTO marketDTO) throws URISyntaxException {
        log.debug("REST request to update Market : {}", marketDTO);
        if (marketDTO.getId() == null) {
            return createMarket(marketDTO);
        }
        MarketDTO result = marketService.save(marketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /markets : get all the markets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of markets in body
     */
    @GetMapping("/markets")
    @Timed
    public List<MarketDTO> getAllMarkets() {
        log.debug("REST request to get all Markets");
        return marketService.findAll();
        }

    /**
     * GET  /markets/:id : get the "id" market.
     *
     * @param id the id of the marketDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketDTO, or with status 404 (Not Found)
     */
    @GetMapping("/markets/{id}")
    @Timed
    public ResponseEntity<MarketDTO> getMarket(@PathVariable Long id) {
        log.debug("REST request to get Market : {}", id);
        MarketDTO marketDTO = marketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketDTO));
    }

    /**
     * DELETE  /markets/:id : delete the "id" market.
     *
     * @param id the id of the marketDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/markets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarket(@PathVariable Long id) {
        log.debug("REST request to delete Market : {}", id);
        marketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/markets?query=:query : search for the market corresponding
     * to the query.
     *
     * @param query the query of the market search
     * @return the result of the search
     */
    @GetMapping("/_search/markets")
    @Timed
    public List<MarketDTO> searchMarkets(@RequestParam String query) {
        log.debug("REST request to search Markets for query {}", query);
        return marketService.search(query);
    }

}
