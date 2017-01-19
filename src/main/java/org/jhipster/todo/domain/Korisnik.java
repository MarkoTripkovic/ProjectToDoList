package org.jhipster.todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Korisnik.
 */
@Entity
@Table(name = "korisnik")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(unique = true)
    private Rola rola;

    @OneToMany(mappedBy = "korisnik")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lista> ids = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Korisnik username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public Korisnik email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Korisnik password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rola getRola() {
        return rola;
    }

    public Korisnik rola(Rola rola) {
        this.rola = rola;
        return this;
    }

    public void setRola(Rola rola) {
        this.rola = rola;
    }

    public Set<Lista> getIds() {
        return ids;
    }

    public Korisnik ids(Set<Lista> listas) {
        this.ids = listas;
        return this;
    }

    public Korisnik addId(Lista lista) {
        ids.add(lista);
        lista.setKorisnik(this);
        return this;
    }

    public Korisnik removeId(Lista lista) {
        ids.remove(lista);
        lista.setKorisnik(null);
        return this;
    }

    public void setIds(Set<Lista> listas) {
        this.ids = listas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Korisnik korisnik = (Korisnik) o;
        if (korisnik.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, korisnik.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Korisnik{" +
            "id=" + id +
            ", username='" + username + "'" +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            '}';
    }

	
}
