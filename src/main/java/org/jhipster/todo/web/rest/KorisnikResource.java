package org.jhipster.todo.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.jhipster.todo.security.jwt.JWTConfigurer;
import org.jhipster.todo.security.jwt.TokenProvider;
import org.jhipster.todo.service.KorisnikService;
import org.jhipster.todo.web.rest.util.HeaderUtil;
import org.jhipster.todo.web.rest.util.PaginationUtil;
import org.jhipster.todo.service.dto.KorisnikDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Korisnik.
 */
@RestController
@RequestMapping("/api")
public class KorisnikResource {

    private final Logger log = LoggerFactory.getLogger(KorisnikResource.class);
        
    @Inject
    private KorisnikService korisnikService;
    @Inject TokenProvider tokenProvider;

    /**
     * POST  /korisniks : Create a new korisnik.
     *
     * @param korisnikDTO the korisnikDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new korisnikDTO, or with status 400 (Bad Request) if the korisnik has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dodavanjekorisnika")
    @Timed
    public ResponseEntity<KorisnikDTO> createKorisnik(@RequestBody KorisnikDTO korisnikDTO) throws URISyntaxException {
        log.debug("REST request to save Korisnik : {}", korisnikDTO);
        if (korisnikDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("korisnik", "idexists", "A new korisnik cannot already have an ID")).body(null);
        }
        KorisnikDTO result = korisnikService.save(korisnikDTO);
        return ResponseEntity.created(new URI("/api/korisnik/" + result.getId())) .body(result);
    }

    /**
     * PUT  /korisniks : Updates an existing korisnik.
     *
     * @param korisnikDTO the korisnikDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated korisnikDTO,
     * or with status 400 (Bad Request) if the korisnikDTO is not valid,
     * or with status 500 (Internal Server Error) if the korisnikDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/izmenainfokorisnika")
    @Timed
    public ResponseEntity<KorisnikDTO> updateKorisnik(@RequestBody KorisnikDTO korisnikDTO) throws URISyntaxException {
        log.debug("REST request to update Korisnik : {}", korisnikDTO);
        if (korisnikDTO.getId() == null) {
            return createKorisnik(korisnikDTO);
        }
        KorisnikDTO result = korisnikService.save(korisnikDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("korisnik", korisnikDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /korisniks : get all the korisniks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of korisniks in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/svikorisnici")
    @Timed
    public ResponseEntity<List<KorisnikDTO>> getAllKorisniks(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Korisniks");
        Page<KorisnikDTO> page = korisnikService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/svikorisnici");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /korisniks/:id : get the "id" korisnik.
     *
     * @param id the id of the korisnikDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the korisnikDTO, or with status 404 (Not Found)
     */
    @GetMapping("/korisnik")
    @Timed
    public ResponseEntity<KorisnikDTO> getKorisnik(HttpServletRequest httpServletRequest) {
        //log.debug("REST request to get Korisnik : {}", id);
    	String token = httpServletRequest.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
    	Long id =Long.valueOf(tokenProvider.getUserIdFromToken(token).longValue());
    	
        KorisnikDTO korisnikDTO = korisnikService.findOne(id);
        return Optional.ofNullable(korisnikDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    /**
     * DELETE  /korisniks/:id : delete the "id" korisnik.
     *
     * @param id the id of the korisnikDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/korisnik/{login}")
    @Timed
    public ResponseEntity<Void> deleteKorisnik(@PathVariable Long login) {
        korisnikService.delete(login);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("korisnik", login.toString())).build();
    }

}
