package org.jhipster.todo.service.mapper;

import org.jhipster.todo.domain.*;
import org.jhipster.todo.service.dto.ListaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Lista and its DTO ListaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListaMapper {

    @Mapping(source = "korisnik.id", target = "korisnikId")
    ListaDTO listaToListaDTO(Lista lista);

    List<ListaDTO> listasToListaDTOs(List<Lista> listas);

    @Mapping(source = "korisnikId", target = "korisnik")
    Lista listaDTOToLista(ListaDTO listaDTO);

    List<Lista> listaDTOsToListas(List<ListaDTO> listaDTOs);

    default Korisnik korisnikFromId(Long id) {
        if (id == null) {
            return null;
        }
        Korisnik korisnik = new Korisnik();
        korisnik.setId(id);
        return korisnik;
    }
}
