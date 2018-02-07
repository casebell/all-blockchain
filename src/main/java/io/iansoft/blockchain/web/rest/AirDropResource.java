package io.iansoft.blockchain.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.iansoft.blockchain.domain.AirDrop;

import io.iansoft.blockchain.repository.AirDropRepository;
import io.iansoft.blockchain.repository.search.AirDropSearchRepository;
import io.iansoft.blockchain.web.rest.errors.BadRequestAlertException;
import io.iansoft.blockchain.web.rest.util.HeaderUtil;
import io.iansoft.blockchain.web.rest.util.PaginationUtil;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AirDrop.
 */
@RestController
@RequestMapping("/api")
public class AirDropResource {

    private final Logger log = LoggerFactory.getLogger(AirDropResource.class);

    private static final String ENTITY_NAME = "airDrop";

    private final AirDropRepository airDropRepository;

    private final AirDropSearchRepository airDropSearchRepository;

    public AirDropResource(AirDropRepository airDropRepository, AirDropSearchRepository airDropSearchRepository) {
        this.airDropRepository = airDropRepository;
        this.airDropSearchRepository = airDropSearchRepository;
    }

    /**
     * POST  /air-drops : Create a new airDrop.
     *
     * @param airDrop the airDrop to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airDrop, or with status 400 (Bad Request) if the airDrop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/air-drops")
    @Timed
    public ResponseEntity<AirDrop> createAirDrop(@Valid @RequestBody AirDrop airDrop) throws URISyntaxException {
        log.debug("REST request to save AirDrop : {}", airDrop);
        if (airDrop.getId() != null) {
            throw new BadRequestAlertException("A new airDrop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AirDrop result = airDropRepository.save(airDrop);
        airDropSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/air-drops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /air-drops : Updates an existing airDrop.
     *
     * @param airDrop the airDrop to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airDrop,
     * or with status 400 (Bad Request) if the airDrop is not valid,
     * or with status 500 (Internal Server Error) if the airDrop couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/air-drops")
    @Timed
    public ResponseEntity<AirDrop> updateAirDrop(@Valid @RequestBody AirDrop airDrop) throws URISyntaxException {
        log.debug("REST request to update AirDrop : {}", airDrop);
        if (airDrop.getId() == null) {
            return createAirDrop(airDrop);
        }
        AirDrop result = airDropRepository.save(airDrop);
        airDropSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airDrop.getId().toString()))
            .body(result);
    }

    /**
     * GET  /air-drops : get all the airDrops.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of airDrops in body
     */
    @GetMapping("/air-drops")
    @Timed
    public ResponseEntity<List<AirDrop>> getAllAirDrops(Pageable pageable) {
        log.debug("REST request to get a page of AirDrops");
        Page<AirDrop> page = airDropRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/air-drops");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /air-drops/:id : get the "id" airDrop.
     *
     * @param id the id of the airDrop to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airDrop, or with status 404 (Not Found)
     */
    @GetMapping("/air-drops/{id}")
    @Timed
    public ResponseEntity<AirDrop> getAirDrop(@PathVariable Long id) {
        log.debug("REST request to get AirDrop : {}", id);
        AirDrop airDrop = airDropRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(airDrop));
    }

    /**
     * DELETE  /air-drops/:id : delete the "id" airDrop.
     *
     * @param id the id of the airDrop to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/air-drops/{id}")
    @Timed
    public ResponseEntity<Void> deleteAirDrop(@PathVariable Long id) {
        log.debug("REST request to delete AirDrop : {}", id);
        airDropRepository.delete(id);
        airDropSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/air-drops?query=:query : search for the airDrop corresponding
     * to the query.
     *
     * @param query the query of the airDrop search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/air-drops")
    @Timed
    public ResponseEntity<List<AirDrop>> searchAirDrops(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AirDrops for query {}", query);
        Page<AirDrop> page = airDropSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/air-drops");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
