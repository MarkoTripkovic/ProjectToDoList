package org.jhipster.todo.service.impl;

import org.jhipster.todo.service.RolaService;
import org.jhipster.todo.domain.Rola;
import org.jhipster.todo.repository.RolaRepository;
import org.jhipster.todo.service.dto.RolaDTO;
import org.jhipster.todo.service.mapper.RolaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Rola.
 */
@Service
@Transactional
public class RolaServiceImpl implements RolaService{

    private final Logger log = LoggerFactory.getLogger(RolaServiceImpl.class);
    
    @Inject
    private RolaRepository rolaRepository;

    @Inject
    private RolaMapper rolaMapper;

    /**
     * Save a rola.
     *
     * @param rolaDTO the entity to save
     * @return the persisted entity
     */
    public RolaDTO save(RolaDTO rolaDTO) {
        log.debug("Request to save Rola : {}", rolaDTO);
        Rola rola = rolaMapper.rolaDTOToRola(rolaDTO);
        rola = rolaRepository.save(rola);
        RolaDTO result = rolaMapper.rolaToRolaDTO(rola);
        return result;
    }

    /**
     *  Get all the rolas.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<RolaDTO> findAll() {
        log.debug("Request to get all Rolas");
        List<RolaDTO> result = rolaRepository.findAll().stream()
            .map(rolaMapper::rolaToRolaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one rola by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RolaDTO findOne(Long id) {
        log.debug("Request to get Rola : {}", id);
        Rola rola = rolaRepository.findOne(id);
        RolaDTO rolaDTO = rolaMapper.rolaToRolaDTO(rola);
        return rolaDTO;
    }

    /**
     *  Delete the  rola by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Rola : {}", id);
        rolaRepository.delete(id);
    }
}
