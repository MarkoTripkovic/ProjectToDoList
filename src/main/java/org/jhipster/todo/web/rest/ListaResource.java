package org.jhipster.todo.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.jhipster.todo.domain.Lista;
import org.jhipster.todo.repository.ListaRepository;
import org.jhipster.todo.security.jwt.JWTConfigurer;
import org.jhipster.todo.security.jwt.TokenProvider;
import org.jhipster.todo.service.ListaService;
import org.jhipster.todo.web.rest.util.HeaderUtil;
import org.jhipster.todo.web.rest.util.PaginationUtil;
import org.jhipster.todo.service.dto.ListaDTO;

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
 * REST controller for managing Lista.
 */
@RestController
@RequestMapping("/api")
public class ListaResource {

    private final Logger log = LoggerFactory.getLogger(ListaResource.class);
        
    @Inject
    private ListaService listaService;
    @Inject TokenProvider tokenProvider;
    @Inject
    ListaRepository listaRepository;

    /**
     * POST  /listas : Create a new lista.
     *
     * @param listaDTO the listaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listaDTO, or with status 400 (Bad Request) if the lista has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dodavanjeListe")
    @Timed
    public ResponseEntity<ListaDTO> createLista(@RequestBody ListaDTO listaDTO) throws URISyntaxException {
        log.debug("REST request to save Lista : {}", listaDTO);
        if (listaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lista", "idexists", "A new lista cannot already have an ID")).body(null);
        }
        ListaDTO result = listaService.save(listaDTO);
        return ResponseEntity.created(new URI("/api/itemizListe/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lista", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /listas : Updates an existing lista.
     *
     * @param listaDTO the listaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listaDTO,
     * or with status 400 (Bad Request) if the listaDTO is not valid,
     * or with status 500 (Internal Server Error) if the listaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/izmenaListe")
    @Timed
    public ResponseEntity<ListaDTO> updateLista(@RequestBody ListaDTO listaDTO) throws URISyntaxException {
        log.debug("REST request to update Lista : {}", listaDTO);
        if (listaDTO.getId() == null) {
            return createLista(listaDTO);
        }
        ListaDTO result = listaService.save(listaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lista", listaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /listas : get all the listas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of listas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/liste")
    @Timed
   public ResponseEntity<List<ListaDTO>> getAllListas(@ApiParam Pageable pageable)
      throws URISyntaxException {
       log.debug("REST request to get a page of Listas");
       Page<ListaDTO> page = listaService.findAll(pageable);
      HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/listas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /listas/:id : get the "id" lista.
     *
     * @param id the id of the listaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/korisnikoveListe")
    @Timed
    public ResponseEntity<List<ListaDTO>> getLista(HttpServletRequest httpServletRequest) {
    	String token = httpServletRequest.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
    	Long id = Long.valueOf(tokenProvider.getUserIdFromToken(token).longValue());
    	List<ListaDTO> dto = (List<ListaDTO>) listaService.findAllByUserId(id);
        
        return Optional.ofNullable(dto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/itemizListe/{id}")
    @Timed
    public ResponseEntity<ListaDTO> getItemFromList(@PathVariable Long id) {
        ListaDTO dto = listaService.findOne(id);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * DELETE  /listas/:id : delete the "id" lista.
     *
     * @param id the id of the listaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/itemizListe/{id}")
    @Timed
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        log.debug("REST request to delete Lista : {}", id);
        listaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lista", id.toString())).build();
    }

}
