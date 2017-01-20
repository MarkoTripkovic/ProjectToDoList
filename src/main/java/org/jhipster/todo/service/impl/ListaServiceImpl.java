package org.jhipster.todo.service.impl;

import org.jhipster.todo.service.ListaService;
import org.jhipster.todo.domain.Lista;
import org.jhipster.todo.repository.ListaRepository;
import org.jhipster.todo.service.dto.ListaDTO;
import org.jhipster.todo.service.mapper.ListaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Lista.
 */
@Service
@Transactional
public class ListaServiceImpl implements ListaService{

    private final Logger log = LoggerFactory.getLogger(ListaServiceImpl.class);
    
    @Inject
    private ListaRepository listaRepository;

    @Inject
    private ListaMapper listaMapper;

    /**
     * Save a lista.
     *
     * @param listaDTO the entity to save
     * @return the persisted entity
     */
    public ListaDTO save(ListaDTO listaDTO) {
        log.debug("Request to save Lista : {}", listaDTO);
        Lista lista = listaMapper.listaDTOToLista(listaDTO);
        lista = listaRepository.save(lista);
        ListaDTO result = listaMapper.listaToListaDTO(lista);
        return result;
    }

    /**
     *  Get all the listas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ListaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Listas");
        Page<Lista> result = listaRepository.findAll(pageable);
        return result.map(lista -> listaMapper.listaToListaDTO(lista));
    }

    /**
     *  Get one lista by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ListaDTO findOne(Long id) {
        log.debug("Request to get Lista : {}", id);
        Lista lista = listaRepository.findOne(id);
        ListaDTO listaDTO = listaMapper.listaToListaDTO(lista);
        return listaDTO;
    }

    /**
     *  Delete the  lista by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lista : {}", id);
        listaRepository.delete(id);
    }

	public List<ListaDTO> findAllByUserId(Long id) {
		List<Lista> lista = listaRepository.findOneByKorisnikId(id);
		List<ListaDTO> listaDTO = listaMapper.listasToListaDTOs(lista);
		return listaDTO;
		
	}

	
}
