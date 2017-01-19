package org.jhipster.todo.service;

import org.jhipster.todo.service.dto.RolaDTO;
import java.util.List;

/**
 * Service Interface for managing Rola.
 */
public interface RolaService {

    /**
     * Save a rola.
     *
     * @param rolaDTO the entity to save
     * @return the persisted entity
     */
    RolaDTO save(RolaDTO rolaDTO);

    /**
     *  Get all the rolas.
     *  
     *  @return the list of entities
     */
    List<RolaDTO> findAll();

    /**
     *  Get the "id" rola.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RolaDTO findOne(Long id);

    /**
     *  Delete the "id" rola.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
