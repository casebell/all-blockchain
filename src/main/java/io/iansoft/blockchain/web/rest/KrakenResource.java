package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.service.KrakenService;
import io.iansoft.blockchain.web.rest.errors.BadRequestAlertException;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.service.dto.KrakenDTO;
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
 * REST controller for managing Kraken.
 */
@RestController
@RequestMapping("/api")
public class KrakenResource {

    private final Logger log = LoggerFactory.getLogger(KrakenResource.class);

    private static final String ENTITY_NAME = "kraken";

    private final KrakenService krakenService;

    public KrakenResource(KrakenService krakenService) {
        this.krakenService = krakenService;
    }

    /**
     * POST  /krakens : Create a new kraken.
     *
     * @param krakenDTO the krakenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new krakenDTO, or with status 400 (Bad Request) if the kraken has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/krakens")
    @Timed
    public ResponseEntity<KrakenDTO> createKraken(@Valid @RequestBody KrakenDTO krakenDTO) throws URISyntaxException {
        log.debug("REST request to save Kraken : {}", krakenDTO);
        if (krakenDTO.getId() != null) {
            throw new BadRequestAlertException("A new kraken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KrakenDTO result = krakenService.save(krakenDTO);
        return ResponseEntity.created(new URI("/api/krakens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /krakens : Updates an existing kraken.
     *
     * @param krakenDTO the krakenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated krakenDTO,
     * or with status 400 (Bad Request) if the krakenDTO is not valid,
     * or with status 500 (Internal Server Error) if the krakenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/krakens")
    @Timed
    public ResponseEntity<KrakenDTO> updateKraken(@Valid @RequestBody KrakenDTO krakenDTO) throws URISyntaxException {
        log.debug("REST request to update Kraken : {}", krakenDTO);
        if (krakenDTO.getId() == null) {
            return createKraken(krakenDTO);
        }
        KrakenDTO result = krakenService.save(krakenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, krakenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /krakens : get all the krakens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of krakens in body
     */
    @GetMapping("/krakens")
    @Timed
    public List<KrakenDTO> getAllKrakens() {
        log.debug("REST request to get all Krakens");
        return krakenService.findAll();
        }

    /**
     * GET  /krakens/:id : get the "id" kraken.
     *
     * @param id the id of the krakenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the krakenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/krakens/{id}")
    @Timed
    public ResponseEntity<KrakenDTO> getKraken(@PathVariable Long id) {
        log.debug("REST request to get Kraken : {}", id);
        KrakenDTO krakenDTO = krakenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(krakenDTO));
    }

    /**
     * DELETE  /krakens/:id : delete the "id" kraken.
     *
     * @param id the id of the krakenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/krakens/{id}")
    @Timed
    public ResponseEntity<Void> deleteKraken(@PathVariable Long id) {
        log.debug("REST request to delete Kraken : {}", id);
        krakenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/krakens?query=:query : search for the kraken corresponding
     * to the query.
     *
     * @param query the query of the kraken search
     * @return the result of the search
     */
    @GetMapping("/_search/krakens")
    @Timed
    public List<KrakenDTO> searchKrakens(@RequestParam String query) {
        log.debug("REST request to search Krakens for query {}", query);
        return krakenService.search(query);
    }

}
