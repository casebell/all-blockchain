package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.BitfinexService;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.service.dto.BitfinexDTO;
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
 * REST controller for managing Bitfinex.
 */
@RestController
@RequestMapping("/api")
public class BitfinexResource {

    private final Logger log = LoggerFactory.getLogger(BitfinexResource.class);

    private static final String ENTITY_NAME = "bitfinex";

    private final BitfinexService bitfinexService;

    public BitfinexResource(BitfinexService bitfinexService) {
        this.bitfinexService = bitfinexService;
    }


    @GetMapping("/coin-api/bitfinex")
    @Timed
    public ResponseEntity<List<BitfinexDTO>> getKraken(){
        return ResponseEntity.ok(bitfinexService.getBitfinex());
    }
    /**
     * POST  /bitfinexes : Create a new bitfinex.
     *
     * @param bitfinexDTO the bitfinexDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bitfinexDTO, or with status 400 (Bad Request) if the bitfinex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bitfinexes")
    @Timed
    public ResponseEntity<BitfinexDTO> createBitfinex(@Valid @RequestBody BitfinexDTO bitfinexDTO) throws URISyntaxException {
        log.debug("REST request to save Bitfinex : {}", bitfinexDTO);
        if (bitfinexDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bitfinex cannot already have an ID")).body(null);
        }
        BitfinexDTO result = bitfinexService.save(bitfinexDTO);
        return ResponseEntity.created(new URI("/api/bitfinexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bitfinexes : Updates an existing bitfinex.
     *
     * @param bitfinexDTO the bitfinexDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bitfinexDTO,
     * or with status 400 (Bad Request) if the bitfinexDTO is not valid,
     * or with status 500 (Internal Server Error) if the bitfinexDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bitfinexes")
    @Timed
    public ResponseEntity<BitfinexDTO> updateBitfinex(@Valid @RequestBody BitfinexDTO bitfinexDTO) throws URISyntaxException {
        log.debug("REST request to update Bitfinex : {}", bitfinexDTO);
        if (bitfinexDTO.getId() == null) {
            return createBitfinex(bitfinexDTO);
        }
        BitfinexDTO result = bitfinexService.save(bitfinexDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bitfinexDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bitfinexes : get all the bitfinexes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of bitfinexes in body
     */
    @GetMapping("/bitfinexes")
    @Timed
    public List<BitfinexDTO> getAllBitfinexes(@RequestParam(required = false) String filter) {
        if ("coin-is-null".equals(filter)) {
            log.debug("REST request to get all Bitfinexs where coin is null");
            return bitfinexService.findAllWhereCoinIsNull();
        }
        log.debug("REST request to get all Bitfinexes");
        return bitfinexService.findAll();
        }

    /**
     * GET  /bitfinexes/:id : get the "id" bitfinex.
     *
     * @param id the id of the bitfinexDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bitfinexDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bitfinexes/{id}")
    @Timed
    public ResponseEntity<BitfinexDTO> getBitfinex(@PathVariable Long id) {
        log.debug("REST request to get Bitfinex : {}", id);
        BitfinexDTO bitfinexDTO = bitfinexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bitfinexDTO));
    }

    /**
     * DELETE  /bitfinexes/:id : delete the "id" bitfinex.
     *
     * @param id the id of the bitfinexDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bitfinexes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBitfinex(@PathVariable Long id) {
        log.debug("REST request to delete Bitfinex : {}", id);
        bitfinexService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bitfinexes?query=:query : search for the bitfinex corresponding
     * to the query.
     *
     * @param query the query of the bitfinex search
     * @return the result of the search
     */
    @GetMapping("/_search/bitfinexes")
    @Timed
    public List<BitfinexDTO> searchBitfinexes(@RequestParam String query) {
        log.debug("REST request to search Bitfinexes for query {}", query);
        return bitfinexService.search(query);
    }

}
