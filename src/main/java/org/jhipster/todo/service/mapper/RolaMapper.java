package org.jhipster.todo.service.mapper;

import org.jhipster.todo.domain.*;
import org.jhipster.todo.service.dto.RolaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Rola and its DTO RolaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RolaMapper {

    RolaDTO rolaToRolaDTO(Rola rola);

    List<RolaDTO> rolasToRolaDTOs(List<Rola> rolas);

    Rola rolaDTOToRola(RolaDTO rolaDTO);

    List<Rola> rolaDTOsToRolas(List<RolaDTO> rolaDTOs);
}
