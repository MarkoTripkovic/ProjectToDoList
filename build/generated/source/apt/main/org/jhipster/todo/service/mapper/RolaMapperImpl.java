package org.jhipster.todo.service.mapper;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.jhipster.todo.domain.Rola;

import org.jhipster.todo.service.dto.RolaDTO;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-23T14:45:04+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"

)

@Component

public class RolaMapperImpl implements RolaMapper {

    @Override

    public RolaDTO rolaToRolaDTO(Rola rola) {

        if ( rola == null ) {

            return null;
        }

        RolaDTO rolaDTO = new RolaDTO();

        rolaDTO.setId( rola.getId() );

        rolaDTO.setName( rola.getName() );

        return rolaDTO;
    }

    @Override

    public List<RolaDTO> rolasToRolaDTOs(List<Rola> rolas) {

        if ( rolas == null ) {

            return null;
        }

        List<RolaDTO> list = new ArrayList<RolaDTO>();

        for ( Rola rola : rolas ) {

            list.add( rolaToRolaDTO( rola ) );
        }

        return list;
    }

    @Override

    public Rola rolaDTOToRola(RolaDTO rolaDTO) {

        if ( rolaDTO == null ) {

            return null;
        }

        Rola rola = new Rola();

        rola.setId( rolaDTO.getId() );

        rola.setName( rolaDTO.getName() );

        return rola;
    }

    @Override

    public List<Rola> rolaDTOsToRolas(List<RolaDTO> rolaDTOs) {

        if ( rolaDTOs == null ) {

            return null;
        }

        List<Rola> list = new ArrayList<Rola>();

        for ( RolaDTO rolaDTO : rolaDTOs ) {

            list.add( rolaDTOToRola( rolaDTO ) );
        }

        return list;
    }
}

