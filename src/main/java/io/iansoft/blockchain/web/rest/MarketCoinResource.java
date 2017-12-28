package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.iansoft.blockchain.service.MarketCoinService;
import io.iansoft.blockchain.service.dto.CoinDTO;
import io.iansoft.blockchain.service.dto.MarketCoinDTO;
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
 * REST controller for managing Market Coin.
 */
@RestController
@RequestMapping("/api")
public class MarketCoinResource {

    private final Logger log = LoggerFactory.getLogger(MarketCoinResource.class);

    private static final String ENTITY_NAME = "marketCoin";

    private final MarketCoinService marketCoinService;

    public MarketCoinResource(MarketCoinService marketCoinService) {
        this.marketCoinService = marketCoinService;
    }

    /**
     * POST  /market-coins : Create a new market.
     *
     * @param marketCoinDTO the marketCoinDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketCoinDTO, or with status 400 (Bad Request) if the market has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-coins")
    @Timed
    public ResponseEntity<MarketCoinDTO> createMarketCoin(@Valid @RequestBody MarketCoinDTO marketCoinDTO) throws URISyntaxException {
        log.debug("REST request to save Market : {}", marketCoinDTO);
        if (marketCoinDTO.getId() != null) {
            throw new BadRequestAlertException("A new market cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketCoinDTO result = marketCoinService.save(marketCoinDTO);
        return ResponseEntity.created(new URI("/api/market-coins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-coins : Updates an existing market coin.
     *
     * @param marketCoinDTO the marketCoinDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketCoinDTO,
     * or with status 400 (Bad Request) if the marketCoinDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketCoinDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-coins")
    @Timed
    public ResponseEntity<MarketCoinDTO> updateMarketCoin(@Valid @RequestBody MarketCoinDTO marketCoinDTO) throws URISyntaxException {
        log.debug("REST request to update MarketCoin : {}", marketCoinDTO);
        if (marketCoinDTO.getId() == null) {
            return createMarketCoin(marketCoinDTO);
        }
        MarketCoinDTO result = marketCoinService.save(marketCoinDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketCoinDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-coins : get all the market-coins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of market-coins in body
     */
    @GetMapping("/market-coins")
    @Timed
    public List<MarketCoinDTO> getAllMarketCoins() {
        log.debug("REST request to get all MarketCoins");
        return marketCoinService.findAll();
    }

    @GetMapping("/market-coins/coins/{id}/{userId}")
    @Timed
    public List<MarketCoinDTO> getMarketCoinAll(@PathVariable Long id,@PathVariable Long userId) {
        log.debug("REST request to get all MarketCoins");
        return marketCoinService.findMarketCoinAll(id,userId);
        }

    /**
     * GET  /market-coins/:id : get the "id" market coin.
     *
     * @param id the id of the marketCoinDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketCoinDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-coins/{id}")
    @Timed
    public ResponseEntity<MarketCoinDTO> getMarketCoin(@PathVariable Long id) {
        log.debug("REST request to get Market Coin : {}", id);
        MarketCoinDTO marketCoinDTO = marketCoinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketCoinDTO));
    }

    /**
     * DELETE  /market-coins/:id : delete the "id" market.
     *
     * @param id the id of the marketCoinDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-coins/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketCoin(@PathVariable Long id) {
        log.debug("REST request to delete Market Coin : {}", id);
        marketCoinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-coins?query=:query : search for the market corresponding
     * to the query.
     *
     * @param query the query of the market search
     * @return the result of the search
     */
    @GetMapping("/_search/market-coins")
    @Timed
    public List<MarketCoinDTO> searchMarketCoins(@RequestParam String query) {
        log.debug("REST request to search Market Coin for query {}", query);
        return marketCoinService.search(query);
    }

}
