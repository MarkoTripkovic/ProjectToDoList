package org.jhipster.todo.service;

import org.jhipster.todo.service.dto.KorisnikDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Korisnik.
 */
public interface KorisnikService {

    /**
     * Save a korisnik.
     *
     * @param korisnikDTO the entity to save
     * @return the persisted entity
     */
    KorisnikDTO save(KorisnikDTO korisnikDTO);

    /**
     *  Get all the korisniks.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<KorisnikDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" korisnik.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KorisnikDTO findOne(Long id);

    /**
     *  Delete the "id" korisnik.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
