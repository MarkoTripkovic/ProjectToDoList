package org.jhipster.todo.service.mapper;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.jhipster.todo.domain.Korisnik;

import org.jhipster.todo.domain.Lista;

import org.jhipster.todo.service.dto.ListaDTO;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-19T16:23:37+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"

)

@Component

public class ListaMapperImpl implements ListaMapper {

    @Override

    public ListaDTO listaToListaDTO(Lista lista) {

        if ( lista == null ) {

            return null;
        }

        ListaDTO listaDTO = new ListaDTO();

        listaDTO.setKorisnikId( listaKorisnikId( lista ) );

        listaDTO.setId( lista.getId() );

        listaDTO.setTitle( lista.getTitle() );

        listaDTO.setText( lista.getText() );

        listaDTO.setDate( lista.getDate() );

        listaDTO.setStatus( lista.isStatus() );

        return listaDTO;
    }

    @Override

    public List<ListaDTO> listasToListaDTOs(List<Lista> listas) {

        if ( listas == null ) {

            return null;
        }

        List<ListaDTO> list = new ArrayList<ListaDTO>();

        for ( Lista lista : listas ) {

            list.add( listaToListaDTO( lista ) );
        }

        return list;
    }

    @Override

    public Lista listaDTOToLista(ListaDTO listaDTO) {

        if ( listaDTO == null ) {

            return null;
        }

        Lista lista = new Lista();

        lista.setKorisnik( korisnikFromId( listaDTO.getKorisnikId() ) );

        lista.setId( listaDTO.getId() );

        lista.setTitle( listaDTO.getTitle() );

        lista.setText( listaDTO.getText() );

        lista.setDate( listaDTO.getDate() );

        lista.setStatus( listaDTO.getStatus() );

        return lista;
    }

    @Override

    public List<Lista> listaDTOsToListas(List<ListaDTO> listaDTOs) {

        if ( listaDTOs == null ) {

            return null;
        }

        List<Lista> list = new ArrayList<Lista>();

        for ( ListaDTO listaDTO : listaDTOs ) {

            list.add( listaDTOToLista( listaDTO ) );
        }

        return list;
    }

    private Long listaKorisnikId(Lista lista) {

        if ( lista == null ) {

            return null;
        }

        Korisnik korisnik = lista.getKorisnik();

        if ( korisnik == null ) {

            return null;
        }

        Long id = korisnik.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

