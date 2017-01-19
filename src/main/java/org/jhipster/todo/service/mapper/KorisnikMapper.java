package org.jhipster.todo.service.mapper;

import org.jhipster.todo.domain.*;
import org.jhipster.todo.service.dto.KorisnikDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Korisnik and its DTO KorisnikDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KorisnikMapper {

    @Mapping(source = "rola.id", target = "rolaId")
    KorisnikDTO korisnikToKorisnikDTO(Korisnik korisnik);

    List<KorisnikDTO> korisniksToKorisnikDTOs(List<Korisnik> korisniks);

    @Mapping(source = "rolaId", target = "rola")
    @Mapping(target = "ids", ignore = true)
    Korisnik korisnikDTOToKorisnik(KorisnikDTO korisnikDTO);

    List<Korisnik> korisnikDTOsToKorisniks(List<KorisnikDTO> korisnikDTOs);

    default Rola rolaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Rola rola = new Rola();
        rola.setId(id);
        return rola;
    }
}
