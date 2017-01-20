package org.jhipster.todo.repository;

import org.jhipster.todo.domain.Lista;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Lista entity.
 */
public interface ListaRepository extends JpaRepository<Lista,Long> {
	List<Lista> findOneByKorisnikId(Long id);
	

}
