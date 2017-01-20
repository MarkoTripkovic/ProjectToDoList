package org.jhipster.todo.service.impl;

import org.jhipster.todo.service.KorisnikService;
import org.jhipster.todo.domain.Korisnik;
import org.jhipster.todo.repository.KorisnikRepository;
import org.jhipster.todo.service.dto.KorisnikDTO;
import org.jhipster.todo.service.mapper.KorisnikMapper;
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
 * Service Implementation for managing Korisnik.
 */
@Service
@Transactional
public class KorisnikServiceImpl implements KorisnikService{

    private final Logger log = LoggerFactory.getLogger(KorisnikServiceImpl.class);
    
    @Inject
    private KorisnikRepository korisnikRepository;

    @Inject
    private KorisnikMapper korisnikMapper;

    /**
     * Save a korisnik.
     *
     * @param korisnikDTO the entity to save
     * @return the persisted entity
     */
    public KorisnikDTO save(KorisnikDTO korisnikDTO) {
        log.debug("Request to save Korisnik : {}", korisnikDTO);
        Korisnik korisnik = korisnikMapper.korisnikDTOToKorisnik(korisnikDTO);
        korisnik = korisnikRepository.save(korisnik);
        KorisnikDTO result = korisnikMapper.korisnikToKorisnikDTO(korisnik);
        return result;
    }

    /**
     *  Get all the korisniks.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<KorisnikDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Korisniks");
        Page<Korisnik> result = korisnikRepository.findAll(pageable);
        return result.map(korisnik -> korisnikMapper.korisnikToKorisnikDTO(korisnik));
    }

    /**
     *  Get one korisnik by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public KorisnikDTO findOne(Long id) {
        log.debug("Request to get Korisnik : {}", id);
        Korisnik korisnik = korisnikRepository.findOne(id);
        KorisnikDTO korisnikDTO = korisnikMapper.korisnikToKorisnikDTO(korisnik);
        return korisnikDTO;
    }

    /**
     *  Delete the  korisnik by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Korisnik : {}", id);
        korisnikRepository.delete(id);
    }

	@Override
	@Transactional(readOnly = true) 
	public KorisnikDTO findByUsername(String username) {
		Korisnik korisnik = korisnikRepository.findByUsername(username);
        KorisnikDTO korisnikDTO = korisnikMapper.korisnikToKorisnikDTO(korisnik);
        return korisnikDTO;
	}

	
}
