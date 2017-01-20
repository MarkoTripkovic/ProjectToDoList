package org.jhipster.todo.repository;

import org.jhipster.todo.domain.Korisnik;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Korisnik entity.
 */

public interface KorisnikRepository extends JpaRepository<Korisnik,Long> {
	Korisnik findByUsername(String username);

	Korisnik findOne(Long id);

}
