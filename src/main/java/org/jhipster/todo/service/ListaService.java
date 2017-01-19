package org.jhipster.todo.service;

import org.jhipster.todo.service.dto.ListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Lista.
 */
public interface ListaService {

    /**
     * Save a lista.
     *
     * @param listaDTO the entity to save
     * @return the persisted entity
     */
    ListaDTO save(ListaDTO listaDTO);

    /**
     *  Get all the listas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ListaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" lista.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ListaDTO findOne(Long id);

    /**
     *  Delete the "id" lista.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
