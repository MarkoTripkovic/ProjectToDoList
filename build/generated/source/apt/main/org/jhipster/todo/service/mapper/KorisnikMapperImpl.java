package org.jhipster.todo.service.mapper;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.jhipster.todo.domain.Korisnik;

import org.jhipster.todo.domain.Rola;

import org.jhipster.todo.service.dto.KorisnikDTO;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-20T14:41:28+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_77 (Oracle Corporation)"

)

@Component

public class KorisnikMapperImpl implements KorisnikMapper {

    @Override

    public KorisnikDTO korisnikToKorisnikDTO(Korisnik korisnik) {

        if ( korisnik == null ) {

            return null;
        }

        KorisnikDTO korisnikDTO = new KorisnikDTO();

        korisnikDTO.setRolaId( korisnikRolaId( korisnik ) );

        korisnikDTO.setId( korisnik.getId() );

        korisnikDTO.setUsername( korisnik.getUsername() );

        korisnikDTO.setEmail( korisnik.getEmail() );

        korisnikDTO.setPassword( korisnik.getPassword() );

        return korisnikDTO;
    }

    @Override

    public List<KorisnikDTO> korisniksToKorisnikDTOs(List<Korisnik> korisniks) {

        if ( korisniks == null ) {

            return null;
        }

        List<KorisnikDTO> list = new ArrayList<KorisnikDTO>();

        for ( Korisnik korisnik : korisniks ) {

            list.add( korisnikToKorisnikDTO( korisnik ) );
        }

        return list;
    }

    @Override

    public Korisnik korisnikDTOToKorisnik(KorisnikDTO korisnikDTO) {

        if ( korisnikDTO == null ) {

            return null;
        }

        Korisnik korisnik = new Korisnik();

        korisnik.setRola( rolaFromId( korisnikDTO.getRolaId() ) );

        korisnik.setId( korisnikDTO.getId() );

        korisnik.setUsername( korisnikDTO.getUsername() );

        korisnik.setEmail( korisnikDTO.getEmail() );

        korisnik.setPassword( korisnikDTO.getPassword() );

        return korisnik;
    }

    @Override

    public List<Korisnik> korisnikDTOsToKorisniks(List<KorisnikDTO> korisnikDTOs) {

        if ( korisnikDTOs == null ) {

            return null;
        }

        List<Korisnik> list = new ArrayList<Korisnik>();

        for ( KorisnikDTO korisnikDTO : korisnikDTOs ) {

            list.add( korisnikDTOToKorisnik( korisnikDTO ) );
        }

        return list;
    }

    private Long korisnikRolaId(Korisnik korisnik) {

        if ( korisnik == null ) {

            return null;
        }

        Rola rola = korisnik.getRola();

        if ( rola == null ) {

            return null;
        }

        Long id = rola.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

