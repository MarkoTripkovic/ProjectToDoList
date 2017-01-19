package org.jhipster.todo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.todo.service.RolaService;
import org.jhipster.todo.web.rest.util.HeaderUtil;
import org.jhipster.todo.service.dto.RolaDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Rola.
 */
@RestController
@RequestMapping("/api")
public class RolaResource {

    private final Logger log = LoggerFactory.getLogger(RolaResource.class);
        
    @Inject
    private RolaService rolaService;

    /**
     * POST  /rolas : Create a new rola.
     *
     * @param rolaDTO the rolaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rolaDTO, or with status 400 (Bad Request) if the rola has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rolas")
    @Timed
    public ResponseEntity<RolaDTO> createRola(@Valid @RequestBody RolaDTO rolaDTO) throws URISyntaxException {
        log.debug("REST request to save Rola : {}", rolaDTO);
        if (rolaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rola", "idexists", "A new rola cannot already have an ID")).body(null);
        }
        RolaDTO result = rolaService.save(rolaDTO);
        return ResponseEntity.created(new URI("/api/rolas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rola", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rolas : Updates an existing rola.
     *
     * @param rolaDTO the rolaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rolaDTO,
     * or with status 400 (Bad Request) if the rolaDTO is not valid,
     * or with status 500 (Internal Server Error) if the rolaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rolas")
    @Timed
    public ResponseEntity<RolaDTO> updateRola(@Valid @RequestBody RolaDTO rolaDTO) throws URISyntaxException {
        log.debug("REST request to update Rola : {}", rolaDTO);
        if (rolaDTO.getId() == null) {
            return createRola(rolaDTO);
        }
        RolaDTO result = rolaService.save(rolaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rola", rolaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rolas : get all the rolas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rolas in body
     */
    @GetMapping("/rolas")
    @Timed
    public List<RolaDTO> getAllRolas() {
        log.debug("REST request to get all Rolas");
        return rolaService.findAll();
    }

    /**
     * GET  /rolas/:id : get the "id" rola.
     *
     * @param id the id of the rolaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rolaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rolas/{id}")
    @Timed
    public ResponseEntity<RolaDTO> getRola(@PathVariable Long id) {
        log.debug("REST request to get Rola : {}", id);
        RolaDTO rolaDTO = rolaService.findOne(id);
        return Optional.ofNullable(rolaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rolas/:id : delete the "id" rola.
     *
     * @param id the id of the rolaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rolas/{id}")
    @Timed
    public ResponseEntity<Void> deleteRola(@PathVariable Long id) {
        log.debug("REST request to delete Rola : {}", id);
        rolaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rola", id.toString())).build();
    }

}
