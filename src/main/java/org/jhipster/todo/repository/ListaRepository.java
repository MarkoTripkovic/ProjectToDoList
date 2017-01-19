package org.jhipster.todo.repository;

import org.jhipster.todo.domain.Lista;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lista entity.
 */
@SuppressWarnings("unused")
public interface ListaRepository extends JpaRepository<Lista,Long> {

}
