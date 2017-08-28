package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.CoinisService;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.service.dto.CoinisDTO;
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
 * REST controller for managing Coinis.
 */
@RestController
@RequestMapping("/api")
public class CoinisResource {

    private final Logger log = LoggerFactory.getLogger(CoinisResource.class);

    private static final String ENTITY_NAME = "coinis";

    private final CoinisService coinisService;

    public CoinisResource(CoinisService coinisService) {
        this.coinisService = coinisService;
    }

    @GetMapping("/coin-api/coinis")
    @Timed
    public ResponseEntity<List<CoinisDTO>> getKraken(){

        return ResponseEntity.ok(coinisService.getCoinis());
    }

    /**
     * POST  /coinis : Create a new coinis.
     *
     * @param coinisDTO the coinisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coinisDTO, or with status 400 (Bad Request) if the coinis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coinis")
    @Timed
    public ResponseEntity<CoinisDTO> createCoinis(@Valid @RequestBody CoinisDTO coinisDTO) throws URISyntaxException {
        log.debug("REST request to save Coinis : {}", coinisDTO);
        if (coinisDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new coinis cannot already have an ID")).body(null);
        }
        CoinisDTO result = coinisService.save(coinisDTO);
        return ResponseEntity.created(new URI("/api/coinis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coinis : Updates an existing coinis.
     *
     * @param coinisDTO the coinisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coinisDTO,
     * or with status 400 (Bad Request) if the coinisDTO is not valid,
     * or with status 500 (Internal Server Error) if the coinisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coinis")
    @Timed
    public ResponseEntity<CoinisDTO> updateCoinis(@Valid @RequestBody CoinisDTO coinisDTO) throws URISyntaxException {
        log.debug("REST request to update Coinis : {}", coinisDTO);
        if (coinisDTO.getId() == null) {
            return createCoinis(coinisDTO);
        }
        CoinisDTO result = coinisService.save(coinisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coinisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coinis : get all the coinis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coinis in body
     */
    @GetMapping("/coinis")
    @Timed
    public List<CoinisDTO> getAllCoinis() {
        log.debug("REST request to get all Coinis");
        return coinisService.findAll();
    }

    /**
     * GET  /coinis/:id : get the "id" coinis.
     *
     * @param id the id of the coinisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coinisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coinis/{id}")
    @Timed
    public ResponseEntity<CoinisDTO> getCoinis(@PathVariable Long id) {
        log.debug("REST request to get Coinis : {}", id);
        CoinisDTO coinisDTO = coinisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coinisDTO));
    }

    /**
     * DELETE  /coinis/:id : delete the "id" coinis.
     *
     * @param id the id of the coinisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coinis/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoinis(@PathVariable Long id) {
        log.debug("REST request to delete Coinis : {}", id);
        coinisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/coinis?query=:query : search for the coinis corresponding
     * to the query.
     *
     * @param query the query of the coinis search
     * @return the result of the search
     */
    @GetMapping("/_search/coinis")
    @Timed
    public List<CoinisDTO> searchCoinis(@RequestParam String query) {
        log.debug("REST request to search Coinis for query {}", query);
        return coinisService.search(query);
    }

}
