package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.CoinService;
import io.iansoft.blockchain.web.rest.errors.BadRequestAlertException;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.service.dto.CoinDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Coin.
 */
@RestController
@RequestMapping("/api")
public class CoinResource {

    private final Logger log = LoggerFactory.getLogger(CoinResource.class);

    private static final String ENTITY_NAME = "coin";

    private final CoinService coinService;

    public CoinResource(CoinService coinService) {
        this.coinService = coinService;
    }

    /**
     * POST  /coins : Create a new coin.
     *
     * @param coinDTO the coinDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coinDTO, or with status 400 (Bad Request) if the coin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coins")
    @Timed
    public ResponseEntity<CoinDTO> createCoin(@Valid @RequestBody CoinDTO coinDTO) throws URISyntaxException {
        log.debug("REST request to save Coin : {}", coinDTO);
        if (coinDTO.getId() != null) {
            throw new BadRequestAlertException("A new coin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoinDTO result = coinService.save(coinDTO);
        return ResponseEntity.created(new URI("/api/coins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coins : Updates an existing coin.
     *
     * @param coinDTO the coinDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coinDTO,
     * or with status 400 (Bad Request) if the coinDTO is not valid,
     * or with status 500 (Internal Server Error) if the coinDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coins")
    @Timed
    public ResponseEntity<CoinDTO> updateCoin(@Valid @RequestBody CoinDTO coinDTO) throws URISyntaxException {
        log.debug("REST request to update Coin : {}", coinDTO);
        if (coinDTO.getId() == null) {
            return createCoin(coinDTO);
        }
        CoinDTO result = coinService.save(coinDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coinDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coins : get all the coins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coins in body
     */
    @GetMapping("/coins")
    @Timed
    public List<CoinDTO> getAllCoins() {
        log.debug("REST request to get all Coins");
        return coinService.findAll();
        }

    /**
     * GET  /coins/:id : get the "id" coin.
     *
     * @param id the id of the coinDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coinDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coins/{id}")
    @Timed
    public ResponseEntity<CoinDTO> getCoin(@PathVariable Long id) {
        log.debug("REST request to get Coin : {}", id);
        CoinDTO coinDTO = coinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coinDTO));
    }

    /**
     * DELETE  /coins/:id : delete the "id" coin.
     *
     * @param id the id of the coinDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coins/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoin(@PathVariable Long id) {
        log.debug("REST request to delete Coin : {}", id);
        coinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/coins?query=:query : search for the coin corresponding
     * to the query.
     *
     * @param query the query of the coin search
     * @return the result of the search
     */
    @GetMapping("/_search/coins")
    @Timed
    public List<CoinDTO> searchCoins(@RequestParam String query) {
        log.debug("REST request to search Coins for query {}", query);
        return coinService.search(query);
    }

}
